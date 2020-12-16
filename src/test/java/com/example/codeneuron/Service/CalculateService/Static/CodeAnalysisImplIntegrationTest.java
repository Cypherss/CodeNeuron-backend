package com.example.codeneuron.Service.CalculateService.Static;

import com.example.codeneuron.Dao.EdgeMapper;
import com.example.codeneuron.Dao.NodeMapper;
import com.example.codeneuron.Dao.Node_codeMapper;
import com.example.codeneuron.Service.CalculateService.Dynamic.DomainCal;
import com.example.codeneuron.Service.CalculateService.Dynamic.GraphCal;
import com.example.codeneuron.Service.InitGraph.InitGraph;
import com.example.codeneuron.ServiceImpl.CalculateService.Static.CodeAnalysisImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CodeAnalysisImplIntegrationTest {
    @Autowired
    NodeMapper nodeMapper;
    @Autowired
    EdgeMapper edgeMapper;
    @Autowired
    Node_codeMapper node_codeMapper;
    @Autowired
    DomainCal domainCal;
    @Autowired
    InitGraph initGraph;
    @Autowired
    GraphCal graphCal;
    @Autowired
    CodeAnalysis codeAnalysis;
    @Test
    public void codeAnalysis() throws Exception{
        String zipFileName="src/test/resources/backend-codeneuron.zip";
        File zipFile=new File(zipFileName);
        InputStream zipInputStream=new FileInputStream(zipFile);
        MultipartFile zipMultipartFile=new MockMultipartFile(zipFile.getName(),zipInputStream);

        String jarFileName="src/test/resources/codeneuron-0.0.1-SNAPSHOT.jar";
        File jarFile=new File(jarFileName);
        InputStream jarInputStream=new FileInputStream(jarFile);
        MultipartFile jarMultiPartFile=new MockMultipartFile(jarFile.getName(),jarInputStream);

        codeAnalysis.zipFileAnalysis(zipMultipartFile,2);
        codeAnalysis.jarFileAnalysis(jarMultiPartFile, 2);
    }
//    @Test
//    public void cal() throws Exception{
//        CodeAnalysisImpl codeAnalysisImpl = new CodeAnalysisImpl(nodeMapper,edgeMapper,node_codeMapper,domainCal,initGraph,graphCal);
////        codeAnalysisImpl.test();
//    }
}
