package com.example.codeneuron.Controller.Calculate;

import com.example.codeneuron.Service.CalculateService.Dynamic.StructureCal;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StructureController {
    StructureCal structureCal;
    @Autowired
    public StructureController(StructureCal structureCal){
        this.structureCal=structureCal;
    }
    @RequestMapping(value = "/structure",method = RequestMethod.GET)
    public ResponseVO getStructure(@RequestParam("id")int projectId){
        return structureCal.getProjectStructure(projectId);
    }
}
