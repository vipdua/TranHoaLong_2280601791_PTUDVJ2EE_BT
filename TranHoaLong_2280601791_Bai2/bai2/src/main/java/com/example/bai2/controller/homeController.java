package com.example.bai2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class homeController {
    @GetMapping("hello")
    public  String xinChao(){
        return "Long dep zai";
    }
}
