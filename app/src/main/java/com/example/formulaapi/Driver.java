package com.example.formulaapi;

public class Driver {
    private String broadcastName;
    private String country;
    private int driverNumber;
    private String firstName;
    private String fullName;
    private String url;
    private int meetingKey;
    private String acronym;
    private int sessionKey;
    private String teamColor;
    private String teamName;

    public Driver(String broadcastName, String teamName, String teamColor, int sessionKey, String acronym,
                  int meetingKey, String url, String fullName, String firstName, String country, int driverNumber) {
        this.broadcastName = broadcastName;
        this.teamName = teamName;
        this.teamColor = teamColor;
        this.sessionKey = sessionKey;
        this.acronym = acronym;
        this.meetingKey = meetingKey;
        this.url = url;
        this.fullName = fullName;
        this.firstName = firstName;
        this.country = country;
        this.driverNumber = driverNumber;
    }

    public String getBroadcastName() {
        return broadcastName;
    }

    public void setBroadcastName(String broadcastName) {
        this.broadcastName = broadcastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(int driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMeetingKey() {
        return meetingKey;
    }

    public void setMeetingKey(int meetingKey) {
        this.meetingKey = meetingKey;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public int getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(int sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
