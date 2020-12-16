package com.example.codeneuron.Controller.Calculate;


import com.example.codeneuron.Service.CalculateService.Dynamic.PathCal;
import com.example.codeneuron.Service.Project.ProjectService;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/path")
public class PathController {
    PathCal pathCal;
    @Autowired
    public PathController(PathCal pathCal){
        this.pathCal=pathCal;
    }

    //ResponseVO的content是ArrayList<ArrayList<Node>>，按路的正确方向排序
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseVO getAllPaths(@RequestParam("source") String source,@RequestParam("target")String target,@RequestParam("projectId")int projectId){
        return pathCal.searchAllPaths(source,target,projectId);
    }

    @RequestMapping(value = "/shortest", method = RequestMethod.GET)
    public ResponseVO getShortestPath(@RequestParam("source") String source,@RequestParam("target")String target,@RequestParam("projectId")int projectId){
        return pathCal.searchShortestPath(source,target,projectId);
    }

    @RequestMapping(value = "/namelike", method = RequestMethod.GET)
    public ResponseVO getFunctionNameLike(@RequestParam("name")String name,@RequestParam("projectId")int projectId){
        return pathCal.getFunctionNameLike(name, projectId);
    }

}
