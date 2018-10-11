package com.sawrose.movielist.features.model

data class MovieDetailsView(val id: Int,
        val title: String,
        val poster: String,
        val summary: String,
        val cast: String,
        val director: String,
        val year: Int,
        val trailer: String)