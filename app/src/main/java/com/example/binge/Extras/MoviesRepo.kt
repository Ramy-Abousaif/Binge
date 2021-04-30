package com.example.binge.Extras

import com.example.binge.Models.MovieModel
import com.example.binge.Services.TMDBService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepo {

    private val TMDB_SERVICE: TMDBService
    var randomMovieTitle: String = ""
    var randomMoviePoster: String? = ""

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        TMDB_SERVICE = retrofit.create(TMDBService::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movieModels: List<MovieModel>) -> Unit,
        onError: () -> Unit
    ) {
        TMDB_SERVICE.getPopularMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movieModels)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getRandomMovies(
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        TMDB_SERVICE.getPopularMovies(page = (1..500).random())
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            val randomMovieIndex = ((0..19).random())
                            randomMovieTitle = responseBody.movieModels[randomMovieIndex].title
                            randomMoviePoster = responseBody.movieModels[randomMovieIndex].posterPath
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    public fun randomMovieTitle(): String
    {
        return randomMovieTitle
    }

    public fun randomMoviePoster(): String?
    {
        return randomMoviePoster
    }

    fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movieModels: List<MovieModel>) -> Unit,
        onError: () -> Unit
    ) {
        TMDB_SERVICE.getTopRatedMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movieModels)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getUpcomingMovies(
        page: Int = 1,
        onSuccess: (movieModels: List<MovieModel>) -> Unit,
        onError: () -> Unit
    ) {
        TMDB_SERVICE.getUpcomingMovies(page = page)
                .enqueue(object : Callback<MoviesResponse> {
                    override fun onResponse(
                        call: Call<MoviesResponse>,
                        response: Response<MoviesResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                onSuccess.invoke(responseBody.movieModels)
                            } else {
                                onError.invoke()
                            }
                        } else {
                            onError.invoke()
                        }
                    }

                    override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                        onError.invoke()
                    }
                })
    }
}