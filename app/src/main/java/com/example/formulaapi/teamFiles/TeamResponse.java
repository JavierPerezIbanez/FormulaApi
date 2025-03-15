package com.example.formulaapi.teamFiles;

import com.example.formulaapi.baseFiles.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamResponse extends BaseResponse<Team> {
    @SerializedName("teams")
    private List<Team> teams;

    @Override
    public List<Team> getItems() {
        return teams;
    }
}
