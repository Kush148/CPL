package com.example.cpl;

public class TeamManager {

    String UserName;
    int teamManagerId;
    public TeamManager(String userName, int teamManagerId) {
        UserName = userName;
        this.teamManagerId = teamManagerId;
    }

    public TeamManager(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getTeamManagerId() {
        return teamManagerId;
    }

    public void setTeamManagerId(int teamManagerId) {
        this.teamManagerId = teamManagerId;
    }

    public String toString(String userName){
        return ""+userName+"";
    }


}
