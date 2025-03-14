package com.example.formulaapi.circuits;

public class Circuit {
    private String circuitId;
    private String circuitName;
    private String country;
    private String city;
    private int circuitLength;
    private String lapRecord;
    private int firstParticipationYear;
    private int numberOfCorners;
    private String fastestLapDriverId;
    private String fastestLapTeamId;
    private int fastestLapYear;
    private String url;

    public String getCircuitId() {
        return circuitId;
    }

    public String getCircuitName() {
        return circuitName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public int getCircuitLength() {
        return circuitLength;
    }

    public String getLapRecord() {
        return lapRecord;
    }

    public int getFirstParticipationYear() {
        return firstParticipationYear;
    }

    public int getNumberOfCorners() {
        return numberOfCorners;
    }

    public String getFastestLapDriverId() {
        return fastestLapDriverId;
    }

    public String getFastestLapTeamId() {
        return fastestLapTeamId;
    }

    public int getFastestLapYear() {
        return fastestLapYear;
    }

    public String getUrl() {
        return url;
    }
}
