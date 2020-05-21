package com.example.aplikacjafirebase

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import java.net.URL


class SearchFilmAdapter (var mCtx:Context, var resources:Int, var items:List<FilmSearchModel>):ArrayAdapter<FilmSearchModel>(mCtx,resources,items){
     override fun getView(position: Int, convertView: View?, parent:ViewGroup):View{
        val layoutInflater:LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(resources,null)

        val imageView:ImageView = view.findViewById(R.id.iv_film_poster)
        val yearTextView:TextView = view.findViewById(R.id.tv_film_year)
        val titleTextView:TextView = view.findViewById(R.id.tv_film_title)
        val typeTextView:TextView = view.findViewById(R.id.tv_film_type)
        var mItem:FilmSearchModel = items[position]
        Picasso.get().load(mItem.poster).into(imageView)
        titleTextView.text = mItem.title
        yearTextView.text = mItem.year
        typeTextView.text = mItem.type


        return view
    }
}