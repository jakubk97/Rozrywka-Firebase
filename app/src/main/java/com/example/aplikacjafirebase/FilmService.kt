package com.example.aplikacjafirebase

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

interface FilmService {

    @GET("?")
    fun searchFilm(@Query("s") name: String,
                   @Query("apikey") apikey: String): Observable<FilmSearchApiModel.Result>

    @GET("?")
    fun findFilmByImdbID(@Query("i") name: String,
                         @Query("apikey") apikey: String): Observable<FilmApiModel.Result>

    companion object {
        fun create(): FilmService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.omdbapi.com/")
                .build()

            return retrofit.create(FilmService::class.java)
        }
    }

}