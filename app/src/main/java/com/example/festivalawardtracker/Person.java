package com.example.festivalawardtracker;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

public class Person extends DatabaseAware{
    String firstName;
    String middleName;
    String lastName;
    Gender gender;
    LocalDate birthday;
    Contact contact;

    public void setContact(String business, String phone, String email, String street, String city, String state, String zip) {
        setContact(new Contact(business, phone, email, street, city, state, zip));
        //TODO: V2, search db for identical contact (Parents, repeat Event Locations)
        // What's V2? Carlos
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getBirthday() {
        return birthday.toString();
    }
    public void setBirthday(String birthday) { this.birthday = LocalDate.parse(birthday);
    }
    public Contact getContact() { return contact; }
    public void setContact(Contact contact) { this.contact = contact; }

    public void setName(String first, String middle, String last) {
        setFirstName(first);
        setMiddleName(middle);
        setLastName(last);
    }
    public String getFullName(){
        return String.format("%s %s. %s", getFirstName(), getMiddleName().charAt(0), getLastName());
    }

    public String getGenderString(){return getGender().toString();}

    public int getAge(){return getAge(LocalDate.now());}
    public int getAge(LocalDate onDate){
        return Period.between(birthday, onDate).getYears();
    }

    public String shortLocation() {
        return String.format("%s, %s", getContact().city, getContact().stateCode());
    }
    public String getEmail(){return contact.email;}
    public static Class<Person> ofType(){return Person.class;}

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
}
