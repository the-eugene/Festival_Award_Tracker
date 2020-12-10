package com.example.festivalawardtracker;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.time.Period;

/**
 * Model Class representing a person, also serves as a parent class to Student and Teacher
 * in the future this class can be used for Parents
 * NFMC needs the full name of a person including the middle name, as well as their birthday to track individuals
 */
public class Person extends DBAware implements Comparable<Person> {
    public String firstName;
    public String middleName;
    public String lastName;
    public Gender gender;
    public LocalDate birthday;
    public Contact contact;

    /**
     * No argument constructor for firebase
     */
    public Person() {}

    /**
     * Adds contact information
     * @see Contact
     * @param business address line - business name
     * @param phone phone number
     * @param email email address
     * @param street address line - street address
     * @param city address - city
     * @param state address - state, can be a full name of the state or the two digit code
     * @param zip address - zip code
     */
    public void setContact(String business, String phone, String email, String street, String city, String state, String zip) {
        setContact(new Contact(business, phone, email, street, city, state, zip));
        //TODO: V2, search db for identical contact (Parents, repeat Event Locations)
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday.toString();
    }

    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday);
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * Sets the full name of a person all at once
     * @param first First Name
     * @param middle Middle Name
     * @param last Last Name
     */
    public void setName(String first, String middle, String last) {
        setFirstName(first);
        setMiddleName(middle);
        setLastName(last);
    }

    /**
     * @return String containing the person's name as First M. Last
     */
    public String getFullName() {
        return String.format("%s %s. %s", getFirstName(), getMiddleName().charAt(0), getLastName());
    }

    /**
     * @return a String with person's Gender
     */
    public String getGenderString() {
        return getGender().toString();
    }

    /**
     * @return int of the person's age right now
     */
    public Integer getAge() {
        return getAge(LocalDate.now());
    }

    /**
     * Calculate age at a particular date - important for NFMC
     * @param onDate The date at which the age will be calculated
     * @return int representing the person's age on that date
     */
    public Integer getAge(LocalDate onDate) {
        return (Integer) Period.between(birthday, onDate).getYears();
    }

    /**
     * Get a short location string
     * @return formatted string containing city and state code
     */
    public String shortLocation() {
        return String.format("%s, %s", getContact().city, getContact().stateCode());
    }

    public String getEmail() {
        return contact.email;
    }

    /**
     * Allows for sorting of people by their name
     * @param o Person being compared to
     * @return comparison between their names: last and first.
     *
     */
    @Override
    public int compareTo(Person o) {
        int last = lastName.compareTo(o.lastName);
        return last!=0?last:firstName.compareTo(o.firstName);
    }

    /**
     * Represents a gender
     */
    public enum Gender {
        MALE,
        FEMALE;

        @NonNull
        @Override
        public String toString() {
            if (this == MALE) {
                return "Male";
            } else {
                return "Female";
            }
        }
    }
}
