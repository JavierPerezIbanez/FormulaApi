package com.example.formulaapi.teamFiles;

import com.example.formulaapi.baseFiles.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamResponse<T> extends BaseResponse<T> {
    @SerializedName("teams")
    private List<T> teams; // Para listas de equipos

    @SerializedName("team")
    private List<T> team; // Para consultas de un único equipo

    @SerializedName("limit")
    private int limit;

    @SerializedName("offset")
    private int offset;

    @SerializedName("total")
    private int total;

    public List<T> getItems() {
        return teams != null ? teams : team;
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
