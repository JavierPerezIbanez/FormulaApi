package com.example.formulaapi.driverFiles;

import com.example.formulaapi.baseFiles.BaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverResponse<T> extends BaseResponse<T> {
    @SerializedName("drivers")
    private List<T> drivers; // Para listas de pilotos

    @SerializedName("driver")
    private List<T> driver; // Para consultas de un único piloto

    @SerializedName("limit")
    private int limit;

    @SerializedName("offset")
    private int offset;

    @SerializedName("total")
    private int total;

    public List<T> getItems() {
        return drivers != null ? drivers : driver;
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