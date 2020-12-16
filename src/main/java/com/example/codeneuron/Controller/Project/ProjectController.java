package com.example.codeneuron.Controller.Project;
import com.example.codeneuron.Service.Project.ProjectService;
import com.example.codeneuron.VO.ProjectForm;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/project")
public class ProjectController {
    ProjectService projectService;
    @Autowired
    public ProjectController(ProjectService projectService){
        this.projectService=projectService;
    }


    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public ResponseVO getProjectInfo(@RequestParam("id") int id){
        return projectService.getProjectById(id);
    }

    @RequestMapping(value = "/team", method = RequestMethod.GET)
    public ResponseVO getProjectForTeam(@RequestParam("teamId") int teamId){
        return projectService.getAllProjectsByTeamId(teamId);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseVO getProjectForUser(@RequestParam("userId") int userId){
        return projectService.getAllProjectsByUserId(userId);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseVO deleteProjectById(@RequestParam("projectId") int projectId){
        return projectService.deleteProjectById(projectId);
    }

    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseVO deleteProjectForTeam(@RequestParam("teamId") int teamId){
        return projectService.deleteProjectByTeamId(teamId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseVO createProjectForTeam(@RequestBody ProjectForm projectForm){
        return projectService.createProjectForTeam(projectForm);
    }

    @RequestMapping(value = "/edges", method = RequestMethod.GET)
    public ResponseVO getEdgeForProject(@RequestParam("projectId") int projectId){
        return projectService.getEdgeForProject(projectId);
    }

    @RequestMapping(value = "/nodes", method = RequestMethod.GET)
    public ResponseVO getNodeForProject(@RequestParam("projectId") int projectId){
        return projectService.getNodeForProject(projectId);
    }
}
