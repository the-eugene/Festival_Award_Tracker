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

    public Performance getPerformance(){
        return DBManager.Students.get(studentID).performances.get(performanceID);
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
