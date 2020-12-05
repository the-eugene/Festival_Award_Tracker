package com.example.festivalawardtracker;

import android.util.Log;
import static org.junit.Assert.*;

import org.junit.Test;

public class GetByEmailInstrumentedTest {
    @Test
    public void checkEmail(){
        //Student student=new Student();

        Student s = DBManager.getStudentByEmail("none");
        assertNull(s);
        s=DBManager.getStudentByEmail("info@spacetime.com");
        assertNotNull(s);
        assertEquals("Alice B. Callaway", s.getFullName());

        s=DBManager.getStudentByEmail("batmail@cave.org");
        assertEquals("Bruce M. Wane", s.getFullName());
        Log.d("TEST: batmail@cave.org", s.getFullName());

    }
}
