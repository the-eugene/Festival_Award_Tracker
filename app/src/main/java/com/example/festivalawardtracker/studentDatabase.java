package com.example.festivalawardtracker;

public class studentDatabase {
    String fname;
    String lname;
    String bdate;
    String gender;
    String number;
    String instrument;
    String teacher;


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public studentDatabase(String firstName, String lastName, String birthDate, String Gender
          , String phoneNumber, String Instrument, String Teacher) {
        fname = firstName;
        lname = lastName;
        bdate = birthDate;
        gender = Gender;
        number = phoneNumber;
        instrument = Instrument;
        teacher = Teacher;
    }



}
