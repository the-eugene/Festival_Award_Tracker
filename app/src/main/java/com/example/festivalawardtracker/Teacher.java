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

    public void addStudent(Student student) {
        if (student.ID==null){
            DBManager.Students.put(null,student);
        }
        studentIDs.add(student.ID);
        DBManager.Teachers.put(ID,this); //not save
    }

    public void loadStudents(){
        //TODO load students by teacher
    }

}
