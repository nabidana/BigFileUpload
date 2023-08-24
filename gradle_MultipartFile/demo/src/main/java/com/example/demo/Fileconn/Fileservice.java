package com.example.demo.Fileconn;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Fileservice {
    public boolean chunkUpload(MultipartFile file, int chunkNumber, int totalChunks) {
        final String filepath = System.getProperty("user.dir")+File.separator+"demo"+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"upload";
    	// 파일 업로드 위치
        String uploadDir = filepath;
        try{
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 임시 저장 파일 이름
            String filename = file.getOriginalFilename() + ".part" + chunkNumber;

            Path filePath = Paths.get(uploadDir, filename);
            // 임시 저장
            Files.write(filePath, file.getBytes());

            // 마지막 조각이 전송 됐을 경우
            if (chunkNumber == totalChunks-1) {
                String outputFilename = file.getOriginalFilename();
                Path outputFile = Paths.get(uploadDir, outputFilename);
                Files.createFile(outputFile);
                
                // 임시 파일들을 하나로 합침
                for (int i = 0; i < totalChunks; i++) {
                    Path chunkFile = Paths.get(uploadDir, file.getOriginalFilename() + ".part" + i);
                    Files.write(outputFile, Files.readAllBytes(chunkFile), StandardOpenOption.APPEND);
                    // 합친 후 삭제
                    Files.delete(chunkFile);
                }
                log.info("File uploaded successfully");
                return true;
            } else {
                return false;
            }
        }catch (IOException e){
            log.info("ERR : "+e.toString());
            return false;
        }
    }
}
