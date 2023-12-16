package com.sebastv.cine

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class FilmDataActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_EDIT_FILM = 1
        var ID_EDIT_FILM = ""

        // Variables estáticas para almacenar los valores modificados
        var urlImdbBoton: String = ""
        // var linkImg: String = ""

        var nombrePeliA: String = ""
        var anioPeliA: String = ""
        var directorPeliA: String = ""
        var imdbUrlA: String = ""
        var formatoPeliA: String = ""
        var generoPeliA: String = ""
        var comentariosPeliA: String = ""

        var nombrePeliB: String = ""
        var anioPeliB: String = ""
        var directorPeliB: String = ""
        var imdbUrlB: String = ""
        var formatoPeliB: String = ""
        var generoPeliB: String = ""
        var comentariosPeliB: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_data)

        // Obtener el filmId enviado desde la actividad FilmListActivity
        ID_EDIT_FILM = intent.getStringExtra(FilmListActivity.EXTRA_FILM_ID).toString()

        // Obtener el recurso de imagen según el id de la película
        val imageResourceId: Int = when (ID_EDIT_FILM) {
            "A" -> R.drawable.origins_movie
            "B" -> R.drawable.matrix_movie
            else -> -20
        }

        // Actualizar la imagen de la película segun el id
        val ivCartel: ImageView = findViewById(R.id.ivCartel)
        ivCartel.setImageResource(imageResourceId)

        // Actualizar los elementos de la interfaz según el id de la película
        when (ID_EDIT_FILM) {
            "A" -> {
                // Película A
                // Si aún no se editó nada, muestra la pelicula A
                if (nombrePeliA.isNullOrEmpty()){
                    actualizarInterfaz(getString(R.string.nombrePeliA), getString(R.string.anioPeliA), getString(R.string.directorPeliA), getString(R.string.imdb_url_A), getString(R.string.formatoPeliA), getString(R.string.GeneroPeliA), getString(R.string.comentariosA))
                } else {
                    // Luego de la edición muestra los nuevos valores
                    actualizarInterfaz(
                        nombrePeliA,
                        anioPeliA,
                        directorPeliA,
                        imdbUrlA,
                        formatoPeliA,
                        generoPeliA,
                        comentariosPeliA
                    )
                }
            }
            "B" -> {
                // Película B
                // Si aún no se editó nada, muestra la pelicula B
                if (nombrePeliB.isNullOrEmpty()){
                    actualizarInterfaz(getString(R.string.nombrePeliB), getString(R.string.anioPeliB), getString(R.string.directorPeliB),  getString(R.string.imdb_url_B), getString(R.string.formatoPeliB), getString(R.string.GeneroPeliB), getString(R.string.comentariosB))
                } else {
                    // Luego de la edición muestra los nuevos valores
                    actualizarInterfaz(
                        nombrePeliB,
                        anioPeliB,
                        directorPeliB,
                        imdbUrlB,
                        formatoPeliB,
                        generoPeliB,
                        comentariosPeliB
                    )
                }
            }
        }

        // Creacion de las val para los botones
        val btnVerEnIMDB: Button = findViewById(R.id.btnVerEnIMDB)
        val btnEditarPelic: Button = findViewById(R.id.btnEditarPelic)
        val btnVolverPrincipio: Button = findViewById(R.id.btnVolverPrincipio)

        // Manejador para el botón "Ver en IMDB"
        btnVerEnIMDB.setOnClickListener {
            val webpage = Uri.parse(urlImdbBoton)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(intent)
        }

        // Manejador para el botón "Editar Pelicula"
        btnEditarPelic.setOnClickListener {
            navigateToEdit()
        }

        // Manejador para el botón "Volver al Principio"
        btnVolverPrincipio.setOnClickListener {
            navigateToList()
        }
    }

    private fun navigateToEdit() {
        //val filmId =
        val intent = Intent(this,FilmEditActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("idedit", FilmDataActivity.ID_EDIT_FILM)
        startActivityForResult(intent, REQUEST_EDIT_FILM)
    }

    // Este método se llama cuando FilmEditActivity finaliza
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDIT_FILM && resultCode == RESULT_OK) {
            // El usuario guardó los cambios
            actualizarInterfazSegunID(data)
            Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()
        } else if (resultCode == RESULT_CANCELED) {
            // El usuario canceló la operación
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToList() {
        val intent= Intent(this,FilmListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun actualizarInterfaz(nombre: String, anio: String, director: String, enlaceIMDB: String, formato: String, genero: String, comentarios: String) {
        // Actualiza los elementos de la interfaz con la info de la película
        // que viene desde la otra actividad en el intent
        val tvNombrePelicula: TextView = findViewById(R.id.tvNombrePelicula)
        val tvAnioEstreno: TextView = findViewById(R.id.tvAnioEstreno)
        val tvDirector: TextView = findViewById(R.id.tvDirector)
        val tvFormato: TextView = findViewById(R.id.tvGenero)
        val tvGenero: TextView = findViewById(R.id.tvFormato)
        val tvComentarios: TextView = findViewById(R.id.tvComentarios)


        tvNombrePelicula.text = nombre
        tvAnioEstreno.text = anio
        tvDirector.text = director
        tvFormato.text = formato
        tvGenero.text = genero
        tvComentarios.text = comentarios
        urlImdbBoton = enlaceIMDB
    }

    // Actualizar los elementos de la interfaz según el id de la película
    private fun actualizarInterfazSegunID(data: Intent?) {
        when (ID_EDIT_FILM) {
            "A" -> {
                // Película A
                actualizarInterfaz(
                    data?.getStringExtra("nombre") ?: "Ingresar nombre",
                    data?.getStringExtra("anio") ?: "Ingresar anio",
                    data?.getStringExtra("director") ?: "Ingresar director",
                    data?.getStringExtra("enlaceImdb") ?: "",
                    data?.getStringExtra("formato") ?: "Ingresar director",
                    data?.getStringExtra("genero") ?: "Ingresar director",
                    data?.getStringExtra("comentario") ?: "Ingresar comentarios"
                )
            }
            "B" -> {
                // Película B
                actualizarInterfaz(
                    data?.getStringExtra("nombre") ?: "Ingresar nombre",
                    data?.getStringExtra("anio") ?: "Ingresar anio",
                    data?.getStringExtra("director") ?: "Ingresar director",
                    data?.getStringExtra("enlaceImdb") ?: "",
                    data?.getStringExtra("formato") ?: "",
                    data?.getStringExtra("genero") ?: "",
                    data?.getStringExtra("comentario") ?: "Ingresar comentarios"
                )
            }
        }
    }
}