package com.example.aplikacjafirebase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.database.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search_film.*

class SearchFilm : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    private var disposable: Disposable? = null

    private val filmService by lazy {
        FilmService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_film)

        val profileName = intent.getStringExtra("Username")

        ref = FirebaseDatabase.getInstance().getReference("films" + profileName)

        search_button.setOnClickListener {
            if (et_search.text.toString().isNotEmpty()) {
                beginSearch(et_search.text.toString())
                val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(et_search.windowToken, 0)
            }
        }

        val listView = findViewById<ListView>(R.id.listViewSearchFilms)
        listView.setOnItemClickListener { parent, view, position, id ->
            val popup = PopupMenu(this, view)
            popup.inflate(R.menu.search_film_menu)
            popup.setOnMenuItemClickListener {
                val item = parent.getItemAtPosition(position) as FilmSearchModel
                addFilm(item.imdbID, profileName)
                true
            }
            popup.show()

        }

        back_to_my_films.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Username", profileName)
            startActivity(intent)
            finish()
        }
    }

    private fun beginSearch(searchString: String) {
        var listview = findViewById<ListView>(R.id.listViewSearchFilms)
        var list = mutableListOf<FilmSearchModel>()

        disposable = filmService.searchFilm(searchString, "38317b82")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    if (result.Search != null) {
                        var filmList: List<FilmSearchApiModel.Films> = result.Search
                        for (f in filmList) {
                            list.add(FilmSearchModel(f.Title, f.Year, f.imdbID, f.Poster, f.Type))
                        }
                        listview.adapter = SearchFilmAdapter(this, R.layout.search_films_row, list)
                    } else {
                        Toast.makeText(
                            this,
                            "No results",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                ,
                { error ->
                    Toast.makeText(
                        this,
                        error.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            )

    }

    private fun addFilm(imdbID: String, profileName: String) {
        disposable = filmService.findFilmByImdbID(imdbID, "38317b82")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    if (result != null) {
                        var film: FilmApiModel.Result = result
                        if (film.Title != null) {
                            ref.child(film.imdbID).setValue(film).addOnCompleteListener {
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("Username", profileName)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Operation fail.Please try again later",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Operation fail.Please try again later",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                ,
                { error ->
                    Toast.makeText(
                        this,
                        error.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            )

    }
}
