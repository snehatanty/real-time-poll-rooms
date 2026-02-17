package com.sneha.realtimepoll.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "forward:/index.html";
    }

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        return "Real-Time Poll Rooms API is running ✅";
    }
}

