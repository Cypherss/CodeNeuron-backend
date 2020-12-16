package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Team;
import com.example.codeneuron.PO.TeamUser;
import com.example.codeneuron.PO.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Mapper
@Service
public interface TeamMapper {
    public int createTeam(Team team);

    public int deleteTeam(int teamId);

    public int deleteTeamUser(int teamId);

    public int deleteTeamForLeader(int leaderId);

    public int deleteTeamUserForLeader(int leaderId);

    public List<TeamUser> findTeamUserById(int teamId);

    /**
     * 增加组内用户
     * @param teamUser
     * @return
     */
    public int addUserForTeam(TeamUser teamUser);

    /**
     * 删除组内某成员
     * @param teamUser
     * @return
     */
    public int deleteUserForTeam(TeamUser teamUser);

    public List<Team> findTeamsByLeaderId(int leaderId);

    public List<Team> findTeamsByUserId(int userId);

    public List<User> findUsersByTeamId(int teamId);

    public User getLeaderByTeamId(int teamId);

    public Team getTeamByTeamId(int teamId);
}
