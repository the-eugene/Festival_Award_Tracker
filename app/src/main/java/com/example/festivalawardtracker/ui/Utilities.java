package com.example.festivalawardtracker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Use this class for reusable custom methods.
 * It can be used for constants and methods as well.
 * @author Carlos, Eugene
 * @see <a href="https://stackoverflow.com/questions/11603412/android-how-to-create-a-reusable-function">How to create a reusable methods in Android</a>
 */
public final class Utilities {
    private static final String TAG = "UTILITIES";
    public static String EVENT_ID="EVENT_ID";
    public static String EVENT_DESCRIPTION_ID="EVENT_DESCRIPTION_ID";
    public static String FESTIVAL_ID="FESTIVAL_ID";
    public static String STUDENT_ID="STUDENT_ID";

    // Private constructor to prevent instantiation
    private Utilities() {
    }

    /* CUSTOM METHODS start here */

    /**
     * It parses a string date in the given format into a LocalDate data type.
     * @param date Any date from the UI, specifically from any given Material Design Date Picker
     * @return localDate Returns the given date in LocalDate type
     * @author Carlos
     */
    public static LocalDate stringMaterialToLocalDate(String date) {
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
                Log.e(TAG, "LocalDate(): Failed at first case.");
            }

            try {
                pattern = DateTimeFormatter.ofPattern("MMM d, yyyy");
                localDate = LocalDate.parse(date, pattern);
            } catch (DateTimeParseException e2) {
                Log.e(TAG, "LocalDate(): Failed at second case.");
            }

        } catch (DateTimeParseException e2) {
            Log.wtf(TAG, "LocalDate(): Something terrible has just happened.");
        }
        return localDate;
    } // End of stringToLocalDate()

    /**
     *
     * @author Eugene, Carlos
     * @param activity the activity
     * @param extra the extra information passed
     * @return if there are any extras included
     */
    public static String retrieveExtra(Activity activity, String extra) {
        Intent intent = activity.getIntent();
        String retrievable;

        if (intent.hasExtra(extra)) retrievable = intent.getExtras().getString(extra);
        else retrievable = activity.getPreferences(Context.MODE_PRIVATE).getString(extra, null);

        if (retrievable == null) Log.wtf(TAG, "retrieveExtra("+extra+"): failed" );

        return retrievable;
    }

    /**
     * Simple method that returns "Yes" or "No"
     * @author Carlos
     * @param booleanValue A boolean true or false.
     * @return If boolean true, returns String "Yes". If boolean false, returns String "No".
     */
    public static String booleanToYesOrNo(boolean booleanValue) {
        if(booleanValue)
            return "Yes";
        else
            return "No";
    }

    /**
     * A simple method returning boolean true or false.
     * @author Carlos
     * @param answer String with "Yes" or "No"
     * @return Expected boolean value.
     */
    public static boolean yesOrNoToBoolean(String answer) {
        if (answer.equals("Yes")) return true;
        else return false;
    }
}
