package com.example.formulaapi;

import com.example.formulaapi.circuits.CircuitsResponse;
import com.example.formulaapi.seasons.SeasonResponse;
import com.example.formulaapi.teamsAndDrivers.DriversResponse;
import com.example.formulaapi.teamsAndDrivers.TeamDriversResponse;
import com.example.formulaapi.teamsAndDrivers.TeamsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * Get the list of teams in a certain year
     * @param year is the year selected
     */
    @GET("{year}/teams")
    Observable<TeamsResponse> getTeamsList(@Path ("year") String year);

    /**
     * Get the list of drivers od a certain team in a certain year
     * @param teamId is the team selected
     * @param year is the team selected
     */
    @GET("{year}/teams/{teamId}/drivers")
    Observable<TeamDriversResponse> getDriversList(@Path("teamId") String teamId,
                                                   @Path("year") String year);

    /**
     * get the information of the driver with the driverId given
     * @param driverId whose info is wanted
     */
    @GET("drivers/{driverId}")
    Observable<DriversResponse> getDriver(@Path("driverId") String driverId);

    /**
     * Get the list of circuits
     */
    @GET("circuits")
    Observable<CircuitsResponse> getCircuitsList(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    /**
     * Get the list of seasons
     */
    @GET("seasons")
    Observable<SeasonResponse> getSeasonsList(
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
