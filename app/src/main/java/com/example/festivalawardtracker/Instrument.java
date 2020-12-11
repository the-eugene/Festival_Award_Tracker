package com.example.festivalawardtracker;

import java.util.Arrays;

/**
 * @author Eugene
 */
public enum Instrument {
    piano, violin, viola, cello, voice, newcomer
    ;

    /**
     * @return String array of instrument names from this enumerator, which can be used in a dropdown
     */
    public static String[] Options() {

        String[] options = new String[Instrument.values().length];

        for (int i = 0; i < Instrument.values().length; i++) {
            options[i] = Instrument.values()[i].toString();
            options[i] = options[i].substring(0, 1).toUpperCase() + options[i].substring(1);
        }

        return options;
    }

    /**
     * @return String with the first letter capitalized
     */
    public String ToCapitalizedString(){
        String string= this.toString();
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
