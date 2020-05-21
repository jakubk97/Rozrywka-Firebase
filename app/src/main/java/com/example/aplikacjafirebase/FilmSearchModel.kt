package com.example.aplikacjafirebase
import java.lang.reflect.Constructor

class FilmSearchModel(
    val title: String,
    val year: String,
    val imdbID:String,
    val poster: String,
    val type: String
) {
    constructor() : this("", "", "", "", "") {

    }
}

