package com.example.aloevibe.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {

        if (error != null) {
            String errorMessage = "Грешен имейл или парола";
            model.addAttribute("loginError", errorMessage);
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", "");
        }
        return "login";
    }
}
