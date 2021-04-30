package com.example.binge.Services

import com.example.binge.Extras.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "9e1b281c7090884268f7269a0f11525f",
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "9e1b281c7090884268f7269a0f11525f",
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
            @Query("api_key") apiKey: String = "9e1b281c7090884268f7269a0f11525f",
            @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("watch/providers/movie")
    fun getWatchProviders(
        @Query("api_key") apiKey: String = "9e1b281c7090884268f7269a0f11525f"
    ): Call<MoviesResponse>

    @GET("genre/movie/list")
    fun getGenre(
        @Query("api_key") apiKey: String = "9e1b281c7090884268f7269a0f11525f"
    ): Call<MoviesResponse>
}