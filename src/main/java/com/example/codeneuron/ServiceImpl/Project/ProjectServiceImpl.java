package com.example.codeneuron.ServiceImpl.Project;

import com.example.codeneuron.Dao.*;
import com.example.codeneuron.PO.*;
import com.example.codeneuron.Service.Project.ProjectService;
import com.example.codeneuron.VO.ProjectForm;
import com.example.codeneuron.VO.ProjectVO;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    DomainMapper domainMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    Node_codeMapper node_codeMapper;

    @Override
    public ResponseVO getAllProjectsByTeamId(int teamId){
        List<Project> projects=projectMapper.findProjectByTeamId(teamId);
        List<ProjectVO> resultProjects = new ArrayList<>();
        for(Project project:projects){
            resultProjects.add(new ProjectVO(project));
        }
        return ResponseVO.buildSuccess(resultProjects);

    }

    public ResponseVO getAllProjectsByUserId(int userId){
        List<Project> projects=projectMapper.findProjectByUserId(userId);
        List<ProjectVO> resultProjects = new ArrayList<>();
        for(Project project:projects){
            resultProjects.add(new ProjectVO(project));
        }
        return ResponseVO.buildSuccess(resultProjects);

    }

    @Override
    public ResponseVO getProjectById(int projectId){
        Project project=projectMapper.findProjectById(projectId);
        if(project!=null) {
            return ResponseVO.buildSuccess(new ProjectVO(project));
        }else{
            return ResponseVO.buildFailure("No project "+(projectId+""));
        }
    }

    @Override
    public ResponseVO deleteProjectById(int projectId){
        List<Node> nodes = nodeMapper.selectNodeByProjectId(projectId);
        List<Edge> edges = edgeMapper.selectEdgeByProjectId(projectId);
        List<Domain> domains = domainMapper.getDomainByProjectId(projectId);
        //先获取comment
        List<Comment> commentOfNodes = commentMapper.getCommentOfNodesForProject(projectId);
        List<Comment> commentOfEdges = commentMapper.getCommentOfEdgesForProject(projectId);
        List<Comment> commentOfDomains = commentMapper.getCommentOfDomainsForProject(projectId);
        //先要把domain、edge、node对应edge_comment、node_comment、domain_comment中条目删掉，写成批量删除
        for(Comment comment:commentOfNodes){
            commentMapper.deleteCommentForNode(comment.getId(),comment.getRelatedId());
            commentMapper.deleteComment(comment.getId());
        }
        for(Comment comment:commentOfEdges){
            commentMapper.deleteCommentForNode(comment.getId(),comment.getRelatedId());
            commentMapper.deleteComment(comment.getId());
        }
        for(Comment comment:commentOfDomains){
            commentMapper.deleteCommentForDomain(comment.getId(),comment.getRelatedId());
            commentMapper.deleteComment(comment.getId());
        }
        //然后删node_code
        if(nodes.size()!=0) {
            node_codeMapper.deleteNode_code(nodes);
        }
        //然后删domain_node
        if(domains.size()!=0) {
            nodeMapper.deleteNodeForDomains(domains);
        }
        //然后删domain、node、edge
        domainMapper.deleteDomainByProjectId(projectId);
        nodeMapper.deleteNodeForProject(projectId);
        edgeMapper.deleteEdgeForProject(projectId);
        //最后删project
        if(projectMapper.deleteProject(projectId)==1) {
            return ResponseVO.buildSuccess("Deleted project " + (projectId + ""));
        }else{
            return ResponseVO.buildFailure("Delete "+(projectId+"")+" failed");
        }
    }

    @Override
    public ResponseVO deleteProjectByTeamId(int teamId){
        List<Project> projects = projectMapper.findProjectByTeamId(teamId);
        for(Project project:projects){
            ResponseVO tmp = deleteProjectById(project.getId());
            if(tmp.getSuccess()==false){
                return ResponseVO.buildFailure("Delete projects for user "+(teamId+"")+" failed");
            }
        }
        return ResponseVO.buildSuccess("Deleted projects for user "+(teamId+""));

    }

    //这里暂时是单纯的创建一个project，具体的初始化在导入jar包、分析的那个方法逻辑里实现？
    @Override
    public  ResponseVO createProjectForTeam(ProjectForm projectForm){
        Project project = new Project(projectForm);
        projectMapper.createProject(project);
        if(project.getId()!=0){
            return ResponseVO.buildSuccess(new ProjectVO(project));
        }else{
            return ResponseVO.buildFailure("Create project failed");
        }
    }

    @Override
    public ResponseVO getEdgeForProject(int projectId){
        List<Edge> edges = edgeMapper.selectEdgeByProjectId(projectId);
        return ResponseVO.buildSuccess(edges);
    }

    @Override
    public ResponseVO getNodeForProject(int projectId){
        List<Node> nodes = nodeMapper.selectNodeByProjectId(projectId);
        return ResponseVO.buildSuccess(nodes);
    }
}
