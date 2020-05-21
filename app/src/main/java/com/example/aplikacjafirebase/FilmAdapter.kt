package com.example.aplikacjafirebase

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso


class FilmAdapter (var mCtx:Context,var resources:Int,var items:List<FilmModel>):ArrayAdapter<FilmModel>(mCtx,resources,items){
     override fun getView(position: Int, convertView: View?, parent:ViewGroup):View{
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources,null)

        val imageView:ImageView = view.findViewById(R.id.iv_film_poster)
        val titleTextView:TextView = view.findViewById(R.id.tv_film_title)
        val genreTextView:TextView = view.findViewById(R.id.tv_film_genre)
        val yearTextView:TextView = view.findViewById(R.id.tv_film_year)

        val directorTextView:TextView = view.findViewById(R.id.tv_film_director)
        val actorsTextView:TextView = view.findViewById(R.id.tv_film_actors)
        val plotTextView:TextView = view.findViewById(R.id.tv_film_plot)
        val watchedTextView:TextView = view.findViewById(R.id.tv_watched)
        val layout:LinearLayout = view.findViewById(R.id.linearLayoutFilms)

        var mItem:FilmModel = items[position]
        Picasso.get().load(mItem.poster).into(imageView)
        titleTextView.text = mItem.title
        yearTextView.text = mItem.year
        genreTextView.text = mItem.genre
        if(mItem.watched == "FILM ALREADY WATCHED"){
           watchedTextView.text = mItem.watched
           layout.setBackgroundColor(Color.parseColor("#56A2EF"))
           watchedTextView.setBackgroundColor(Color.parseColor("#56A2EF"))
        }
        else{
           watchedTextView.text = "WAITIN TO BE WATCHED"
        }

        directorTextView.text = "Director: " + mItem.director
        actorsTextView.text = "Actors: " + mItem.actors
        plotTextView.text = "" + mItem.plot


        return view
    }
}