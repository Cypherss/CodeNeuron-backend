package com.example.codeneuron.ServiceImpl.CalculateService.Dynamic;

import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.PO.TreeNode;
import com.example.codeneuron.Service.CalculateService.Dynamic.StructureCal;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StructureCalImpl implements StructureCal {
    /**
     * 获取指定项目的文件结构
     * @param projectId
     * @return
     */
    NodeMapper nodeMapper;
    @Autowired
    public StructureCalImpl(NodeMapper nodeMapper){
        this.nodeMapper=nodeMapper;
    }

    @Override
    public ResponseVO getProjectStructure(int projectId){
        List<Node> nodeList=nodeMapper.selectNodeByProjectId(projectId);
        try{
            List<TreeNode> tree=builtStructure(nodeList);
            return ResponseVO.buildSuccess(tree);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("error");
        }
    }

    public List<TreeNode> builtStructure(List<Node> nodes){
        Map<String, TreeNode> treeNodeMap=new HashMap<String, TreeNode>();
        List<TreeNode> rootList=new ArrayList<TreeNode>();
        for(Node node:nodes){
            String temp[]=node.getName().split(":");
            //
            String path=temp[0],function=temp[1];
            String packageClass[]=path.split("\\.");
            //System.out.println(packageClass.length);
            String tempFullName;
            for(int i=packageClass.length-1;i>0;i--){
                String fullName[]=new String[2];
                int flag=1;
                for(int j=i-1;j<=i;j++){
                    tempFullName=packageClass[0];
                    for(int k=1;k<=j;k++){
                        tempFullName=tempFullName+"."+packageClass[k];
                    }
                    fullName[j-i+1]=tempFullName;
                    if(!treeNodeMap.containsKey(tempFullName)){
                        String titleTemp[]=tempFullName.split("\\.");
                        TreeNode tempNode;
                        String title;
                        if(titleTemp.length>1){
                            title=titleTemp[titleTemp.length-1];
                        }else {
                            title=tempFullName;
                        }
                        tempNode=new TreeNode(title,tempFullName);
                        treeNodeMap.put(tempFullName,tempNode);
                        if(j==0){
                            rootList.add(tempNode);
                        }
                    }
                }
                for(TreeNode tempNode:treeNodeMap.get(fullName[0]).getChildren()){
                    TreeNode son=treeNodeMap.get(fullName[1]);
                    if(tempNode.getTitle().equals(son.getTitle())){
                        flag=0;
                        break;
                    }
                }
                if (flag==1){
                    treeNodeMap.get(fullName[0]).add(treeNodeMap.get(fullName[1]));
                }
            }
            treeNodeMap.get(temp[0]).add(new TreeNode(function,node.getName()));
        }
        return rootList;
    }
}
