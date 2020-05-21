package com.example.aplikacjafirebase

object FilmSearchApiModel {
    data class Result(val Search: List<Films>, val totalResults: String, val Response: String)
    data class Films(
        val Title: String,
        val Year: String,
        val imdbID: String,
        val Type: String,
        val Poster: String
    )
}

