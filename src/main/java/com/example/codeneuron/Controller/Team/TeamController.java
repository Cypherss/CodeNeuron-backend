package com.example.codeneuron.Controller.Team;


import com.example.codeneuron.Service.Team.TeamService;
import com.example.codeneuron.VO.JoinForm;
import com.example.codeneuron.VO.ResponseVO;
import com.example.codeneuron.VO.TeamForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamController {
    TeamService teamService;
    @Autowired
    public TeamController(TeamService teamService){
        this.teamService=teamService;
    }

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public ResponseVO getTeamInfo(@RequestParam("id") int id){
        return teamService.getTeamById(id);
    }

    @RequestMapping(value= "/teammates",method = RequestMethod.GET)
    public ResponseVO getTeammates(@RequestParam("userId") int userId) { return teamService.getAllUsersByTeamId(userId);}

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseVO getTeamForUser(@RequestParam("userId") int userId){
        return teamService.getAllTeamsByUserId(userId);
    }

    @RequestMapping(value = "/own",method = RequestMethod.GET)
    public ResponseVO getTeamForLeader(@RequestParam("leaderId")int leaderId){
        return teamService.getAllTeamsByLeaderId(leaderId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseVO createTeamForLeader(@RequestBody TeamForm teamForm){
        return teamService.createTeamForLeader(teamForm);
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public ResponseVO joinTeam(@RequestBody JoinForm joinForm){
        return teamService.joinTeam(joinForm);
    }
}
