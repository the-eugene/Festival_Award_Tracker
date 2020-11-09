package com.example.festivalawardtracker;

import java.time.LocalDate;
import java.util.ArrayList;

public class SchoolYear extends DatabaseAware{
    String name;
    LocalDate start;
    LocalDate end;
    ArrayList<String> eventIDs;
    boolean contains(LocalDate d){return d.compareTo(start)*d.compareTo(end)<=0;}
}
