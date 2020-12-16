package com.example.codeneuron.Service.Team;

import com.example.codeneuron.Dao.AccountMapper;
import com.example.codeneuron.Dao.TeamMapper;
import com.example.codeneuron.PO.Team;
import com.example.codeneuron.PO.TeamUser;
import com.example.codeneuron.PO.User;
import com.example.codeneuron.VO.JoinForm;
import com.example.codeneuron.VO.ResponseVO;
import com.example.codeneuron.VO.TeamForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamServiceTest {
    TeamForm teamForm;
    User leader;
    User user;
    Team team;

    @Autowired
    TeamService teamService;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    TeamMapper teamMapper;
    @Before
    public void init(){
        leader=new User("testLeader","password");
        accountMapper.createNewUser(leader);
        teamForm=new TeamForm("teamTest",leader.getId());
        user=new User("testUser","password");
        accountMapper.createNewUser(user);
        team =new Team(teamForm);
        teamMapper.createTeam(team);
        TeamUser teamUser=new TeamUser(team.getId(),user.getId());
        teamMapper.addUserForTeam(teamUser);
    }
    @Test
    public void getLeaderByTeamId(){
        Assert.assertEquals("testLeader",((User)((ResponseVO)teamService.getLeaderByTeamId(team.getId())).getContent()).getName());
    }
//    @Test
//    public void getAllUsersByTeamId(){
//        Assert.assertEquals(4,((List<User>)((ResponseVO)teamService.getAllUsersByTeamId(1)).getContent()).size());
//    }
    @Test
    public void getAllTeamsByUserId(){
        Assert.assertEquals("teamTest",((List<Team>)((ResponseVO)teamService.getAllTeamsByUserId(user.getId())).getContent()).get(0).getName());
    }
    @Test
    public void getAllTeamsByLeaderId(){
        Assert.assertEquals("teamTest",((List<Team>)((ResponseVO)teamService.getAllTeamsByLeaderId(leader.getId())).getContent()).get(0).getName());
    }
    @Test
    public void getTeamById(){
        Assert.assertEquals("teamTest",((Team)((ResponseVO)teamService.getTeamById(team.getId())).getContent()).getName());
    }
    @Test
    public void deleteTeamById(){
        teamService.deleteTeamById(team.getId());
        Assert.assertNull(teamMapper.getTeamByTeamId(team.getId()));
    }
    @Test
    public void deleteTeamByLeaderId(){
        teamService.deleteTeamByLeaderId(leader.getId());
        Assert.assertEquals(0,teamMapper.findTeamsByLeaderId(leader.getId()).size());
    }
    @Test
    public void createTeamForLeader(){
        teamService.createTeamForLeader(teamForm);
        List<Team> teams=(List<Team>)(((ResponseVO)teamService.getAllTeamsByLeaderId(leader.getId())).getContent());
        Team team=new Team();
        for(Team team1:teams){
            if(team1.getName().equals(teamForm.getName())){
                team=team1;
            }
        }
        int t=leader.getId();
        Assert.assertEquals("teamTest",team.getName());
        Assert.assertEquals(t,team.getLeaderId());
    }
    @Test
    public void joinTeam(){
        JoinForm joinForm=new JoinForm(team.getId(),user.getId());
        teamService.joinTeam(joinForm);
        List<User> users=teamMapper.findUsersByTeamId(team.getId());
        User temp=new User();
        for(User userTemp:users){
            if(userTemp.getId().equals(user.getId())){
                temp=userTemp;
            }
        }
        String tempName=user.getName();
        Assert.assertEquals(tempName,temp.getName());
    }

}
