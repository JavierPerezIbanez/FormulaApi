package com.example.formulaapi;

import com.example.formulaapi.circuits.CircuitsResponse;
import com.example.formulaapi.seasons.SeasonResponse;
import com.example.formulaapi.teamsAndDrivers.DriversResponse;
import com.example.formulaapi.teamsAndDrivers.TeamsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("2025/teams")
    Observable<TeamsResponse> getTeamsList();

    @GET("2025/teams/{teamId}/drivers")
    Observable<DriversResponse> getDriversList(@Path("teamId") String teamId);

    @GET("circuits")
    Observable<CircuitsResponse> getCircuitsList(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
    @GET("seasons")
    Observable<SeasonResponse> getSeasonsList(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
