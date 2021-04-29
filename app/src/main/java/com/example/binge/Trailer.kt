package com.example.binge

import com.google.gson.annotations.SerializedName

class Trailer (key: String, name: String) {
    @SerializedName("key") var key: String
    @SerializedName("name") var name: String

    init {
        this.key = key
        this.name = name
    }

    public fun getMovieKey(): String
    {
        return key
    }

    public fun setMovieKey(key: String)
    {
        this.key = key
    }

    public fun getMovieName(): String
    {
        return name
    }

    public fun setMovieName(name: String)
    {
        this.name = name
    }
}