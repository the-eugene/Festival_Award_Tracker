package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.time.Period;

public class Person{
    String firstName;
    String middleName;
    String lastName;
    Gender gender;
    LocalDate birthday;
    Contact contact;
    enum Gender {
        MALE,
        FEMALE
    }

    public int getAge(){return getAge(LocalDate.now());}
    public int getAge(LocalDate onDate){
        return Period.between(birthday,onDate).getYears();
    }
    public void setName(String first, String middle, String last) {
        firstName=first;
        middleName=middle;
        lastName=last;
    }
}
