package com.example.skripsigre.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Use 10.0.2.2 to redirect android to localhost
    private static final String BASE_URL = "http://10.0.2.2/apprute/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}