package com.example.festivalawardtracker;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.Period;

public class Person extends DatabaseAware{
    String firstName;
    String middleName;
    String lastName;
    Gender gender;
    LocalDate birthday;
    Contact contact;

    public void setContact(String business, String phone, String email, String street, String city, String state, String zip) {
        contact=new Contact(business, phone, email, street, city, state, zip);
        //TODO: V2, search db for identical contact (Parents, repeat Event Locations)
    }

    enum Gender {
        MALE,
        FEMALE
        ;

        @NonNull
        @Override
        public String toString() {
            if (this==MALE) {return "Male";}
            else{return "Female";}
        }
    }

    public void setName(String first, String middle, String last) {
        firstName=first;
        middleName=middle;
        lastName=last;
        //TODO save();
    }
    public String getFullName(){
        return String.format("%s %s. %s",firstName,middleName.charAt(0),lastName);
    }
    public String getGenderString(){return gender.toString();}
    public int getAge(){return getAge(LocalDate.now());}
    public int getAge(LocalDate onDate){
        return Period.between(birthday,onDate).getYears();
    }
    public String getShortLocation() {
        return String.format("%s, %s", contact.city,contact.getStateCode());
    }
}
