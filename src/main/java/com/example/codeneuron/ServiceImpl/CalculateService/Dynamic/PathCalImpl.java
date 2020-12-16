package com.example.codeneuron.ServiceImpl.CalculateService.Dynamic;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.GraphNode;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.Service.CalculateService.Dynamic.PathCal;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathCalImpl implements PathCal {
    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    GraphCal graphCal;
    @Autowired
    InitGraph initGraph;
    /**
     * 路径查找
     * @return
     */
    private LinkedList<GraphNode> onePath = new LinkedList<GraphNode>();
    private Integer shortestPathLength = -1;
    private ArrayList<ArrayList<GraphNode>> shortestPath = new ArrayList<>(); //最短路径可能有多条
    private ArrayList<ArrayList<GraphNode>> allPaths = new ArrayList<>();
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private HashMap<String, LinkedList<GraphNode>> graph = new HashMap<>();
    private HashMap<String, LinkedList<GraphNode>> inverseGraph = new HashMap<>();


    @Override
    public ResponseVO searchAllPaths(String source, String target, int projectId){
        nodes = nodeMapper.selectNodeByProjectId(projectId);
        edges = edgeMapper.selectEdgeByProjectId(projectId);
        graph = initGraph.InitAdjacencyTable(nodes, edges);
        inverseGraph = initGraph.InitInverseAdjacencyTable(nodes,edges);
        graphCal.ClosenessCalculate(projectId,graph,inverseGraph);
        findPath(source, target, projectId);
        if(allPaths.size()==0){
            return ResponseVO.buildFailure("No paths from "+source+" to "+target);
        }
        sortByLength(allPaths);
        return ResponseVO.buildSuccess(allPaths);
    }
    @Override
    public ResponseVO searchShortestPath(String source, String target, int projectId) {
        nodes = nodeMapper.selectNodeByProjectId(projectId);
        edges = edgeMapper.selectEdgeByProjectId(projectId);
        graph = initGraph.InitAdjacencyTable(nodes, edges);
        inverseGraph = initGraph.InitInverseAdjacencyTable(nodes,edges);
        graphCal.ClosenessCalculate(projectId,graph,inverseGraph);
        findPath(source, target, projectId);
        if(shortestPath.size()==0){
            return ResponseVO.buildFailure("No paths from "+source+" to "+target);
        }
        return ResponseVO.buildSuccess(shortestPath);
    }

    //根据函数全名进行搜索
    public void findPath(String src, String des, int projectId){
        onePath.clear();
        allPaths.clear();
        shortestPath.clear();
        shortestPathLength = -1;
        for(Node node:nodes){
            node.setVisited(false);
        }
        if(graph.get(src)==null){
            return;
        }
        DFS(graph.get(src).getFirst(),des);
        //用后置false
        for(Node node:nodes){
            node.setVisited(false);
        }
    }

    //DFS搜索
    public void DFS(GraphNode start, String des){
        if(onePath.size()==0){
            onePath.push(start);
        }
        start.getNode().setVisited(true);
        LinkedList<GraphNode> adjacencyList = graph.get(start.getNode().getName());
        for(GraphNode node:adjacencyList){
            if(node==adjacencyList.getFirst()){
                continue;
            }
            if(!node.getNode().getVisited()){
                onePath.push(node);
                if(node.getNode().getName().equals(des)){
                    ArrayList<GraphNode> rightPath = new ArrayList<>();
                    for(GraphNode pathNode:onePath){
                        rightPath.add(pathNode);
                    }
                    Collections.reverse(rightPath); //路是反的
                    allPaths.add(rightPath);
                    if(shortestPathLength==-1||onePath.size()<=shortestPathLength){
                        if(onePath.size()==shortestPathLength){
                            shortestPath.add(rightPath);
                        }else {
                            shortestPathLength = onePath.size();
                            shortestPath.clear();
                            shortestPath.add(rightPath);
                        }
                    }
                }else{
                    DFS(node, des);
                    node.getNode().setVisited(false);
                }
                onePath.remove(node);
            }
        }
    }

    @Override
    public ResponseVO getFunctionNameLike(String name, int projectId){
        nodes = nodeMapper.selectNodeByProjectId(projectId);
        ArrayList<String> all_matched_funcs = new ArrayList<String>();
        String tmp;
        for(int i = 0; i< nodes.size(); i++){
            tmp = nodes.get(i).getName();
            if(tmp.indexOf(name)!=-1){
                all_matched_funcs.add(nodes.get(i).getName());
            }
        }

        if(all_matched_funcs.size()==0){
            return ResponseVO.buildFailure("No functions name like "+name);
        }
        return ResponseVO.buildSuccess(all_matched_funcs);
    }

    public void sortByLength(ArrayList<ArrayList<GraphNode>> paths){
        Comparator c = new Comparator<ArrayList<GraphNode>>() {
            @Override
            public int compare(ArrayList<GraphNode> a1, ArrayList<GraphNode> a2) {
                if(a1.size()>a2.size()){
                    return 1;
                }else{
                    return -1;
                }
            }
        };
        paths.sort(c);
    }

}
