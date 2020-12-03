package com.example.festivalawardtracker;

/**
 *
 * @author Eugene
 */
public enum Answer {
    Yes, No
    ;

    public static String[] Options() {

        String[] options = new String[Answer.values().length];

        for (int i = 0; i < Answer.values().length; i++) {
            options[i] = Answer.values()[i].toString();
            options[i] = options[i].substring(0, 1).toUpperCase() + options[i].substring(1);
        }

        return options;
    }
}
