package com.example.demo.Tusp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainConn {
    
    @GetMapping("/")
    public String index(){
        return "index";
    }

}
