package com.example.formulaapi;

import com.example.formulaapi.circuitFiles.CircuitResponse;
import com.example.formulaapi.driverFiles.Driver;
import com.example.formulaapi.seasonFiles.SeasonResponse;
import com.example.formulaapi.driverFiles.DriverResponse;
import com.example.formulaapi.antiguos.teamsAndDrivers.TeamDriversResponse;
import com.example.formulaapi.teamFiles.TeamResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("drivers/{driverId}")
    Observable<DriverResponse<Driver>> getDriver(@Path("driverId") String driverId);

    @GET("drivers")
    Observable<DriverResponse<Driver>> getDrivers(@Query("offset") int offset,
                                                  @Query("limit") int limit);

}
