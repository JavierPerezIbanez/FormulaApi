package com.example.formulaapi.seasonFiles;

import com.example.formulaapi.baseFiles.BaseEntity;
import com.google.gson.annotations.SerializedName;

public class Season implements BaseEntity {
    @SerializedName("seasonId")
    private String seasonId;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("year")
    private int year;

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

    public int getYear() {
        return year;
    }
}
