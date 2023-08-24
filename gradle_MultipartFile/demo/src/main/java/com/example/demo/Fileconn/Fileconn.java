package com.example.demo.Fileconn;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Fileconn {
    private final Fileservice fileservice;
    
    @RequestMapping("/")
    public String homepage(){
        return "home";
    }

    @GetMapping("/chunk")
    public String chunkpage(){
        return "chunk";
    }

    @ResponseBody
    @PostMapping("/chunk/upload")
    public ResponseEntity<String> chunkUpload(@RequestParam("chunk") MultipartFile file,
                                              @RequestParam("chunkNumber") int chunkNumber,
                                              @RequestParam("totalChunks") int totalChunks) throws IOException {
        boolean isDone = fileservice.chunkUpload(file, chunkNumber, totalChunks);

        return isDone ?
                ResponseEntity.ok("File uploaded successfully") :
                ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).build();
    }
}
