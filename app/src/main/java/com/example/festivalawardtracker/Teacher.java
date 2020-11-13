package com.example.festivalawardtracker;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    List<String> studentIDs=new ArrayList<>();

    public List<String> getStudentIDs() {
        return studentIDs;
    }

    public void setStudentIDs(List<String> studentIDs) {
        this.studentIDs = studentIDs;
    }
}
