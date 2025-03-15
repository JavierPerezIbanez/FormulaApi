package com.example.formulaapi.teamsAndDrivers;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class TeamDriversResponse {
    @SerializedName("drivers")
    private List<DriverWrapper> driverWrappers;

    public List<Driver> getDrivers() {
        List<Driver> drivers = new ArrayList<>();
        if (driverWrappers != null) {
            for (DriverWrapper wrapper : driverWrappers) {
                drivers.add(wrapper.getDriver());
            }
        }
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
