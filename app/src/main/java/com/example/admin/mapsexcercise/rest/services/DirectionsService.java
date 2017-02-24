package com.example.admin.mapsexcercise.rest.services;


import com.example.admin.mapsexcercise.model.directions.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 2/20/2017.
 */

public interface DirectionsService {
    @GET("/maps/api/directions/json")
    Call<Result> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}
