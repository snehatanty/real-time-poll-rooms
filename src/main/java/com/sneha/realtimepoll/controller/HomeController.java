package com.sneha.realtimepoll.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  @GetMapping("/")
  public String home() {
    return "Real-Time Poll Rooms API is running ✅";
  }
}
