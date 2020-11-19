package com.example.festivalawardtracker;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 * @author Android SDK
 */
public class ExampleTest {
    /**
     * Useful reading on Unit Testing:
     * http://d.android.com/tools/testing
     * https://junit.org/junit4/cookbook.html Really quick reference.
     * https://junit.org/junit4/faq.html#atests_16 Here you'll also find good examples.
     */

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void instrument_Options() {
        assertEquals("Piano", Instrument.Options()[0]);
    }

    @Test
    public void States_List() {
        assertEquals("AB", Contact.OptionsStates()[0]);
    }

}