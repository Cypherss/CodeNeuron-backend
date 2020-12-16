package com.example.codeneuron.Dao;

import com.example.codeneuron.PO.Requirement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Mapper
public interface RequirementMapper {
    /**
     * 创建需求
     * @param requirement
     * @return
     */
    public int createRequirement(Requirement requirement);

    /**
     * 更新需求
     * @param requirement
     * @return
     */
    public int updateRequirement(Requirement requirement);

    /**
     * 选择某个项目下的所有需求
     * @param prejectId
     * @return
     */
    public List<Requirement> selectRequirementsByProjectId(int prejectId);

    /**
     * 根据id选择需求
     * @param id
     * @return
     */
    public Requirement selectRequirementById(int id);

    /**
     * 删除需求
     * @param id
     * @return
     */
    public int deleteRequirement(int id);

    /**
     * 根据名称、描述和项目编号查询需求
     * @param name
     * @param projectId
     * @return
     */
    public Requirement selectRequirementByNameAndProjectId(String name,int projectId);
}
