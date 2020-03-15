package com.example.cpl;

public class Season {

    int seasonId;
    String seasonName;
    String StartDate;
    String EndDate;

    public Season(int seasonId, String seasonName, String startDate, String endDate) {
        this.seasonId = seasonId;
        this.seasonName = seasonName;
        StartDate = startDate;
        EndDate = endDate;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
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

