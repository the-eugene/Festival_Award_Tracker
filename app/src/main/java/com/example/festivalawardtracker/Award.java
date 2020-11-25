package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Award {
    String studentID;
    LocalDate dateReceived;
    String eventID;
    int performanceID;
    AwardType type;
    public Award(){}
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

    public String getDateReceived() {
        return dateReceived.toString();
    }

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

   public static AwardType lookUpCupAwardType(int cuplevel){
        switch (cuplevel) {
            case 1: return AwardType.CUP;
            case 2: return AwardType.CUP_2ND;
            case 3: return AwardType.CUP_3RD;
            case 4: return AwardType.CUP_4TH;
            case 5: return AwardType.GRAND_CUP;
            case 6: return AwardType.PRESIDENTS_CUP;
        }
        return null;
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
