package com.example.formulaapi.teamFiles;

import com.example.formulaapi.baseFiles.BaseEntity;
import com.google.gson.annotations.SerializedName;

public class Team implements BaseEntity {
    @SerializedName("teamId")
    private String teamId;
    @SerializedName("teamName")
    private String teamName;
    @SerializedName("teamNationality")
    private String teamNationality;
    @SerializedName("firstAppeareance")
    private int firstAppeareance;
    @SerializedName("constructorsChampionships")
    private int constructorsChampionships;
    @SerializedName("driversChampionships")
    private int driversChampionships;
    @SerializedName("url")
    private String url;

    @Override
    public String getId() {
        return teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamNationality() {
        return teamNationality;
    }

    public int getFirstAppeareance() {
        return firstAppeareance;
    }

    public int getConstructorsChampionships() {
        return constructorsChampionships;
    }

    public int getDriversChampionships() {
        return driversChampionships;
    }

    public String getUrl() {
        return url;
    }
}
