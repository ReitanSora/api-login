package com.reitansora.apilogin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for validating user input.
 */
public class UserValidation {

    /**
     * Validates an email address using a regular expression pattern.
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public boolean EmailValidator (String email) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9.-]+([_A-Za-z0-9.-]+)*@[\\.a-z]+([\\.a-z]{3})+([\\.a-z]?{3})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    /**
     * Validates a password using a regular expression pattern.
     *
     * @param password the password to validate
     * @return true if the password is valid, false otherwise
     */
    public boolean PasswordValidator (String password) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9.-]{8,}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
