package com.example.formulaapi.teamFiles.teamDrivers;

import com.example.formulaapi.baseFiles.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamDriverResponse extends BaseResponse<TeamDriver> {
    @SerializedName("drivers")
    private List<TeamDriver> drivers;

    @Override
    public List<TeamDriver> getItems() {
        return drivers;
    }
}
