package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.time.Period;

public class Person implements DatabaseAware{
    String firstName;
    String middleName;
    String lastName;
    Gender gender;
    LocalDate birthday;
    Contact contact;

    public int getAge(){return getAge(LocalDate.now());}
    public int getAge(LocalDate onDate){
        return Period.between(birthday,onDate).getYears();
    }

    @Override
    public boolean Save() {
        return false;
    }

    @Override
    public boolean Load() {
        return false;
    }

    public void setName(String first, String middle, String last) {
    }
}
