package com.mindnova.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderSingleton {
    private static PasswordEncoder instance;

    private PasswordEncoderSingleton() {}

    public static synchronized PasswordEncoder getInstance() {
        if (instance == null) {
            instance = new BCryptPasswordEncoder();
        }
        return instance;
    }
}
