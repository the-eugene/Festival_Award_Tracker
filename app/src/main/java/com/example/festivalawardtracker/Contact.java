package com.example.festivalawardtracker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Model class holding contact information such as address, phone number, and email
 * @author Eugene
 * @see Event
 * @see Person
 */
public class Contact {
    public static final Map<String, String> STATE_MAP;

    static {
        STATE_MAP = new HashMap<>();
        STATE_MAP.put("AL", "Alabama");
        STATE_MAP.put("AK", "Alaska");
        STATE_MAP.put("AB", "Alberta");
        STATE_MAP.put("AZ", "Arizona");
        STATE_MAP.put("AR", "Arkansas");
        STATE_MAP.put("BC", "British Columbia");
        STATE_MAP.put("CA", "California");
        STATE_MAP.put("CO", "Colorado");
        STATE_MAP.put("CT", "Connecticut");
        STATE_MAP.put("DE", "Delaware");
        STATE_MAP.put("DC", "District Of Columbia");
        STATE_MAP.put("FL", "Florida");
        STATE_MAP.put("GA", "Georgia");
        STATE_MAP.put("GU", "Guam");
        STATE_MAP.put("HI", "Hawaii");
        STATE_MAP.put("ID", "Idaho");
        STATE_MAP.put("IL", "Illinois");
        STATE_MAP.put("IN", "Indiana");
        STATE_MAP.put("IA", "Iowa");
        STATE_MAP.put("KS", "Kansas");
        STATE_MAP.put("KY", "Kentucky");
        STATE_MAP.put("LA", "Louisiana");
        STATE_MAP.put("ME", "Maine");
        STATE_MAP.put("MB", "Manitoba");
        STATE_MAP.put("MD", "Maryland");
        STATE_MAP.put("MA", "Massachusetts");
        STATE_MAP.put("MI", "Michigan");
        STATE_MAP.put("MN", "Minnesota");
        STATE_MAP.put("MS", "Mississippi");
        STATE_MAP.put("MO", "Missouri");
        STATE_MAP.put("MT", "Montana");
        STATE_MAP.put("NE", "Nebraska");
        STATE_MAP.put("NV", "Nevada");
        STATE_MAP.put("NB", "New Brunswick");
        STATE_MAP.put("NH", "New Hampshire");
        STATE_MAP.put("NJ", "New Jersey");
        STATE_MAP.put("NM", "New Mexico");
        STATE_MAP.put("NY", "New York");
        STATE_MAP.put("NF", "Newfoundland");
        STATE_MAP.put("NC", "North Carolina");
        STATE_MAP.put("ND", "North Dakota");
        STATE_MAP.put("NT", "Northwest Territories");
        STATE_MAP.put("NS", "Nova Scotia");
        STATE_MAP.put("NU", "Nunavut");
        STATE_MAP.put("OH", "Ohio");
        STATE_MAP.put("OK", "Oklahoma");
        STATE_MAP.put("ON", "Ontario");
        STATE_MAP.put("OR", "Oregon");
        STATE_MAP.put("PA", "Pennsylvania");
        STATE_MAP.put("PE", "Prince Edward Island");
        STATE_MAP.put("PR", "Puerto Rico");
        STATE_MAP.put("QC", "Quebec");
        STATE_MAP.put("RI", "Rhode Island");
        STATE_MAP.put("SK", "Saskatchewan");
        STATE_MAP.put("SC", "South Carolina");
        STATE_MAP.put("SD", "South Dakota");
        STATE_MAP.put("TN", "Tennessee");
        STATE_MAP.put("TX", "Texas");
        STATE_MAP.put("UT", "Utah");
        STATE_MAP.put("VT", "Vermont");
        STATE_MAP.put("VI", "Virgin Islands");
        STATE_MAP.put("VA", "Virginia");
        STATE_MAP.put("WA", "Washington");
        STATE_MAP.put("WV", "West Virginia");
        STATE_MAP.put("WI", "Wisconsin");
        STATE_MAP.put("WY", "Wyoming");
        STATE_MAP.put("YT", "Yukon Territory");
    }

    public String business;
    public String email;
    public String phone;
    public String street;
    public String city;
    public String state;
    public String zip;

    /**
     * no argument constructor for firebase
     */
    public Contact() {
    }

    /**
     * All in one constructor
     * @param business address line - business name
     * @param phone phone number
     * @param email email address
     * @param street address line - street address
     * @param city address - city
     * @param state address - state, can be a full name of the state or the two digit code
     * @param zip address - zip code
     */
    Contact(String business, String phone, String email, String street, String city, String state, String zip) {
        this.business = business;
        this.phone = phone;
        this.email = email;
        this.street = street;
        this.city = city;
        this.zip = zip;
        if (state.length() == 2) {
            this.state = state;
        } else if (STATE_MAP.containsValue(state)) {
            for (Map.Entry<String, String> line : STATE_MAP.entrySet()) {
                if (line.getValue().equals(state)) {
                    this.state = line.getKey();
                    break;
                }
            }
        }
    }

    /**
     * Generates possible state options in an array - use in a drop down for example
     * @return String array of two letter state codes
     */
    public static String[] OptionsStates() {
        String[] options = STATE_MAP.keySet().toArray(new String[0]);
        Arrays.sort(options);
        return options;
    }

    /**
     * returns the state code for the contact
     * @return two letter state code
     */
    public String stateCode() {
        return state;
    }

    /**
     * returns the full state name for the contact
     * @return String with the full name of the state
     */
    public String stateName() {
        return STATE_MAP.get(state);
    }

    /**
     * gets the address for the contact - does not include the name of the business
     * @return multi-line string address including the street, city, state, and zip (not business)
     */
    public String fullAddress() {
        return String.format("%s\n%s, %s  %s", street, city, state, zip);
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}

