package com.sebastv.cine

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class FilmEditActivity : AppCompatActivity() {
    companion object {
        var ID_EDIT = ""
    }
    // Inputs
    private lateinit var etTituloPeli: EditText
    private lateinit var etDirectorPeli: EditText
    private lateinit var etAnioPeli: EditText
    private lateinit var etEnlaceIMDB: EditText
    private lateinit var etComentarios: EditText
    private lateinit var spnFormato: Spinner
    private lateinit var spnGenero: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_edit)

        // Obtener el filmId enviado desde la otra actividad
        ID_EDIT = intent.getStringExtra("idedit").toString()

        // Se inicializan los EditText y Spinners
        etTituloPeli = findViewById(R.id.etTituloPeli)
        etDirectorPeli = findViewById(R.id.etDirectorPeli)
        etAnioPeli = findViewById(R.id.etAnioPeli)
        etEnlaceIMDB = findViewById(R.id.etEnlaceIMDB)
        etComentarios = findViewById(R.id.etComentarios)
        spnFormato = findViewById<View?>(R.id.spnFormato) as Spinner
        spnGenero =  findViewById<View?>(R.id.spnGenero) as Spinner

        // Actualiza los elementos de la interfaz según el id de la película
        when (ID_EDIT) {
            "A" -> {
                // Película A
                val nombreA = getString(R.string.nombrePeliA)
                val anioA = getString(R.string.anioPeliA)
                val directorA = getString(R.string.directorPeliA)
                val imdbA = getString(R.string.imdb_url_A)
                val comentariosA = getString(R.string.comentariosA)

                etTituloPeli.setText(nombreA)
                etAnioPeli.setText(anioA)
                etDirectorPeli.setText(directorA)
                etEnlaceIMDB.setText(imdbA)
                etComentarios.setText(comentariosA)
            }
            "B" -> {
                // Película B
                val nombreB = getString(R.string.nombrePeliB)
                val anioB = getString(R.string.anioPeliB)
                val directorB = getString(R.string.directorPeliB)
                val imdbB = getString(R.string.imdb_url_B)
                val comentariosB = getString(R.string.comentariosB)

                etTituloPeli.setText(nombreB)
                etAnioPeli.setText(anioB)
                etDirectorPeli.setText(directorB)
                etEnlaceIMDB.setText(imdbB)
                etComentarios.setText(comentariosB)
            }
        }

        // Botones
        val btnTomarFoto: Button = findViewById(R.id.btnTomarFoto)
        val btnElegirImg: Button = findViewById(R.id.btnElegirImg)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)

        // Manejadores para botones
        // Manejador para el botón "Tomar Foto"
        btnTomarFoto.setOnClickListener {

        }

        // Manejador para el botón "Elegir Imagen"
        btnElegirImg.setOnClickListener {

        }

        // Manejador para el botón "Guardar"
        btnGuardar.setOnClickListener {
            save()
        }

        // Manejador para el botón "Cancelar"
        btnCancelar.setOnClickListener {
            cancel()
        }
    }

    // Funcion que establece resultado a OK y pasa los datos editados
    private fun save() {
        // Obtener datos editados de los EditText
        val nuevoNombre = etTituloPeli.text.toString()
        val nuevoAnio = etAnioPeli.text.toString()
        val nuevoDirector = etDirectorPeli.text.toString()
        val nuevoImdb = etEnlaceIMDB.text.toString()
        val nuevoComentario = etComentarios.text.toString()
        val opcFormato = spnFormato.selectedItem.toString()
        val opcGenero = spnGenero.selectedItem.toString()

        val resultIntent = Intent()
        resultIntent.putExtra("nombre", nuevoNombre)
        resultIntent.putExtra("anio", nuevoAnio)
        resultIntent.putExtra("director", nuevoDirector)
        resultIntent.putExtra("enlaceImdb", nuevoImdb)
        resultIntent.putExtra("formato", opcFormato)
        resultIntent.putExtra("genero", opcGenero)
        resultIntent.putExtra("comentario", nuevoComentario)

        // Obtener el identificador de la película actual

        // Actualizar las variables estáticas según el identificador de la película
        when (ID_EDIT) {
            "A" -> {
                FilmDataActivity.nombrePeliA = nuevoNombre
                FilmDataActivity.anioPeliA = "Año: $nuevoAnio"
                FilmDataActivity.directorPeliA = "Director: $nuevoDirector"
                FilmDataActivity.imdbUrlA = nuevoImdb
                FilmDataActivity.formatoPeliA = opcFormato
                FilmDataActivity.generoPeliA = opcGenero
                FilmDataActivity.comentariosPeliA = nuevoComentario
            }
            "B" -> {
                FilmDataActivity.nombrePeliB = nuevoNombre
                FilmDataActivity.anioPeliB = "Año: $nuevoAnio"
                FilmDataActivity.directorPeliB = "Director: $nuevoDirector"
                FilmDataActivity.imdbUrlB = nuevoImdb
                FilmDataActivity.formatoPeliB = opcFormato
                FilmDataActivity.generoPeliB = opcGenero
                FilmDataActivity.comentariosPeliB = nuevoComentario
            }
        }

        // Se coloca el resultado a ok y se envían los nuevos valores
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    // Funcion que establece resultado a CANCEL
    // Muestra un mensaje de cancelado y te manda a la pantalla anterior
    private fun cancel() {
        setResult(Activity.RESULT_CANCELED)
        Toast.makeText(this,"Operación cancelada", Toast.LENGTH_SHORT).show()
        finish()
    }
}