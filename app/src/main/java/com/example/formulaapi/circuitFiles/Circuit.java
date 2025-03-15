package com.example.formulaapi.circuitFiles;

import com.example.formulaapi.baseFiles.BaseEntity;
import com.google.gson.annotations.SerializedName;

/**
 * Objeto Circuito con los todos los campos que devuelve la API
 */
public class Circuit implements BaseEntity {
    @SerializedName("circuitId")
    private String circuitId;
    @SerializedName("circuitName")
    private String circuitName;
    @SerializedName("country")
    private String country;
    @SerializedName("city")
    private String city;
    @SerializedName("circuitLength")
    private int circuitLength;
    @SerializedName("lapRecord")
    private String lapRecord;
    @SerializedName("firstParticipationYear")
    private int firstParticipationYear;
    @SerializedName("numberOfCorners")
    private int numberOfCorners;
    @SerializedName("fastestLapDriverId")
    private String fastestLapDriverId;
    @SerializedName("fastestLapTeamId")
    private String fastestLapTeamId;
    @SerializedName("fastestLapYear")
    private int fastestLapYear;
    @SerializedName("url")
    private String url;

    @Override
    public String getId() {
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
