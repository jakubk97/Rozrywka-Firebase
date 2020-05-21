package com.example.aplikacjafirebase

object FilmApiModel {
    data class Result(
        val imdbID: String,
        val Title: String,
        val Year: String,
        val Rated: String,
        val Released: String,
        val Runtime: String,
        val Genre: String,
        val Director: String,
        val Actors: String,
        val Plot: String,
        val Language: String,
        val Country: String,
        val Awards: String,
        val Poster: String,
        val Type: String
    )
}

