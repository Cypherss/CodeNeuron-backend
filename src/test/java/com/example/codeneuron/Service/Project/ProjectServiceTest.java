package com.example.codeneuron.Service.Project;

import com.example.codeneuron.Dao.*;
import com.example.codeneuron.PO.*;
import com.example.codeneuron.VO.ProjectForm;
import com.example.codeneuron.VO.ProjectVO;
import com.example.codeneuron.VO.TeamForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProjectServiceTest {
    User user;
    User leader;
    Team team;
    TeamForm teamForm;
    Project project;
    ProjectForm projectForm;

    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    ProjectService projectService;
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    EdgeMapper edgeMapper;
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
        projectForm=new ProjectForm("projecttest",team.getId(),1);
        project=new Project(projectForm);
        projectMapper.createProject(project);
    }
    @Test
    public void getAllProjectsByUserId() {
        List<ProjectVO> projects=(List<ProjectVO>) projectService.getAllProjectsByUserId(user.getId()).getContent();
        Assert.assertEquals("projecttest", projects.get(0).getName());
        Assert.assertEquals(team.getId(), projects.get(0).getTeamId());
        Assert.assertEquals(1,projects.get(0).getClosenessThreshold(),2);
    }

    @Test
    public void getProjectById() {
        ProjectVO projectVO=(ProjectVO)projectService.getProjectById(project.getId()).getContent();
        Assert.assertEquals("projecttest",projectVO.getName());
        Assert.assertEquals(1,projectVO.getClosenessThreshold(),2);
        Assert.assertEquals(team.getId(),projectVO.getTeamId());
    }

    @Test
    public void deleteProjectById() {
        projectService.deleteProjectById(project.getId());
        Assert.assertEquals(null,projectService.getProjectById(project.getId()).getContent());
    }

    @Test
    public void deleteProjectByTeamId() {
        projectService.deleteProjectByTeamId(team.getId());
        Assert.assertEquals(0,((List<Project>)projectService.getAllProjectsByTeamId(team.getId()).getContent()).size());
    }

    @Test
    public void createProjectForTeam() {
        projectService.createProjectForTeam(projectForm);
        List<ProjectVO> projects=(List<ProjectVO>) projectService.getAllProjectsByTeamId(team.getId()).getContent();
        Assert.assertEquals(2,projects.size());
        Assert.assertEquals("projecttest",projects.get(0).getName());
        Assert.assertEquals(1,projects.get(0).getClosenessThreshold(),2);
    }

    @Test
    public void getEdgeForProject() {
        for(int i=0;i<100;i++){
            Node node=new Node();
            node.setName("node"+i);
            node.setProjectId(project.getId());
            nodeMapper.createNewNode(node);
        }
        List<Edge> tempEdges=new ArrayList<Edge>();
        for(int i=0;i<50;i++){
            Edge edge=new Edge();
            edge.setCalleeName("node"+i);
            edge.setCallerName("node"+(i+1));
            edge.setTypeOfCall("M");
            tempEdges.add(edge);
        }
        for(int i=50;i<99;i++){
            Edge edge=new Edge();
            edge.setCalleeName((i+1)+"node");
            edge.setCallerName(i+"node");
            edge.setTypeOfCall("O");
            tempEdges.add(edge);
        }
        edgeMapper.insertEdgeForProject(tempEdges,project.getId());
        List<Edge> edges=(List<Edge>)projectService.getEdgeForProject(project.getId()).getContent();
        for(int i=0;i<50;i++){
            Assert.assertEquals("node"+i,edges.get(i).getCalleeName());
            Assert.assertEquals("node"+(i+1),edges.get(i).getCallerName());
        }
        for(int i=50;i<99;i++){
            Assert.assertEquals((i+1)+"node",edges.get(i).getCalleeName());
            Assert.assertEquals(i+"node",edges.get(i).getCallerName());
        }
    }

    @Test
    public void getNodeForProject() {
        for(int i=0;i<50;i++){
            Node node=new Node();
            node.setName("node"+i);
            node.setProjectId(project.getId());
            nodeMapper.createNewNode(node);
        }
        List<Node> nodes=(List<Node>) projectService.getNodeForProject(project.getId()).getContent();
        for(int i=0;i<50;i++){
            Assert.assertEquals("node"+i,nodes.get(i).getName());
        }
    }
}