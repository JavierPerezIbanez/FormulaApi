package com.example.formulaapi.seasonFiles;

import com.example.formulaapi.baseFiles.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SeasonResponse<T> extends BaseResponse<T> {
    @SerializedName("championships")
    private List<T> seasons; // Para listas de temporadas

    @SerializedName("limit")
    private int limit;

    @SerializedName("offset")
    private int offset;

    @SerializedName("total")
    private int total;

    public List<T> getItems() {
        return seasons;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return total;
    }
}