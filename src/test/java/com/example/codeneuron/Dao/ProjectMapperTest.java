package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Project;
import com.example.codeneuron.VO.ProjectForm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProjectMapperTest {
    @Autowired
    ProjectMapper projectMapper;

    @Test
    public void createProject() {
        ProjectForm projectForm = new ProjectForm();
        projectForm.setName("project 2");
        projectForm.setClosenessThreshold(0.5);
        projectForm.setTeamId(1);
        Project project = new Project(projectForm);
        Assert.assertEquals(1,projectMapper.createProject(project));
    }

    @Test
    public void deleteProject() {
        ProjectForm projectForm = new ProjectForm();
        projectForm.setName("Test delete project");
        projectForm.setClosenessThreshold(1);
        projectForm.setTeamId(1);
        Project project = new Project(projectForm);
        projectMapper.createProject(project);
        Assert.assertEquals(1,projectMapper.deleteProject(project.getId()));
    }

    @Test
    public void deletProjectForUserTest(){
        projectMapper.deleteProjectForTeam(1);
        Assert.assertEquals(0, projectMapper.findProjectByUserId(1).size());
        for(int i = 0;i<100;i++){
            ProjectForm projectForm = new ProjectForm();
            projectForm.setName("Test delete project"+(i+""));
            projectForm.setClosenessThreshold(1);
            projectForm.setTeamId(1);
            Project project = new Project(projectForm);
            projectMapper.createProject(project);
        }
        Assert.assertEquals(100,projectMapper.findProjectByUserId(1).size());
        projectMapper.deleteProjectForTeam(1);
        Assert.assertEquals(0,projectMapper.findProjectByUserId(1).size());
    }

    @Test
    public void updateProject() {
        ProjectForm projectForm = new ProjectForm();
        projectForm.setName("Test update project");
        projectForm.setClosenessThreshold(1);
        projectForm.setTeamId(1);
        Project project = new Project(projectForm);
        projectMapper.createProject(project);
        project.setName("After update");
        project.setTeamId(2);
        project.setClosenessThreshold(0);
        Assert.assertEquals(1,projectMapper.updateProject(project));
        Project result = projectMapper.findProjectById(project.getId());
        Assert.assertEquals("After update",result.getName());
        Assert.assertEquals(0,result.getClosenessThreshold(),0.0);
        Assert.assertEquals(2,result.getTeamId());

    }

    @Test
    public void updateThreshold() {
        ProjectForm projectForm = new ProjectForm();
        projectForm.setName("Test update project");
        projectForm.setClosenessThreshold(1);
        projectForm.setTeamId(1);
        Project project = new Project(projectForm);
        projectMapper.createProject(project);
        project.setClosenessThreshold(0);
        Assert.assertEquals(1, projectMapper.updateThreshold(project.getId(),0));
        Project result = projectMapper.findProjectById(project.getId());
        Assert.assertEquals(0, result.getClosenessThreshold(),0.0);
    }

    @Test
    public void findProjectById() {
        ProjectForm projectForm = new ProjectForm();
        projectForm.setName("Test find project");
        projectForm.setClosenessThreshold(1);
        projectForm.setTeamId(1);
        Project project = new Project(projectForm);
        projectMapper.createProject(project);
        Project result = projectMapper.findProjectById(project.getId());
        Assert.assertEquals("Test find project",result.getName());
        Assert.assertEquals(1,result.getTeamId());
        Assert.assertEquals(1,result.getClosenessThreshold(),0.0);
    }

    @Test
    public void findProjectByUserId() {
        int before = projectMapper.findProjectByUserId(1).size();
        for(int i = 0;i<10;i++){
            ProjectForm projectForm = new ProjectForm();
            projectForm.setName("Test find project"+(i+""));
            projectForm.setClosenessThreshold(1);
            projectForm.setTeamId(1);
            Project project = new Project(projectForm);
            projectMapper.createProject(project);
        }
        int after = projectMapper.findProjectByUserId(1).size();
        Assert.assertEquals(before+10,after);
    }
}