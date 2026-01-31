package com.example.bai2.controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class homeController {

    @GetMapping("/hello")
    public String index(Model model) {
        model.addAttribute("message", "Day la message tu Controller");
        model.addAttribute("title", "Trang chu");
        return "index";
    }
}