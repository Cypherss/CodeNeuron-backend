package com.example.codeneuron.Service.CalculateService.Dynamic;

import com.example.codeneuron.VO.ResponseVO;

public interface StructureCal {
    /**
     * 获取指定项目的文件结构
     * @param projectId
     * @return
     */
    public ResponseVO getProjectStructure(int projectId);
}
