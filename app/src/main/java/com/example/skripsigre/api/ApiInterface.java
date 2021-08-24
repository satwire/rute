package com.example.skripsigre.api;

import com.example.skripsigre.Routes;
import com.example.skripsigre.model.login.Login;
import com.example.skripsigre.model.register.Register;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginResponse(@Field("username") String username, @Field("password") String password

    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerResponse(@Field("username") String username, @Field("password") String password,
            @Field("name") String name

    );
}