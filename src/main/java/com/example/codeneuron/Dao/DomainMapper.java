package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Domain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Mapper
@Service
public interface DomainMapper {

    /**
     * 根据projectId和紧密度获得domain
     * @param projectId
     * @param closenessThreshold
     * @return
     */
    public List<Domain> getDomainByProjectIdAndCloseness(@Param("projectId") int projectId, @Param("closenessThreshold") double closenessThreshold);

    /**
     * 根据projectId获得所有threshold下的Domain，主要是为了批量删除用
     * @param projectId
     * @return
     */
    public List<Domain> getDomainByProjectId(int projectId);

    /**
     * 根据id获得Domain
     * @param id
     * @return
     */
    public Domain getDomainById(int id);

    /**
     * 插入新domain
     * @param domain
     * @return
     */
    public int insertDomain(Domain domain);

    /**
     * 删除某个项目时，将历史domain全部删除
     * @param projectId
     * @return
     */
    public int deleteDomainByProjectId(int projectId);

}
