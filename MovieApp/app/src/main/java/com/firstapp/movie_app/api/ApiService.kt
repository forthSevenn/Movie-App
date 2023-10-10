package com.firstapp.movie_app.api

import com.firstapp.movie_app.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Call<MovieResponse>
}