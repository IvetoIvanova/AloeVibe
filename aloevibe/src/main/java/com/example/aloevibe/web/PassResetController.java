package com.example.aloevibe.web;

import com.example.aloevibe.service.PassResetService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PassResetController {

    private final PassResetService passResetService;
    private final PasswordEncoder passwordEncoder;

    public PassResetController(PassResetService passResetService, PasswordEncoder passwordEncoder) {
        this.passResetService = passResetService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email, RedirectAttributes redirectAttributes) {
        passResetService.createPasswordResetToken(email);
        redirectAttributes.addFlashAttribute("message", "Ако имейлът съществува, ще получиш линк.");
        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        if (!passResetService.isValidToken(token)) {
            model.addAttribute("error", "Невалиден или изтекъл токен.");
            return "error";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token,
                                       @RequestParam String password,
                                       RedirectAttributes redirectAttributes) {
        try {
            passResetService.updatePassword(token, password, passwordEncoder);
            redirectAttributes.addFlashAttribute("message", "Паролата е променена успешно.");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/reset-password?token=" + token;
        }
    }
}
