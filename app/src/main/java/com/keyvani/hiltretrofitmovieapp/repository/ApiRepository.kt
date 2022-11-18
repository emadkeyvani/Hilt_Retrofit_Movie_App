package com.keyvani.hiltretrofitmovieapp.repository

import com.keyvani.hiltretrofitmovieapp.server.ApiServices
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(private var api:ApiServices){
    fun getAllMovies()= api.moviesList()
    fun getMovieDetails(id: Int) =api.movieDetails(id)
}