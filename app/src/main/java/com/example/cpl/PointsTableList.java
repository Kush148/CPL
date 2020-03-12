package com.example.cpl;


public class PointsTableList {

    String TeamName;
    int play;
    int Win;
    int Lose;
    int Points;

    public PointsTableList(String teamName, int play, int win, int lose, int points) {
        TeamName = teamName;
        this.play = play;
        Win = win;
        Lose = lose;
        Points = points;}

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public int getPlay() {
        return play;
    }

    public void setPlay(int play) {
        this.play = play;
    }

    public int getWin() {
        return Win;
    }

    public void setWin(int win) {
        Win = win;
    }

    public int getLose() {
        return Lose;
    }

    public void setLose(int lose) {
        Lose = lose;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }


    }




