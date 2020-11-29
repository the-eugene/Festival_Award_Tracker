package com.example.festivalawardtracker.ui;

import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Use this class for reusable custom methods.
 * It can be used for constants and methods as well.
 * @author Carlos
 * @see <a href="https://stackoverflow.com/questions/11603412/android-how-to-create-a-reusable-function">How to create a reusable methods in Android</a>
 */
public final class Utilities {

    // Private constructor to prevent instantiation
    private Utilities() {}

    /* CUSTOM METHODS start here */

    /**
     * It parses a string date in the given format into a LocalDate data type.
     * @author Carlos
     * @param date Any date from the UI, specifically from any given Material Design Date Picker
     * @return localDate Returns the given date in LocalDate type
     */
    public static LocalDate stringToLocalDate(String date) {
        /*
         * MaterialDatePicker: https://developer.android.com/reference/com/google/android/material/datepicker/MaterialDatePicker.Builder?authuser=1
         * Date parsing issue: https://docs.oracle.com/javase/tutorial/datetime/iso/format.html
         */
        DateTimeFormatter pattern;
        LocalDate localDate = null;

        try {

            try {
                pattern = DateTimeFormatter.ofPattern("MMM dd, yyyy");
                localDate = LocalDate.parse(date, pattern);
            } catch (DateTimeParseException e1) {
                Log.e("DATE_TIME_PARSING", "Failed at first case.");
            }

            try {
                pattern = DateTimeFormatter.ofPattern("MMM d, yyyy");
                localDate = LocalDate.parse(date, pattern);
            } catch (DateTimeParseException e2) {
                Log.e("DATE_TIME_PARSING", "Failed at second case.");
            }

        } catch (DateTimeParseException e2) {
            Log.wtf("DATE_TIME_PARSING", "Something terrible has just happened.");
        }
        return localDate;
    } // End of stringToLocalDate()

}
