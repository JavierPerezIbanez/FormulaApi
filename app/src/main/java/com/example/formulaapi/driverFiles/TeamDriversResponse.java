package com.example.formulaapi.driverFiles;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TeamDriversResponse {

    @SerializedName("drivers")
    private List<DriverWrapper> drivers;

    public List<DriverWrapper> getDrivers() {
        return drivers;
    }

    public static class DriverWrapper {
        @SerializedName("driver")
        private Driver driver;

        public Driver getDriver() {
            return driver;
        }
    }
}
