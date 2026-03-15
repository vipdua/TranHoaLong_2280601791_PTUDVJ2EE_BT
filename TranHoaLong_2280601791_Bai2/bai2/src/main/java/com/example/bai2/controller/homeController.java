package com.example.bai2.controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class homeController {

    @GetMapping("/hello")
    public String index(Model model) {
        model.addAttribute("message", "Day la message tu Controller");
        model.addAttribute("title", "Trang chu");
        return "index";
    }

    @GetMapping("/home")
    public String home(Principal principal) {
        return "redirect:/products";
    }

    @GetMapping("/access-denied")
    public String accessDenied(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error",
                "Bạn không có quyền truy cập chức năng này!");

        return "redirect:/products";
    }
}