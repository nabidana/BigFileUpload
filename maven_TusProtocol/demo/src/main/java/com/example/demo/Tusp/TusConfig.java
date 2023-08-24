package com.example.demo.Tusp;

import java.io.File;
import java.io.IOException;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;

@Slf4j
@Configuration
public class TusConfig {
    
    String tusStoragePath = System.getProperty("user.dir")+File.separator+"demo"+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"static"+File.separator+"upload";

    Long tusExpirationPeriod = (long) 60000;

    @PreDestroy
    public void exit(){
        try {
            tus().cleanup();
        } catch (IOException e) {
            log.info("CLEAN FAIL : "+e.toString());
        }
    }

    @Bean
    public TusFileUploadService tus() {
        return new TusFileUploadService()
            .withStoragePath(tusStoragePath)
            .withDownloadFeature()
            .withUploadExpirationPeriod(tusExpirationPeriod)
            .withUploadURI("/tus/upload");
    }
}
