package com.example.festivalawardtracker;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.Period;

public class Person extends DBAware implements Comparable<Person>{
    public String firstName;
    public String middleName;
    public String lastName;
    public Gender gender;
    public LocalDate birthday;
    public Contact contact;

    public Person(String firstName, String middleName) {
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public Person() {

    }

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

    public Integer getAge(){return getAge(LocalDate.now());}
    public Integer getAge(LocalDate onDate){
        return (Integer)Period.between(birthday, onDate).getYears();
    }

    public String shortLocation() {
        return String.format("%s, %s", getContact().city, getContact().stateCode());
    }
    public String getEmail(){return contact.email;}
    public static Class<Person> ofType(){return Person.class;}

    @Override
    public int compareTo(Person o) {
        return lastName.compareTo(o.lastName);
    }

    public enum Gender {
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
