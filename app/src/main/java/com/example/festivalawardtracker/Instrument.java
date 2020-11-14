package com.example.festivalawardtracker;

import java.util.Arrays;

public enum Instrument {
    piano, violin, viola, cello
    ;

    public static String[] Options() {
        String[] options=new String[Instrument.values().length];
        for (int i=0; i<Instrument.values().length; i++) {
            options[i]=Instrument.values()[i].toString();
            options[i]=options[i].substring(0, 1).toUpperCase() + options[i].substring(1);
        }
        return options;
    }
}
