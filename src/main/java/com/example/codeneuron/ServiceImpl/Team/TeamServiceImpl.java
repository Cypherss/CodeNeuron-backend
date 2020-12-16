package com.example.codeneuron.ServiceImpl.Team;

import com.example.codeneuron.Dao.*;
import com.example.codeneuron.PO.*;
import com.example.codeneuron.Service.Team.TeamService;
import com.example.codeneuron.VO.JoinForm;
import com.example.codeneuron.VO.ResponseVO;
import com.example.codeneuron.VO.TeamForm;
import com.example.codeneuron.VO.TeamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamMapper teamMapper;

    /**
     * 根据组id获取组长
     *
     * @param teamId
     * @return
     */
    @Override
    public ResponseVO getLeaderByTeamId(int teamId) {
        User leader;
        try {
            leader=teamMapper.getLeaderByTeamId(teamId);
        }catch (Exception e) {
            return ResponseVO.buildFailure("Cannot find the team.");
        }
        return ResponseVO.buildSuccess(leader);
    }


    /**
     * 根据组id获取所有组员
     *
     * @param teamId
     * @return
     */
    public ResponseVO getAllUsersByTeamId(int teamId) {
        List<User> userList;
        try{
            userList=teamMapper.findUsersByTeamId(teamId);
        }catch (Exception e){
            return ResponseVO.buildFailure("Cannot find the team.");
        }
        return ResponseVO.buildSuccess(userList);
    }


    /**
     * 根据用户id获取所有参与组
     *
     * @param userId
     * @return
     */
    public ResponseVO getAllTeamsByUserId(int userId) {
        List<Team> teamList;
        try{
            teamList=teamMapper.findTeamsByUserId(userId);
        }catch (Exception e){
            return ResponseVO.buildFailure("Cannot find the team.");
        }
        if(teamList.size()>0){
            return ResponseVO.buildSuccess(teamList);
        }else{
            return ResponseVO.buildFailure("Cannot find the team.");
        }
    }

    /**
     * 根据组长id获取所有组
     *
     * @param leaderId
     * @return
     */
    public ResponseVO getAllTeamsByLeaderId(int leaderId) {
        List<Team> teamList;
        try{
            teamList=teamMapper.findTeamsByLeaderId(leaderId);
        }catch (Exception e){
            return ResponseVO.buildFailure("Cannot find the teams.");
        }
        if(teamList.size()>0){
            return ResponseVO.buildSuccess(teamList);
        }else{
            return ResponseVO.buildFailure("Cannot find the teams.");
        }
    }

    public ResponseVO getTeamById(int teamId) {
        Team team;
        try{
            team=teamMapper.getTeamByTeamId(teamId);
        }catch (Exception e){
            return ResponseVO.buildFailure("Cannot find the teams.");
        }
        return ResponseVO.buildSuccess(team);
    }

    public ResponseVO deleteTeamById(int teamId) {
        try{
            teamMapper.deleteTeam(teamId);
            teamMapper.deleteTeamUser(teamId);
        }catch (Exception e){
            return ResponseVO.buildFailure("Fail to delete.");
        }
        return ResponseVO.buildSuccess();
    }

    /**
     * 删除组长的所有组
     *
     * @param leaderId
     * @return
     */
    public ResponseVO deleteTeamByLeaderId(int leaderId) {
        try{
            teamMapper.deleteTeamForLeader(leaderId);
        }catch (Exception e){
            return ResponseVO.buildFailure("Fail to delete teams for leader.");
        }
        return ResponseVO.buildSuccess();
    }

    public ResponseVO createTeamForLeader(TeamForm teamForm){
        try{
            Team team=new Team(teamForm);
            teamMapper.createTeam(team);
            TeamVO teamVO=new TeamVO(team);
            int id=teamVO.getId();
            int userId=team.getLeaderId();
            TeamUser teamUser=new TeamUser(id,userId);
            teamMapper.addUserForTeam(teamUser);
        }catch (Exception e){
            return ResponseVO.buildFailure("Fail to create.");
        }
        return ResponseVO.buildSuccess();
    }

    public ResponseVO joinTeam(JoinForm joinForm){
        try{
            TeamUser teamUser=new TeamUser(joinForm.getTeamId(),joinForm.getUserId());
            teamMapper.addUserForTeam(teamUser);
        }catch (Exception e){
            return ResponseVO.buildFailure("Fail to join.");
        }
        return ResponseVO.buildSuccess();
    }
}
