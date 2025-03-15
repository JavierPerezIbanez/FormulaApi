package com.example.formulaapi;

import com.example.formulaapi.circuitFiles.Circuit;
import com.example.formulaapi.circuitFiles.CircuitResponse;
import com.example.formulaapi.driverFiles.Driver;
import com.example.formulaapi.driverFiles.DriverResponse;

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

    @GET("circuits/{circuitId}")
    Observable<CircuitResponse<Circuit>> getCircuit(@Path("circuitId") String circuitId);

    @GET("circuits")
    Observable<CircuitResponse<Circuit>> getCircuits(@Query("offset") int offset,
                                                      @Query("limit") int limit);
}
