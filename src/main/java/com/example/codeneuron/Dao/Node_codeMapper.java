package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Node;
import com.example.codeneuron.PO.Node_code;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface Node_codeMapper {
    public int createNode_code(Node_code node_code);
    public Node_code getNode_Code(int nodeId);
    public int updateNode_code(Node_code node_code);
    public int deleteNode_code(@Param("list") List<Node> nodeList);
}
