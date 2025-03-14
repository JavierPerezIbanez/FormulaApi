package com.example.formulaapi;

import com.example.formulaapi.teamsAndDrivers.DriversResponse;
import com.example.formulaapi.teamsAndDrivers.TeamsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("2025/teams")
    Observable<TeamsResponse> getTeamsList();

    @GET("2025/teams/{teamId}/drivers")
    Observable<DriversResponse> getDriversList(@Path("teamId") String teamId);
}
