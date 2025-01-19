package com.example.aloevibe.service;

public interface RecaptchaService {

    boolean verifyRecaptchaToken(String captchaResponse);
}
