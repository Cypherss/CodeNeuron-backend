package com.example.codeneuron.Controller.Calculate;

import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.VO.RequirementForm;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graph")
public class GraphController {
    GraphCal graphCal;
    @Autowired
    public GraphController(GraphCal graphCal){this.graphCal=graphCal;}

    @RequestMapping(value = "/nodecount", method = RequestMethod.GET)
    public ResponseVO getNodesCount(@RequestParam("projectId") int projectId){
        return graphCal.NodesCount(projectId);
    }

    @RequestMapping(value = "/edgecount", method = RequestMethod.GET)
    public ResponseVO getEdgesCount(@RequestParam("projectId") int projectId){
        return graphCal.EdgesCount(projectId);
    }

    @RequestMapping(value = "/allNodes",method = RequestMethod.GET)
    public ResponseVO getAllNodes(@RequestParam("projectId")int projectId){
        return graphCal.allNodes(projectId);
    }

    @RequestMapping(value = "/allEdges",method = RequestMethod.GET)
    public ResponseVO getAllEdges(@RequestParam("projectId")int projectId){
        return graphCal.allEdges(projectId);
    }

    @RequestMapping(value = "/trace",method = RequestMethod.GET)
    public ResponseVO requirementTrace(@RequestParam("projectId")int projectId) {
        return graphCal.requirementTrace(projectId);
    }

    @RequestMapping(value = "/addRequirement",method = RequestMethod.POST)
    public ResponseVO addRequirement(@RequestBody RequirementForm requirementForm){
        return graphCal.addRequirement(requirementForm);
    }

    @RequestMapping(value = "/deleteRequirement",method = RequestMethod.GET)
    public ResponseVO deleteRequirement(@RequestParam("requirementId")int requirementId,@RequestParam("projectId")int projectId,@RequestParam("nodeId")int nodeId){
        return graphCal.deleteRequirement(requirementId,projectId,nodeId);
    }

}
