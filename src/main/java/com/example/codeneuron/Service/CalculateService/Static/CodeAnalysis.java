package com.example.codeneuron.Service.CalculateService.Static;

import com.example.codeneuron.VO.ResponseVO;
import org.springframework.web.multipart.MultipartFile;

public interface CodeAnalysis {

    public ResponseVO jarFileAnalysis(MultipartFile jarFile,int projectId);

    public ResponseVO zipFileAnalysis(MultipartFile zipFile,int projectId);

    public ResponseVO getNodeCode(int nodeId);
}
