package com.example.aplikacjafirebase

import java.lang.reflect.Constructor

class FilmModel(
    val imdbID: String,
    val title: String,
    val year: String,
    val rated: String,
    val released: String,
    val runtime: String,
    val genre: String,
    val director: String,
    val actors: String,
    val plot: String,
    val language: String,
    val country: String,
    val awards: String,
    val poster: String,
    val type: String,
    var watched: String

) {
    constructor() : this("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "") {

    }
}

