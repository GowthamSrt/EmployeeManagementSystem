package com.ideas2it.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *<p>
 * Validating various type of input such as name,email addresses and dates.
 * </p>
 */
public class EmployeeValidator {
    private static final Pattern EmailPattern = Pattern.compile("\\b[A-za-z0-9._%-]"
                                            +"+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
    private static final Pattern NamePattern = Pattern.compile("^^[\\p{L} .'-]+$");
    
    /**
     * <p>
     * Validates the given name is valid and not empty.
     * </p>
     */
    public static boolean isValidName(String Name) {
        if(Name == null) {
            return false;
        }
        Matcher matcher=NamePattern.matcher(Name);
        return matcher.matches();
    }

    /**
     * <p>
     * Validates if the given emailId is valid.
     *</p>
     */
    public static boolean isValidEmail(String emailId) {
        if(emailId.isEmpty()) {
           return false;
         }
         Matcher matcher = EmailPattern.matcher(emailId);
         return matcher.matches();
    }
}