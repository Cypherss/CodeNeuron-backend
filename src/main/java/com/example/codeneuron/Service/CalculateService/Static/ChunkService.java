package com.example.codeneuron.Service.CalculateService.Static;

import com.example.codeneuron.VO.MultipartFileParam;
import com.example.codeneuron.VO.NotSameFileExpection;

import java.io.File;
import java.io.IOException;

public interface ChunkService {

    public String chunkUploadByMappedByteBuffer(MultipartFileParam param, String filePath,int projectId) throws IOException, NotSameFileExpection;

    public void renameFile(File toBeRenamed, String toFileNewName);

    public boolean checkUploadStatus(MultipartFileParam param, String fileName, String filePath) throws IOException ;
}
