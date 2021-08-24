package com.example.skripsigre.api;
import com.example.skripsigre.Routes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RoutesApi {

    @GET("routes.php")
    Call<List<Routes>> getRoutes();
}
