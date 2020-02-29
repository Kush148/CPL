package com.example.cpl;

public class Team {

    String teamName;
    String color;


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



    public Team(String teamName, String color) {
        this.teamName = teamName;
        this.color = color;
    }
}
