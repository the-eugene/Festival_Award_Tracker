package com.example.festivalawardtracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class describing Teachers
 * @author Eugene
 * @see DBAware
 * @see Person
 */
public class Teacher extends Person {
    List<String> studentIDs=new ArrayList<>();

    public List<String> getStudentIDs() {
        return studentIDs;
    }

    public void setStudentIDs(List<String> studentIDs) {
        this.studentIDs = studentIDs;
    }

    /**
     * adds a student to the list of a teacher's student
     * Generates IDs as necessary
     * @param student the student to be added
     */
    public void addStudent(Student student) {
        if (student.ID==null){
            DBManager.Students.put(null,student);
        }
        studentIDs.add(student.ID);
        DBManager.Teachers.put(ID,this); //not save
    }

    public void loadStudents(){
        //TODO V2, load students by teacher
    }

}
