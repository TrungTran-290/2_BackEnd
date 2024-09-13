package com.example.thu2_springio.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{api-prefix}/admin")
public class AdminController {
    @GetMapping("/admin1")
    public String admin1(){
        return "Admin 1";
    }

    @GetMapping("/admin2")
    public String admin2(){
        return "Admin 2";
    }
}
