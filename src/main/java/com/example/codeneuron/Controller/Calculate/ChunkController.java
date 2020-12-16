package com.example.codeneuron.Controller.Calculate;

import com.example.codeneuron.Service.CalculateService.Static.ChunkService;
import com.example.codeneuron.VO.MultipartFileParam;
import com.example.codeneuron.VO.NotSameFileExpection;
import com.example.codeneuron.VO.StdOut;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@RestController
@RequestMapping("/chunk")
public class ChunkController {
    @Autowired
    ChunkService chunkService;

    @RequestMapping("/chunkUpload/{projectId}")
    public StdOut chunkUpload(MultipartFileParam param, @PathVariable("projectId") int projectId, HttpServletRequest request, HttpServletResponse response) {
        StdOut out = new StdOut();


        File file = new File("/home/codeneuron/docker");//存储路径


        String path = file.getAbsolutePath();
        response.setContentType("text/html;charset=UTF-8");

        try {
            //判断前端Form表单格式是否支持文件上传
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (!isMultipart) {
                out.setCode(StdOut.PARAMETER_NULL);
                out.setMessage("表单格式错误");
                return out;
            } else {
                param.setTaskId(param.getIdentifier());
                out.setModel(chunkService.chunkUploadByMappedByteBuffer(param, path,projectId));
                return out;
            }
        } catch (NotSameFileExpection e) {
            out.setCode(StdOut.FAIL);
            out.setMessage("MD5校验失败");
            return out;
        } catch (Exception e) {
            out.setCode(StdOut.FAIL);
            out.setMessage("上传失败");
            return out;
        }
    }
}


