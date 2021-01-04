package com.assignment.api;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {



    @Retry
    @GET("json?location=47.6204,-122.3491&radius=2500&type=restaurant&key=AIzaSyBnEVruXG2tdtbadZtqLwt9EmFI6XYEOV0")
    Call<JsonObject> RestList();
}
