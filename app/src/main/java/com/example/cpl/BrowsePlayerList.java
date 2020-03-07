package com.example.cpl;


public class BrowsePlayerList {

    int playerId;
    String playerName;
    String birthDate;
    String playerRole;
    String birthPlace;
    String url;
    int teamId;


    public BrowsePlayerList(int playerId, String playerName, String birthDate, String playerRole, String birthPlace, String url, int teamId) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.birthDate = birthDate;
        this.playerRole = playerRole;
        this.birthPlace = birthPlace;
        this.url = url;
        this.teamId = teamId;
    }

    public BrowsePlayerList(int playerId, String playerName, String birthDate, String playerRole, String birthPlace, int teamId) {

        this.playerId = playerId;
        this.playerName = playerName;
        this.birthDate = birthDate;
        this.playerRole = playerRole;
        this.birthPlace = birthPlace;
        this.teamId = teamId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(String playerRole) {
        this.playerRole = playerRole;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
