package com.example.aloevibe.util;

public enum Constants {
    ;
    public static final String PASSWORD_STRENGTH = "Паролата трябва да съдържа главна и малка буква от латинската азбука, цифра и специален символ(@$!%*#?&).";
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
}
