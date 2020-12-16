package com.example.codeneuron.Service.CalculateService.Dynamic;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.PO.Edge;
import com.example.codeneuron.PO.GraphNode;
import com.example.codeneuron.PO.Node;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
* PathCalImpl Tester.
*
* @author <Authors name>
* @since <pre>2�� 25, 2020</pre>
* @version 1.0
*/
//这个是用真实数据测的
@RunWith(SpringRunner.class)
@SpringBootTest
public class PathCalTest {

    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    InitGraph initGraph;
    @Autowired
    PathCal pathCal;
    List<Node> nodes;
    List<Edge> edges;
    @Before
    public void before() throws Exception {
        nodes=nodeMapper.selectNodeByProjectId(1);
        edges=edgeMapper.selectEdgeByProjectId(1);
        initGraph.InitAdjacencyTable(nodes,edges);

    }

    @Test
    public void testGetFunctionName() throws Exception {
        String data = "HospitalBean";
        Assert.assertEquals(19
                    ,((ArrayList<String>)pathCal.getFunctionNameLike(data,1).getContent()).size());

    }
    /**
    *
    * Method: findPath(String src, String des)
    *
    */
    @Test
    public void testFindPath() throws Exception {
        String from = "edu.ncsu.csc.itrust.action.ManageHospitalAssignmentsAction:getAvailableHospitals(java.lang.String)";
        String to = "edu.ncsu.csc.itrust.beans.HospitalBean:<init>(java.lang.String,java.lang.String)";

        ArrayList<ArrayList<GraphNode>> all = (ArrayList<ArrayList<GraphNode>>)pathCal.searchAllPaths(from,to,1).getContent();
        ArrayList<ArrayList<GraphNode>> shortest = (ArrayList<ArrayList<GraphNode>>)pathCal.searchShortestPath(from,to,1).getContent();

        Assert.assertEquals(2, all.size());
        Assert.assertEquals(2, shortest.size());
//        for(int i = 0;i< PathCalImpl.shortestPath.size();i++){
//            PathCalImpl.printPath(PathCalImpl.shortestPath.get(i));
//        }
    //    Assert.assertEquals(3,PathCalImpl.allPaths.size());
    //    Assert.assertEquals("1-0->2-0->5-0->7",PathCalImpl.printPath(PathCalImpl.shortestPath.get(0)));

    }


}
