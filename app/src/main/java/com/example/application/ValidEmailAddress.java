package com.example.application;

public interface ValidEmailAddress {
    String VALID_EMAIL_ADDRESS_REGEX = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b";

    public default boolean isValidEmailAddress(String email) {
        if (email.matches(VALID_EMAIL_ADDRESS_REGEX)) {
            return true;
        }
        else return false;
    }
    String getEmail();
}
