package com.example.formulaapi.teamsAndDrivers;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class DriversResponse {
    @SerializedName("driver")
    private List<Driver> drivers;

    public List<Driver> getDrivers() {
        return drivers;
    }
}
