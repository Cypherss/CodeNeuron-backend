package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Project;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface ProjectMapper {

    public int createProject(Project project);

    public int deleteProject(int id);

    public int deleteProjectForTeam(int teamId);

    public int updateProject(Project project);

    public int updateThreshold(int id, double newThreshold);

    public Project findProjectById(int id);

    public List<Project> findProjectByTeamId(int teamId);

    public List<Project> findProjectByUserId(int userId);

}
