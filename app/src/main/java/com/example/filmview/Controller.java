package com.example.filmview;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class Controller {
    private static final String BASE_URL = "http://s3-eu-west-1.amazonaws.com/sequeniatesttask/";

    static RetrofitService getApi() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(RetrofitService.class);

    }
}