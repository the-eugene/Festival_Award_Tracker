package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Award {
    String studentID;
    LocalDate dateReceived;
    String eventID;
    int performanceID;
    AwardType type;

    public Award(AwardType type, String studentID, LocalDate date, String eventID, int performanceID) {
        this.type=type;
        this.studentID=studentID;
        this.dateReceived=date;
        this.eventID=eventID;
        this.performanceID=performanceID;
    }

    public Performance retrievePerformance(){
        return DBManager.Students.get(studentID).performances.get(performanceID);
    }

    public boolean isInYear(SchoolYear year) {
        return retrievePerformance().isInYear(year);
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
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

    enum AwardType{
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
        PRESIDENTS_CUP
    }
}
