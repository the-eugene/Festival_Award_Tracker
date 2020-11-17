package com.example.festivalawardtracker;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 * @author Android SDK
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class _ExampleUnitTest {
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