package com.keyvani.hiltretrofitmovieapp.server

import com.keyvani.hiltretrofitmovieapp.model.ResponseMovieDetails
import com.keyvani.hiltretrofitmovieapp.model.ResponseMovieList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {
    @GET("movies")
    fun moviesList(): Call<ResponseMovieList>

    @GET("movies/{movie_id}")
    fun movieDetails(@Path("movie_id") id: Int): Call<ResponseMovieDetails>


}