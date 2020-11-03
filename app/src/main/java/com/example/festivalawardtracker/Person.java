package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

public class Person {
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
}
