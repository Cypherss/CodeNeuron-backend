package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Team;
import com.example.codeneuron.PO.TeamUser;
import com.example.codeneuron.PO.User;
import com.example.codeneuron.VO.TeamForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamMapperTest {
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    AccountMapper accountMapper;
    @Test
    public void createTeam(){
        Team team=new Team(new TeamForm("projectTest",1));
        teamMapper.createTeam(team);
        Assert.assertEquals(1,teamMapper.getTeamByTeamId(team.getId()).getLeaderId());
        Assert.assertEquals("projectTest",teamMapper.getTeamByTeamId(team.getId()).getName());
    }
    @Test
    public void deleteTeam(){
        Team team=new Team(new TeamForm("projectTest",1));
        teamMapper.createTeam(team);
        teamMapper.deleteTeam(team.getId());
        Assert.assertNull(teamMapper.getTeamByTeamId(team.getId()));
    }

    @Test
    public void deleteTeamUser(){
        Team team=new Team(new TeamForm("projectTest",1));
        teamMapper.createTeam(team);
        TeamUser teamUser=new TeamUser(team.getId(),2);
        teamMapper.addUserForTeam(teamUser);
        Assert.assertEquals(1,teamMapper.findTeamUserById(team.getId()).size());
        teamMapper.deleteTeamUser(team.getId());
        Assert.assertEquals(0,teamMapper.findTeamUserById(team.getId()).size());
    }
    @Test
    public void deleteTeamForLeader(){
        Team team = new Team();
        for(int i=0;i<100;i++) {
            team.setName("projectTest"+i);
            team.setLeaderId(1);
            teamMapper.createTeam(team);
        }
        teamMapper.deleteTeamForLeader(1);
        Assert.assertEquals(0,teamMapper.findTeamsByLeaderId(1).size());
    }
    @Test
    public void deleteTeamUserForLeader(){
        Team team1=new Team();
        team1.setName("projectTest1");
        team1.setLeaderId(1);
        teamMapper.createTeam(team1);
        for(int i=0;i<100;i++) {
            TeamUser teamUser = new TeamUser(team1.getId(), i);
            teamMapper.addUserForTeam(teamUser);
        }
        Team team2=new Team();
        team2.setName("projectTest2");
        team2.setLeaderId(1);
        teamMapper.createTeam(team2);
        for(int i=0;i<100;i++) {
            TeamUser teamUser = new TeamUser(team2.getId(), i);
            teamMapper.addUserForTeam(teamUser);
        }
        teamMapper.deleteTeamUserForLeader(1);
        Assert.assertEquals(0, teamMapper.findTeamUserById(team1.getId()).size());
        Assert.assertEquals(0, teamMapper.findTeamUserById(team2.getId()).size());
    }
    @Test
    public void addUserForTeam(){
        Team team=new Team();
        team.setName("projectTest1");
        team.setLeaderId(1);
        teamMapper.createTeam(team);
        for(int i=0;i<100;i++) {
            User user=new User("user"+i,"password"+i);
            accountMapper.createNewUser(user);
            TeamUser teamUser = new TeamUser(team.getId(), user.getId());
            teamMapper.addUserForTeam(teamUser);
        }
        Assert.assertEquals(100, teamMapper.findTeamUserById(team.getId()).size());
    }
    @Test
    public void deleteUserForTeam(){
        Team team=new Team();
        team.setName("projectTest1");
        team.setLeaderId(1);
        teamMapper.createTeam(team);
        User user=new User("user"+1,"password");
        accountMapper.createNewUser(user);
        TeamUser teamUser = new TeamUser(team.getId(), user.getId());
        teamMapper.addUserForTeam(teamUser);
        teamMapper.deleteUserForTeam(teamUser);
        Assert.assertEquals(0, teamMapper.findTeamUserById(team.getId()).size());
    }
    @Test
    public void findTeamsByLeaderId(){
        int before=teamMapper.findTeamsByLeaderId(1).size();
        Team team = new Team();
        for(int i=0;i<100;i++) {
            team.setName("projectTest"+i);
            team.setLeaderId(1);
            teamMapper.createTeam(team);
        }
        int after=before+100;
        Assert.assertEquals(after,teamMapper.findTeamsByLeaderId(1).size());
    }
    @Test
    public void findTeamsByUserId(){
        int before=teamMapper.findTeamsByUserId(2).size();
        for(int i=0;i<100;i++) {
            Team team = new Team();
            team.setName("projectTest"+i);
            team.setLeaderId(1);
            teamMapper.createTeam(team);
            TeamUser teamUser=new TeamUser(team.getId(),2);
            teamMapper.addUserForTeam(teamUser);
        }
        int after=teamMapper.findTeamsByUserId(2).size();
        Assert.assertEquals(100+before,after);
    }
    @Test
    public void findUsersByTeamId(){
        int before=teamMapper.findUsersByTeamId(1).size();
        for(int i=0;i<100;i++) {
            User user=new User("testname"+i,"password"+i);
            accountMapper.createNewUser(user);
            TeamUser teamUser=new TeamUser(1,user.getId());
            teamMapper.addUserForTeam(teamUser);
        }
        int after=teamMapper.findUsersByTeamId(1).size();
        Assert.assertEquals(100+before,after);
    }
    @Test
    public void getLeaderByTeamId(){
        User user=new User("userTest","password");
        accountMapper.createNewUser(user);
        Team team = new Team();
        team.setName("projectTest");
        team.setLeaderId(user.getId());
        teamMapper.createTeam(team);
        Assert.assertEquals(user.getId(),teamMapper.getLeaderByTeamId(team.getId()).getId());
        Assert.assertEquals("userTest",teamMapper.getLeaderByTeamId(team.getId()).getName());
        Assert.assertEquals("password",teamMapper.getLeaderByTeamId(team.getId()).getPassword());
    }
    @Test
    public void getTeamByTeamId(){
        Team team = new Team();
        team.setName("projectTest");
        team.setLeaderId(1);
        teamMapper.createTeam(team);
        Assert.assertEquals("projectTest",teamMapper.getTeamByTeamId(team.getId()).getName());
        Assert.assertEquals(1,teamMapper.getTeamByTeamId(team.getId()).getLeaderId());
    }
}
