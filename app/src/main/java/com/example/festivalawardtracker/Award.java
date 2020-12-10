package com.example.festivalawardtracker;

import androidx.annotation.NonNull;

import java.time.LocalDate;

/**
 * Model Class for awards that were received by the student based on the history of their performances in various events
 *
 * @author Eugene
 */
public class Award {
    String studentID;
    LocalDate dateReceived;
    String eventID;
    int performanceID;
    AwardType type;

    /**
     * no argument Award constructor (used for firebase)
     */
    public Award() {
    }

    /**
     * All in one award constructor
     *
     * @param type          the type of award this is.
     * @param studentID     the ID of the student who received the award
     * @param date          date when award was received
     * @param eventID       ID of the event for which they received this award
     * @param performanceID the index of the performance in the student's history
     */
    public Award(AwardType type, String studentID, LocalDate date, String eventID, int performanceID) {
        this.type = type;
        this.studentID = studentID;
        this.dateReceived = date;
        this.eventID = eventID;
        this.performanceID = performanceID;
    }

    /**
     * Look up the appropriate cup enum
     * @param cuplevel integer for the cup level they are getting
     * @return the appropriate AwardType for that cup level
     */
    public static AwardType lookUpCupAwardType(int cuplevel) {
        switch (cuplevel) {
            case 1:
                return AwardType.CUP;
            case 2:
                return AwardType.CUP_2ND;
            case 3:
                return AwardType.CUP_3RD;
            case 4:
                return AwardType.CUP_4TH;
            case 5:
                return AwardType.GRAND_CUP;
            case 6:
                return AwardType.PRESIDENTS_CUP;
        }
        return null;
    }

    /**
     * @return Performance object for which the award was given
     */
    public Performance retrievePerformance() {
        return DBManager.Students.get(studentID).performances.get(performanceID);
    }

    /**
     * Checks if award belongs to a particular school year
     *
     * @param year The School Year object to be checked
     * @return true if the award was awarded for this year
     */
    public boolean isInYear(SchoolYear year) {
        return retrievePerformance().isInYear(year);
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    /**
     * Getter for dateReceived, converts the date to a string for use in firebase
     * @return String representation of the date
     */
    public String getDateReceived() {
        return dateReceived.toString();
    }

    /**
     * setter for dateReceived, uses a string to make it compatible with firebase
     * @param dateReceived String representation of the date
     */
    public void setDateReceived(String dateReceived) {
        this.dateReceived = LocalDate.parse(dateReceived);
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public int getPerformanceID() {
        return performanceID;
    }

    public void setPerformanceID(int performanceID) {
        this.performanceID = performanceID;
    }

    public AwardType getType() {
        return type;
    }

    public void setType(AwardType type) {
        this.type = type;
    }

    /**
     * Defines possible awards
     */
    enum AwardType {
        NFMC_CERT,
        SUPERIOR_CERT,
        CONSECUTIVE_SUPERIOR_CERT,
        OTHER_PARTICIPATION,
        MEDAL,
        CUP,
        CUP_2ND,
        CUP_3RD,
        CUP_4TH,
        GRAND_CUP,
        PRESIDENTS_CUP;

        @NonNull
        @Override
        public String toString() {
            switch (this) {
                case NFMC_CERT:
                    return "CERT";
                case SUPERIOR_CERT:
                    return "SUP";
                case CONSECUTIVE_SUPERIOR_CERT:
                    return "CS";
                case MEDAL:
                    return "Medal";
                case CUP:
                    return "Cup";
                case CUP_2ND:
                    return "2nd Cup";
                case CUP_3RD:
                    return "3rd Cup";
                case CUP_4TH:
                    return "4th Cup";
                case GRAND_CUP:
                    return "Grand Cup";
                case PRESIDENTS_CUP:
                    return "Pres. Cup";
            }
            return "Other";
        }
    }
}
