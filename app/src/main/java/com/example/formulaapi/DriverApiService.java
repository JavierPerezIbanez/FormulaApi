package com.example.formulaapi;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DriverApiService {
    @GET("drivers")
    Observable<DriversResponse> getDriversList();

}
