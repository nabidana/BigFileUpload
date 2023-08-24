package com.example.demo.Tusp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TusConn {
    
    private final TusService tusService;

    @RequestMapping(value = {"/tus/upload", "/tus/upload/**"})
    public ResponseEntity<Object> tsUpload(HttpServletRequest request, HttpServletResponse response) {
        tusService.process(request, response);
        return httpOkStatus();
    }

    private static ResponseEntity<Object> httpOkStatus() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .build();
    }
}
