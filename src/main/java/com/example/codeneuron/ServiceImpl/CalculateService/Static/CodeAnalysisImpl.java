package com.example.codeneuron.ServiceImpl.CalculateService.Static;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.Dao.Node_codeMapper;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.GraphNode;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.PO.Node_code;
import com.example.codeneuron.Service.CalculateService.Dynamic.DomainCal;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.Service.CalculateService.Static.CodeAnalysis;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.VO.ResponseVO;
import org.apache.bcel.classfile.ClassParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.codeneuron.ServiceImpl.CalculateService.Static.CalPosition;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class CodeAnalysisImpl implements CodeAnalysis {
    NodeMapper nodeMapper;
    EdgeMapper edgeMapper;
    Node_codeMapper node_codeMapper;
    DomainCal domainCal;
    InitGraph initGraph;
    GraphCal graphCal;
    @Autowired
    public CodeAnalysisImpl(NodeMapper nodeMapper,EdgeMapper edgeMapper,Node_codeMapper node_codeMapper,
                            DomainCal domainCal,InitGraph initGraph,GraphCal graphCal){
        this.nodeMapper=nodeMapper;
        this.edgeMapper=edgeMapper;
        this.node_codeMapper=node_codeMapper;
        this.domainCal=domainCal;
        this.graphCal=graphCal;
        this.initGraph=initGraph;
    }

    @Override
    public ResponseVO jarFileAnalysis(MultipartFile jarFile,int projectId){
        if(jarFile.getOriginalFilename().endsWith("jar")||jarFile.getName().endsWith("jar")){
            try{
                //生成节点与方法依赖
                File f=fileTransfer(jarFile);
                List<String> dependencies=generateDependencies(f);
                generateNodesAndEdges(dependencies,projectId);
                File file = new File(f.toURI());
                if (file.delete()){
                    System.out.println("删除成功");
                }else {
                    System.out.println("删除失败");

                }
            }catch (Exception e){
                e.printStackTrace();
                return ResponseVO.buildFailure("error");
            }
        }else{
            return ResponseVO.buildFailure("error");
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO zipFileAnalysis(MultipartFile zipFile, int projectId){
        if(zipFile.getOriginalFilename().endsWith("zip")||zipFile.getName().endsWith("zip")){
            try{
                //提取方法代码
                List<List<String>> functions=getFunctionSourceCode(zipFile.getInputStream());
                if(!saveCode(functions,projectId)){
                    return ResponseVO.buildFailure("error");
                }
            }catch (Exception e){
                e.printStackTrace();
                return ResponseVO.buildFailure("error");
            }
        }else{
            return ResponseVO.buildFailure("error");
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public ResponseVO getNodeCode(int nodeId){
        Node_code node_code = node_codeMapper.getNode_Code(nodeId);
        if(node_code!=null) {
            return ResponseVO.buildSuccess(node_code.getCode());
        }else{
            return ResponseVO.buildFailure("No code for this node");
        }
    }
    public boolean saveCode(List<List<String>> functions,int projectId){
        try{
            for(List<String> function:functions){
                String fullName[]=function.get(0).split("\\(");
                String name=fullName[0].trim(),parameters[]=fullName[1].split(",");
                int lastLen=parameters[parameters.length-1].length();
                parameters[parameters.length-1]=parameters[parameters.length-1].substring(0,lastLen-1);
                List<String> overRideFunc=getSimilarFunction(name,projectId);
                for(String similarFunction:overRideFunc){
                    String similarFunctionName=similarFunction.split("\\(")[0].trim();
                    String totalParm=similarFunction.split("\\(")[1];
                    String singleParam[]=totalParm.split(",");
                    if(singleParam.length==parameters.length&&similarFunctionName.equals(name)){
                        int saveFlag=1;
                        if(singleParam[0].length()>1&&parameters[0].equals("")){
                            saveFlag=0;
                        }else {
                            for(int i=0;i<parameters.length;i++){
                                String tempParam=parameters[i].trim();
                                if(tempParam.contains(" ")){
                                    tempParam=tempParam.split(" ")[0];
                                }
                                if(!singleParam[i].contains(tempParam)){
                                    saveFlag=0;
                                    break;
                                }
                            }
                        }
                        if(saveFlag==1){
                            //存储代码
//                            System.out.println("similar:"+similarFunction);
                            Node node=nodeMapper.selectNodeByNameAndProjectId(similarFunction,projectId);
                            String code="";
                            for(int i=1;i<function.size();i++){
                                code+=(function.get(i)+"\n");
                            }
//                            System.out.println(node.getName());
//                            System.out.println(code);
//                            System.out.println("===================");

                            node_codeMapper.createNode_code(new Node_code(node.getId(),code));
                        }
                    }
                }
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    public List<String> generateDependencies(File f){
        List<String> dependencies=new ArrayList<>();

        Function<ClassParser, ClassVisitor> getClassVisitor =
                (ClassParser cp) -> {
                    try {
                        return new ClassVisitor(cp.parse());
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                };

        try {

            if (!f.exists()) {
                System.err.println("Jar file " + f.getName() + " does not exist");
            }

            try (JarFile jar = new JarFile(f)) {
                Stream<JarEntry> entries = enumerationAsStream(jar.entries());

                String methodCalls = entries.
                        flatMap(e -> {
                            if (e.isDirectory() || !e.getName().endsWith(".class"))
                                return (new ArrayList<String>()).stream();

                            ClassParser cp = new ClassParser(f.getName(), e.getName());
                            return getClassVisitor.apply(cp).start().methodCalls().stream();
                        }).
                        map(s -> s + "\n").
                        reduce(new StringBuilder(),
                                StringBuilder::append,
                                StringBuilder::append).toString();
                for(String s:methodCalls.split("\n")){
                    if(s.startsWith("M")){
                        dependencies.add(s);
                    }
                }
                return dependencies;
            }
        } catch (IOException e) {
            System.err.println("Error while processing jar: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<List<String>> getFunctionSourceCode(InputStream in) throws Exception {
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        List<List<String>> functions=new ArrayList<>();
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.getName().endsWith(".java")) {
                //System.out.println(ze.getName());
                String paths[]=ze.getName().split("/");
                int flag=0;
                for(String s:paths){
                    if (s.startsWith(".")){
                        flag=1;
                        break;
                    }
                }
                if(flag!=1){
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zin));
                    functions.addAll(getFuncCodeEnter(br));
                }
            }
        }
        zin.closeEntry();
        return functions;
    }

    List<List<String>> getFuncCodeEnter(BufferedReader br) throws Exception{
        List<String> clearCode=new ArrayList<String>();
        String packageName="";
        String line;
        int enumFlag=0;
        int anotationFlag=0;
        int left=0,right=0;
        while ((line=br.readLine())!=null){
            String temp=line.trim();
            if(anotationFlag==1){
                if(line.contains("*/")){
                    anotationFlag=0;
                }
                continue;
            }
            if(enumFlag==1){
                for(int i=0;i<temp.length();i++){
                    if(temp.charAt(i)=='{'){
                        left++;
                    }
                    if(temp.charAt(i)=='}'){
                        right++;
                    }
                }
                if(left==right){
                    enumFlag=0;
                }
                continue;
            }
            if(line.startsWith("package ")){
                packageName=line.substring(8,line.length()-1);
                continue;
            }
            if(line.startsWith("import ")||line.startsWith("@")||line.startsWith("/")||line.startsWith(" *")||line.length()<1){
                if(line.startsWith("/*")){
                    anotationFlag=1;
                }
                continue;
            }
            if(temp.length()<1){
                continue;
            }
            if(temp.contains("enum")){
                enumFlag=1;
                for(int i=0;i<temp.length();i++){
                    if(temp.charAt(i)=='{'){
                        left++;
                    }
                    if(temp.charAt(i)=='}'){
                        right++;
                    }
                }
            }
            clearCode.add(line);

        }
        List<List<String>> functions=getFuncCode(packageName,clearCode);
        return functions;
    }

    List<List<String>> getFuncCode(String packageName, List<String> clearCode){
        List<List<String>> functions=new ArrayList<List<String>>();
        List<String> function=new ArrayList<String>();
        String className="";
        String funcName="";
        int muliLineFlag=0;
        int noFuncFlag=0;
        int numOfLeft=0,numOfRight=0;
        for(int idx=0;idx<clearCode.size();idx++){
            String line=clearCode.get(idx);
            //System.out.println(idx+":"+line+" left:"+numOfLeft+"right:"+numOfRight);
            String temp=line;
            temp=temp.trim();
            //System.out.println(temp);
            if(muliLineFlag==1){
                //System.out.println("invalid2:"+line);
                if(line.contains("*/")){
                    muliLineFlag=0;
                    continue;
                }
                continue;
            }
            if(temp.startsWith("@")||temp.startsWith("/")||temp.startsWith("*")){
                //System.out.println("invald:"+line);
                if(temp.startsWith("/*")){
                    muliLineFlag=1;
                }
                continue;
            }
            if(numOfLeft==numOfRight){
                //System.out.println("class:"+line+" pack:"+packageName+" class:"+className);
                if(temp.startsWith("{")){
                    line=clearCode.get(idx-1)+line;
                }
                String classSplit[]=line.split(" ");
                int count=0;
                for(String s:classSplit){
                    if(s.equals("class")||s.equals("interface")||s.equals("enum")){
                        break;
                    }
                    count++;
                }
                className=classSplit[count+1];
                char c;
                for(int i=0;i<line.length();i++){
                    c=line.charAt(i);
                    if(c=='{'){
                        numOfLeft++;
                    }else if(c=='}'){
                        numOfRight++;
                    }
                }
                continue;
            }
            if((numOfLeft-numOfRight)==1){
                //System.out.println("1-:"+line);
//                System.out.println(packageName);
//                System.out.println(className);
                if(line.contains("{")){
                    int backTemp=1;
                    //System.out.println(temp);
                    if(temp.contains("{")&&((!temp.contains("(")))){
                        //temp=clearCode.get(idx-1)+temp;
                        if((!clearCode.get(idx-1).contains(")")&&(!temp.contains(")")))){
                            noFuncFlag=1;
                            continue;
                        }
                        while(true){
                            if(temp.contains("(")||temp.contains("enum")){
                                break;
                            }
                            //System.out.println("cirlce:"+clearCode.get(idx-backTemp));
                            temp=clearCode.get(idx-backTemp)+temp;
                            backTemp++;
                        }
                        if(temp.contains("enum")){
                            continue;
                        }
                    }
                    //System.out.println(line);
                    function=new ArrayList<String>();
                    funcName=getFuncName(temp);
                    function.add(packageName+"."+className+":"+funcName);
                    //function.add(line);
                    for(int k=0;k<backTemp;k++){
                        function.add(clearCode.get(idx-backTemp+k+1));
                    }
                    char c;
                    for(int i=0;i<line.length();i++){
                        c=line.charAt(i);
                        if(c=='{'){
                            numOfLeft++;
                        }else if(c=='}'){
                            numOfRight++;
                        }
                    }
                    if((numOfLeft-numOfRight)==1){
                        functions.add(function);
                    }
                }else if (line.contains("}")){
                    if(noFuncFlag==1){
                        noFuncFlag=0;
                        continue;
                    }
                    char c;
                    for(int i=0;i<line.length();i++){
                        c=line.charAt(i);
                        if(c=='{'){
                            numOfLeft++;
                        }else if(c=='}'){
                            numOfRight++;
                        }
                    }
                }else{
                    //System.out.println("invalid:"+line);
                }
                continue;
            }
            if((numOfLeft-numOfRight)>=2){
                //System.out.println("code:"+line);
                function.add(line);
                char c;
                for(int i=0;i<line.length();i++){
                    c=line.charAt(i);
                    if(c=='{'){
                        numOfLeft++;
                    }else if(c=='}'){
                        numOfRight++;
                    }
                }
                if((numOfLeft-numOfRight)==1){
                    if(function.size()>1){
                        functions.add(function);
                    }
                }
                continue;
            }
        }
        return functions;
    }

    String getFuncName(String line){
        //System.out.print(line);
        int leftIdx;
        String name;
        for(leftIdx=0;leftIdx<line.length();leftIdx++){
            if(line.charAt(leftIdx)=='('){
                break;
            }
        }
        int left=leftIdx-1,right=leftIdx+1;
        while(left>0){
            if(line.charAt(left)==' '){
                left--;
            }else {
                break;
            }
        }
        while (left>0){
            if(line.charAt(left)==' '){
                break;
            }
            left--;
        }
        while (right<line.length()){
            if(line.charAt(right)==')'){
                break;
            }
            right++;
        }
        name=line.substring(left+1,right+1);
        return name;
    }

    public <T> Stream<T> enumerationAsStream(Enumeration<T> e) {
        return StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                        new Iterator<T>() {
                            public T next() {
                                return e.nextElement();
                            }

                            public boolean hasNext() {
                                return e.hasMoreElements();
                            }
                        },
                        Spliterator.ORDERED), false);
    }

    public File fileTransfer(MultipartFile multipartFile){
        int n;
        File f = null;
        // 输出文件的新name 就是指上传后的文件名称
        //System.out.println("getName:"+multipartFile.getName());
        // 输出源文件名称 就是指上传前的文件名称
        //System.out.println("Oriname:"+multipartFile.getOriginalFilename());
        // 创建文件
        f = new File(multipartFile.getName());
        try ( InputStream in  = multipartFile.getInputStream(); OutputStream os = new FileOutputStream(f)){
            // 得到文件流。以文件流的方式输出到新文件
            // 可以使用byte[] ss = multipartFile.getBytes();代替while
            byte[] buffer = new byte[4096];
            while ((n = in.read(buffer,0,4096)) != -1){
                os.write(buffer,0,n);
            }
            // 读取文件第一行
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            //System.out.println(bufferedReader.readLine());
            // 输出路径
            bufferedReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        // 输出file的URL
        //System.out.println(f.toURI().toURL().toString());
        // 输出文件的绝对路径
        //System.out.println(f.getAbsolutePath());
        // 操作完上的文件 需要删除在根目录下生成的文件
//        File file = new File(f.toURI());
//        if (file.delete()){
//            System.out.println("删除成功");
//        }else {
//            System.out.println("删除失败");
//
//        }
        return f;
    }

//    public void test(){
//        System.out.println(1111);
//        List<Node> nodes = nodeMapper.selectNodeByProjectId(1);
//        List<Edge> edges = edgeMapper.selectEdgeByProjectId(1);
//        iterateCollision(nodes,edges);
//    }

    public void iterateCollision(List<Node> nodeList, List<Edge> edgeList){
        List<Node> show;
        double initialX, initialY, initialSize = 40.0;
        for (Node node : nodeList) {
            initialX = 0 + CalPosition.CANVAS_WIDTH * .5;
            initialY = 0 + CalPosition.CANVAS_HEIGHT * .5;
            node.setXPosition(initialX + initialSize * (Math.random() - .5));
            node.setYPosition(initialY + initialSize * (Math.random() - .5));
        }
        CalPosition calPosition = new CalPosition(nodeList, edgeList);
        for(int i = 0;i < 200; i++){
            calPosition.collide();
        }
        show = calPosition.getNodeList();
        for(int i = 0;i < show.size();i++){
            System.out.println(show.get(i).getName()+" "+show.get(i).getX()+" "+show.get(i).getY());
        }
        for(int i = 0;i < show.size();i++){
            nodeMapper.updateNodeX(show.get(i).getX(), show.get(i).getId(), show.get(i).getProjectId());
            nodeMapper.updateNodeY(show.get(i).getY(), show.get(i).getId(), show.get(i).getProjectId());
        }
    }

    public void generateNodesAndEdges(List<String> dependencies,int projectId){
        HashMap<String, Node> nodeHashMap=new HashMap<>();
        HashMap<String, Edge> edgeHashMap=new HashMap<>();
        List<Node> toBeInserted=new ArrayList<>();
        List<Edge> toBeInsertedEdge=new ArrayList<>();
        for(String dependency:dependencies){
            dependency=dependency.substring(2);
            String twoFunc[]=dependency.split(" ");
            String callerName=twoFunc[0];
            String type=twoFunc[1].substring(0,3);
            String calleeName=twoFunc[1].substring(3);
            Node node;
            String callerPackage=callerName.split("\\.")[0],calleePackage=calleeName.split("\\.")[0];
            if(!calleePackage.equals(callerPackage)){
                continue;
            }
            if(!nodeHashMap.containsKey(callerName)){
                node=new Node();
                node.setName(callerName);
                node.setProjectId(projectId);
                node.setVisited(false);
                nodeHashMap.put(callerName,node);
                toBeInserted.add(node);
            }
            if(!nodeHashMap.containsKey(calleeName)){
                node=new Node();
                node.setName(calleeName);
                node.setProjectId(projectId);
                node.setVisited(false);
                nodeHashMap.put(calleeName,node);
                toBeInserted.add(node);
            }
            if(!edgeHashMap.containsKey(callerName+calleeName)){
                Edge edge=new Edge();
                edge.setCallerName(callerName);
                edge.setCalleeName(calleeName);
                edge.setProjectId(projectId);
                edge.setTypeOfCall(type);
                edgeHashMap.put(callerName+calleeName,edge);
//                System.out.println(callerName);
//                System.out.println(calleeName);
                toBeInsertedEdge.add(edge);
            }
        }
        nodeMapper.insertNodeForProject(toBeInserted,projectId);
        edgeMapper.insertEdgeForProject(toBeInsertedEdge,projectId);
        HashMap<String, LinkedList<GraphNode>> graph = initGraph.InitAdjacencyTable(toBeInserted,toBeInsertedEdge);
        HashMap<String, LinkedList<GraphNode>> inverseGraph = initGraph.InitInverseAdjacencyTable(toBeInserted,toBeInsertedEdge);
        List<Edge> edgesWithCloseness = (List<Edge>)graphCal.ClosenessCalculate(projectId,graph,inverseGraph).getContent();
        edgeMapper.updateEdgeCloseness(edgesWithCloseness);
        domainCal.setThreshold(0,projectId);
        iterateCollision(toBeInserted,toBeInsertedEdge);
    }

    public List<String> getSimilarFunction(String name, int projectId){
        List<Node> nodes = nodeMapper.selectNodeByProjectId(projectId);
        ArrayList<String> all_matched_funcs = new ArrayList<String>();
        String tmp;
        //System.out.println("name:"+name);
        for(int i = 0; i< nodes.size(); i++){
            tmp = nodes.get(i).getName();
            //System.out.println("tmp:"+tmp);
            if(tmp.indexOf(name)!=-1){
                all_matched_funcs.add(nodes.get(i).getName());
            }
        }
        return all_matched_funcs;

    }
}
