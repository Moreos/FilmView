package com.example.filmview;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitService {
    @GET("films.json")
    Call<FilmsItems> getFilms();
}
