package com.example.binge

import com.google.gson.annotations.SerializedName

class GetTrailerResponse {
    @SerializedName("id") var id_trailer: Int = 0
    @SerializedName("results")
    lateinit var results_trailer: List<Trailer>

    public fun getID(): Int
    {
        return id_trailer
    }

    public fun setID(id_trailer: Int)
    {
        this.id_trailer = id_trailer
    }

    public fun getResults(): List<Trailer>
    {
        return results_trailer
    }
}