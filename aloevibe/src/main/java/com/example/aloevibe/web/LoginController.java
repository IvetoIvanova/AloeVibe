package com.example.aloevibe.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "registered", required = false) String registered,
                        Model model) {

        if (error != null) {
            String errorMessage = "Грешен имейл или парола";
            model.addAttribute("loginError", errorMessage);
        }

        if (registered != null) {
            String successMessage = "Успешна регистрация! Моля, влезте с вашите данни.";
            model.addAttribute("loginSuccess", successMessage);
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", "");
        }
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        model.addAttribute("message", "Инструкции за възстановяване на паролата са изпратени на " + email);
        return "forgot-password";
    }
}
