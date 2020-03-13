package com.example.cpl;

public class ViewSingleTeamInfo {

    int playerId;
    String teamName,teamColor,userName,contactNumber,playerName,playerRole,url;

    public ViewSingleTeamInfo(int playerId, String playerName, String playerRole, String url) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerRole = playerRole;
        this.url = url;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public ViewSingleTeamInfo(String teamName, String teamColor, String userName, String contactNumber) {
        this.teamName = teamName;
        this.teamColor = teamColor;
        this.userName = userName;
        this.contactNumber = contactNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(String playerRole) {
        this.playerRole = playerRole;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
