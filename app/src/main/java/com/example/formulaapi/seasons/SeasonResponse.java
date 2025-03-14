package com.example.formulaapi.seasons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonResponse {
    @SerializedName("championships")
    private List<Season> seasons;

    public List<Season> getSeasons(){
        return seasons;
    }

}
