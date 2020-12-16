package com.example.codeneuron.Service.Team;

import com.example.codeneuron.VO.JoinForm;
import com.example.codeneuron.VO.ResponseVO;
import com.example.codeneuron.VO.TeamForm;

public interface TeamService {
    /**
     * 根据组id获取组长
     * @param teamId
     * @return
     */
    public ResponseVO getLeaderByTeamId(int teamId);


    /**
     * 根据组id获取所有组员
     * @param teamId
     * @return
     */
    public ResponseVO getAllUsersByTeamId(int teamId);


    /**
     * 根据用户id获取所有参与组
     * @param userId
     * @return
     */
    public ResponseVO getAllTeamsByUserId(int userId);

    /**
     * 根据组长id获取所有组
     * @param leaderId
     * @return
     */
    public ResponseVO getAllTeamsByLeaderId(int leaderId);

    public ResponseVO getTeamById(int teamId);

    public ResponseVO deleteTeamById(int teamId);

    /**
     * 删除组长的所有组
     * @param leaderId
     * @return
     */
    public ResponseVO deleteTeamByLeaderId(int leaderId);

    public ResponseVO createTeamForLeader(TeamForm teamForm);

    public ResponseVO joinTeam(JoinForm joinForm);

}
