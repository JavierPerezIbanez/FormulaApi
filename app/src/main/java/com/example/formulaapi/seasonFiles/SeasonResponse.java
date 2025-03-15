package com.example.formulaapi.seasonFiles;

import com.example.formulaapi.baseFiles.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonResponse extends BaseResponse<Season> {
    @SerializedName("championships")
    private List<Season> seasons;

    @Override
    public List<Season> getItems(){
        return seasons;
    }

}
