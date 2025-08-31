package com.mindnova.utils;

import java.util.regex.Pattern;

public class PasswordStrengthValidator {
    private static PasswordStrengthValidator instance;

    // Regex kiểm tra độ mạnh (ít nhất 8 ký tự, gồm chữ thường, chữ hoa, số, ký tự đặc biệt)
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

    private final Pattern pattern;

    private PasswordStrengthValidator() {
        this.pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    public static synchronized PasswordStrengthValidator getInstance() {
        if (instance == null) {
            instance = new PasswordStrengthValidator();
        }
        return instance;
    }

    public boolean isStrong(String password) {
        return pattern.matcher(password).matches();
    }
}
