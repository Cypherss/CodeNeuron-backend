package com.example.codeneuron.Controller.Calculate;

import com.example.codeneuron.Service.CalculateService.Static.CodeAnalysis;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/codeAnalysis")
public class CodeAnalysisController {
    @Autowired
    CodeAnalysis codeAnalysis;
    @RequestMapping(value = "/uploadzip",method = RequestMethod.POST)
    public ResponseVO uploadZipFile(@RequestParam("file") MultipartFile zipFile, @RequestParam("id")int projectId){
        return codeAnalysis.zipFileAnalysis(zipFile, projectId);
    }
    @RequestMapping(value = "/uploadjar", method = RequestMethod.POST)
    public ResponseVO uploadJarFile(@RequestParam("file") MultipartFile jarFile,@RequestParam("id")int projectId){
        return codeAnalysis.jarFileAnalysis(jarFile, projectId);
    }

    @RequestMapping(value="/getcode",method = RequestMethod.GET)
    public ResponseVO getNodeCode(@RequestParam("nodeId") int nodeId){
        return codeAnalysis.getNodeCode(nodeId);
    }
}
