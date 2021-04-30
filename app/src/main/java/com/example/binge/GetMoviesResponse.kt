package com.example.binge
import com.example.binge.Models.MovieModel
import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movieModels: List<MovieModel>,
    @SerializedName("total_pages") val pages: Int
)