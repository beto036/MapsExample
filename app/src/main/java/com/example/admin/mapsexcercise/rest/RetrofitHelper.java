package com.example.admin.mapsexcercise.rest;


import com.example.admin.mapsexcercise.model.directions.Result;
import com.example.admin.mapsexcercise.rest.services.DirectionsService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2/20/2017.
 */

public class RetrofitHelper {

    public static final String BASE_URL = "https://maps.googleapis.com";

    public static class Factory{

        private static final String API_KEY = "AIzaSyCKwJPuhwsbC4zAmamCecXLj0muugkqx8Y";

        public Retrofit create(){
            return new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public Call<Result> getDirections(String origin, String destination){
            Retrofit retrofit = create();
            DirectionsService directionsService = retrofit.create(DirectionsService.class);
            return directionsService.getDirections(origin, destination, API_KEY);
        }

    }


}
