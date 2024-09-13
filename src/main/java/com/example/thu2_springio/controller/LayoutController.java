package com.example.thu2_springio.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("{api-prefix}/layout")
public class LayoutController {
    @Operation(summary = "Get Layout", description = "Get a layout")
    @GetMapping("/layout1")
    public String layout() {
        return "layout";
    }

    @Operation(summary = "Get Layout 2", description = "Get a layout 2")
    @GetMapping("/layout2")
    public String layout2() {
        return "layout2";
    }
}
