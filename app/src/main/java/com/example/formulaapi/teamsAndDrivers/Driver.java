package com.example.formulaapi.teamsAndDrivers;

public class Driver {
    private String driverId;
    private String name;
    private String surname;
    private String nationality;
    private String birthday;
    private int number;
    private String shortName;
    private String url;
    private String teamId;
    private String fullName;

    public String getDriverId() {
        return driverId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBirthday() {
        return birthday;
    }

    public int getNumber() {
        return number;
    }

    public String getShortName() {
        return shortName;
    }

    public String getUrl() {
        return url;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getFullName() {
        return name+" "+surname;
    }
}
