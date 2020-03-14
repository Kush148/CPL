package com.example.cpl;

public class ViewSchedule {

    int matchNo;
    String teamA,teamB,date,venue,result,resultDescription;

    public int getMatchNo() {
        return matchNo;
    }

    public void setMatchNo(int matchNo) {
        this.matchNo = matchNo;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

    public ViewSchedule(int matchNo, String teamA, String teamB, String date, String venue, String result, String resultDescription) {
        this.matchNo = matchNo;
        this.teamA = teamA;
        this.teamB = teamB;
        this.date = date;
        this.venue = venue;
        this.result = result;
        this.resultDescription = resultDescription;


    }
}
