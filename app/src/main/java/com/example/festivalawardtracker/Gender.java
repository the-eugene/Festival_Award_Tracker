package com.example.festivalawardtracker;

/**
 * @author Eugene
 */
public enum Gender {
    Male, Female, Unspecified
    ;

    public static String[] Options() {

        String[] options = new String[Gender.values().length];

        for (int i = 0; i < Gender.values().length; i++) {
            options[i] = Gender.values()[i].toString();
            options[i] = options[i].substring(0, 1).toUpperCase() + options[i].substring(1);
        }

        return options;
    }
}
