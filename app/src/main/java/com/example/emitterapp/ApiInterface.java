package com.example.emitterapp;

import com.example.emitterapp.pojo.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("users")
    public Call<List<Users>> getAllUsers();



}

