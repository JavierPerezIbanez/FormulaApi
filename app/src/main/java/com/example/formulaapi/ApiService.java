package com.example.formulaapi;

import com.example.formulaapi.teamsAndDrivers.TeamsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("2025/teams")
    Observable<TeamsResponse> getTeamsList();
}
