package com.sebastv.cine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FilmListActivity : AppCompatActivity() {
    companion object {
        val EXTRA_FILM_ID = "EXTRA_FILM_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        val btnPeliculaA: Button = findViewById(R.id.btnPelicRelac)
        val btnPeliculaB: Button = findViewById(R.id.btnEditarPelic)
        val btnAcercaDe: Button = findViewById(R.id.btnVolverPrincipio)

        // Manejador para el botón "Ver Pelicula A"
        btnPeliculaA.setOnClickListener {
            val filmId = "A"
            navigateToDataFilm(filmId)
        }

        // Manejador para el botón "Ver Pelicula B"
        btnPeliculaB.setOnClickListener {
            val filmId = "B"
            navigateToDataFilm(filmId)
        }

        // Manejador para el botón "Acerca De"
        btnAcercaDe.setOnClickListener {
            navigateToAbout()
        }
    }

    // Funcion para navegar al Activity About
    private fun navigateToAbout() {
        val intent = Intent(this, AboutActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    // Funcion para navegar al Activity DataFilm
    private fun navigateToDataFilm(filmId: String) {
        val intent = Intent(this, FilmDataActivity::class.java)
        intent.putExtra(FilmListActivity.EXTRA_FILM_ID, filmId)
        startActivity(intent)
    }
}