package com.firstapp.movie_app.model

data class MovieResponse (
    val page: Int,
    val results: ArrayList<Movies>
)