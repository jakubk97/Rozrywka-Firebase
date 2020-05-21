package com.example.aplikacjafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.PopupMenu
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileName = intent.getStringExtra("Username")

        search_films_button.setOnClickListener {
            val intent = Intent(this, SearchFilm::class.java)
            intent.putExtra("Username", profileName)
            startActivity(intent)
            finish()
        }

        val listView = findViewById<ListView>(R.id.listViewMyFilms)
        listView.setOnItemClickListener { parent, view, position, id ->
            val popup = PopupMenu(this, view)
            popup.inflate(R.menu.my_film_menu)
            popup.setOnMenuItemClickListener {
                val item = parent.getItemAtPosition(position) as FilmModel
                if (it.title == "Delete") {
                    deleteFilm(item.imdbID)
                } else {
                    setWatched(item.imdbID, item)
                }
                true
            }
            popup.show()

        }

        ref = FirebaseDatabase.getInstance().getReference("films" + profileName)

        var listview = findViewById<ListView>(R.id.listViewMyFilms)

        var list = mutableListOf<FilmModel>()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0!!.exists()) {
                    list.clear()
                    for (f in p0.children) {
                        val film = f.getValue(FilmModel::class.java)
                        list.add(film!!)
                    }
                    saveList(listview, list)
                }
            }
        })


//DODAWANIE DO LISTY
//        val filmId = ref.push().key.toString()
//        val film = FilmModel(
//            filmId,
//            "Guardians of the Galaxy Vol. 2",
//            "2017",
//            "PG-13",
//            "05 May 2017",
//            "136 min",
//            "Action, Adventure, Comedy, Sci-Fi",
//            "James Gunn",
//            "Chris Pratt, Zoe Saldana, Dave Bautista, Vin Diesel",
//            "The Guardians struggle to keep together as a team while dealing with their personal family issues, notably Star-Lord's encounter with his father the ambitious celestial being Ego.",
//            "English",
//            "USA",
//            "Nominated for 1 Oscar. Another 15 wins & 56 nominations.",
//            "https://m.media-amazon.com/images/M/MV5BNjM0NTc0NzItM2FlYS00YzEwLWE0YmUtNTA2ZWIzODc2OTgxXkEyXkFqcGdeQXVyNTgwNzIyNzg@._V1_SX300.jpg",
//            "movie"
//        )
//
//        list.add(film)
//
//        ref.child(filmId).setValue(film).addOnCompleteListener {
//            Toast.makeText(applicationContext, "Dodano wartość", Toast.LENGTH_LONG)
//        }


    }

    private fun setWatched(imdbID: String, item: FilmModel) {
        ref.child(imdbID).removeValue()
        item.watched = "FILM ALREADY WATCHED"
        ref.child(imdbID).setValue(item)
    }


    private fun deleteFilm(key: String) {
        ref.child(key).removeValue()
    }

    fun saveList(listview: ListView, list: List<FilmModel>) {
        listview.adapter = FilmAdapter(this, R.layout.my_films_row, list)
    }
}


