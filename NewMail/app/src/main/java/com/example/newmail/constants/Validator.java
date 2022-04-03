package com.example.newmail.constants;

import java.util.regex.Pattern;

public class Validator {
    public static boolean phoneNumberValidate(String phoneNumber) {
        if (phoneNumber.matches("\\d{10}")) return true;
        else if (phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
        else if (phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
        else if (phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;
        else return false;
    }

    public static boolean emailValidate(String email) {
        String regex ="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern
                .compile(regex)
                .matcher(email)
                .matches();
    }
}
