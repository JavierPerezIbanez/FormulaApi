package com.example.formulaapi;

import com.example.formulaapi.circuitFiles.Circuit;
import com.example.formulaapi.circuitFiles.CircuitResponse;
import com.example.formulaapi.driverFiles.Driver;
import com.example.formulaapi.driverFiles.DriverResponse;
import com.example.formulaapi.driverFiles.TeamDriversResponse;
import com.example.formulaapi.seasonFiles.Season;
import com.example.formulaapi.seasonFiles.SeasonResponse;
import com.example.formulaapi.teamFiles.Team;
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

    @GET("circuits/{circuitId}")
    Observable<CircuitResponse<Circuit>> getCircuit(@Path("circuitId") String circuitId);

    @GET("circuits")
    Observable<CircuitResponse<Circuit>> getCircuits(@Query("offset") int offset,
                                                      @Query("limit") int limit);

    @GET("teams/{teamId}")
    Observable<TeamResponse<Team>> getTeam(@Path("teamId") String teamId);

    @GET("teams")
    Observable<TeamResponse<Team>> getTeams(@Query("offset") int offset,
                                            @Query("limit") int limit);

    @GET("seasons/{seasonId}")
    Observable<SeasonResponse<Season>> getSeason(@Path("seasonId") String seasonId);

    @GET("seasons")
    Observable<SeasonResponse<Season>> getSeasons(@Query("offset") int offset,
                                            @Query("limit") int limit);

    @GET("2025/teams")
    Observable<TeamResponse<Team>> getTeams2025();

    @GET("2025/teams/{teamId}/drivers")
    Observable<TeamDriversResponse> getTeamDrivers2025(@Path("teamId") String teamId);
}
