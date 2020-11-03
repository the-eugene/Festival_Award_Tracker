package com.example.festivalawardtracker;

import java.time.LocalDate;

public class Award {
    LocalDate dateReceived;
    int eventID;
    int performanceID;
    AwardType type;
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
