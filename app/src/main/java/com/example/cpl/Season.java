package com.example.cpl;

public class Season {

    String seasonName;
    String StartDate;
    String EndDate;

    public Season(String seasonName, String startDate, String endDate) {
        this.seasonName = seasonName;
        StartDate = startDate;
        EndDate = endDate;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }




}

