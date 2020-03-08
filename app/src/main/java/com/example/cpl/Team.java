package com.example.cpl;

public class Team {

    int teamId;
    String teamName;
    String color;

    public Team(int teamId, String teamName, String color) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.color = color;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
