package com.example.formulaapi.seasonFiles;

import com.example.formulaapi.baseFiles.BaseEntity;
import com.google.gson.annotations.SerializedName;

public class Season implements BaseEntity {
    @SerializedName("championshipId")
    private String seasonId;
    @SerializedName("championshipName")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("year")
    private String year;

    @Override
    public String getId() {
        return seasonId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getYear() {
        return year;
    }
}
