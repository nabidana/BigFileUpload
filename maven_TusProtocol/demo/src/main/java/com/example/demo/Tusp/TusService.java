package com.example.demo.Tusp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.upload.UploadInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class TusService {
    
    String filepath = System.getProperty("user.dir")+File.separator+"demo"+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"upload";
    private final TusFileUploadService tusFileUploadService;

    public void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            tusFileUploadService.process(request, response);
            UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

            if(uploadInfo != null && !uploadInfo.isUploadInProgress()){
                createFile(tusFileUploadService.getUploadedBytes(request.getRequestURI()), uploadInfo.getFileName());
                tusFileUploadService.deleteUpload(request.getRequestURI());
            }
        } catch (Exception e) {
            log.info("TUS ERR : "+e.toString());
        }
    }

    private void createFile(InputStream is, String filename){
        try{
            File file = new File(filepath, filename);
            FileUtils.copyInputStreamToFile(is, file);
        }catch(IOException e){
            log.info("FILE UTIL ERR : "+e.toString());
        }
    }
}
