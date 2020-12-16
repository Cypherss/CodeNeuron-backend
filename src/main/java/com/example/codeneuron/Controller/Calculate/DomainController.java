package com.example.codeneuron.Controller.Calculate;

import com.example.codeneuron.PO.Domain;
import com.example.codeneuron.Service.CalculateService.Dynamic.DomainCal;
import com.example.codeneuron.VO.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/domain")
public class DomainController {
    DomainCal domainCal;
    @Autowired
    public DomainController(DomainCal domainCal){this.domainCal=domainCal;}

    @RequestMapping(value = "/setThreshold", method = RequestMethod.POST)
    public ResponseVO setThreshold(@RequestParam("threshold") double threshold, @RequestParam("projectId") int projectId){
        return domainCal.setThreshold(threshold,projectId);
    }

    @RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
    public ResponseVO getCurrentDomainAndNodes(@RequestParam("projectId")int projectId){
        return domainCal.getDomains(projectId);
    }

    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    public ResponseVO getCount(@RequestParam("threshold")double threshold, @RequestParam("projectId")int projectId){
        return domainCal.getDomainCountByThresholdAndProjectId(threshold,projectId);
    }
}
