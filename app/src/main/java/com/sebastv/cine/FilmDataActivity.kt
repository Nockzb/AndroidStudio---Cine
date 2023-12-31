package com.sebastv.cine

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.sebastv.cine.FilmEditActivity.Companion.IMAGE_URI_KEY
import com.sebastv.cine.FilmEditActivity.Companion.PEDIDO_ELEGIR_IMAGEN
import java.io.InputStream


class FilmDataActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_EDIT_FILM = 1
        var ID_EDIT_FILM = ""
        // Variable para vincular con el ImageView
        lateinit var ivCartel: ImageView

        // Variables estáticas para almacenar los valores modificados
        lateinit var urlImdbBoton: String

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

    private var uriImg: Uri? = FilmEditActivity.imageUri
    private var editedData: Intent? = null
    private lateinit var resultIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_data)

        // Obtener el filmId enviado desde la actividad FilmListActivity
        ID_EDIT_FILM = intent.getStringExtra(FilmListActivity.EXTRA_FILM_ID).toString()

        // Se Inicializa el resultIntent
        resultIntent = Intent()

        // Inicializar uriImg con un valor por defecto
        // uriImg = Uri.parse("android.resource://$packageName/${FilmEditActivity.DEFAULT_IMAGE_RESOURCE}")

        // Se inicializa el ImageView
        ivCartel = findViewById(R.id.ivCartel)
        // ivCartel.setImageResource(imageResourceId)

        if (editedData != null) {
            val newImageUriString = editedData!!.getStringExtra(IMAGE_URI_KEY)
            if (!newImageUriString.isNullOrEmpty()) {
                try {
                    uriImg = Uri.parse(newImageUriString)
                    ivCartel.setImageURI(uriImg)
                } catch (e: Exception) {
                    // Manejar la excepción
                    e.printStackTrace()
                }
            }
        }

        // Actualizar los elementos de la interfaz según el id de la película
        when (ID_EDIT_FILM) {
            "A" -> {
                // Película A
                // Si aún no se editó nada, muestra la pelicula A
                if (nombrePeliA.isNullOrEmpty()){
                    // Variable para generar el Uri de la imagen
                    val imgGuardada: Int = R.drawable.origins_movie
                    val uriImgGuardada: Uri = Uri.parse("android.resource://$packageName/$imgGuardada")
                    actualizarInterfaz(getString(R.string.nombrePeliA), getString(R.string.anioPeliA), getString(R.string.directorPeliA), getString(R.string.imdb_url_A), getString(R.string.formatoPeliA), getString(R.string.GeneroPeliA), getString(R.string.comentariosA), uriImgGuardada)
                } else {
                    // Luego de la edición muestra los nuevos valores
                    if (editedData != null) {
                        val newImageUriString = editedData!!.getStringExtra(IMAGE_URI_KEY)
                        if (!newImageUriString.isNullOrEmpty()) {
                            try {
                                uriImg = Uri.parse(newImageUriString)
                                ivCartel.setImageURI(uriImg)
                            } catch (e: Exception) {
                                // Manejar la excepción
                                e.printStackTrace()
                            }
                        }
                    }
                    actualizarInterfaz(
                        nombrePeliA,
                        anioPeliA,
                        directorPeliA,
                        imdbUrlA,
                        formatoPeliA,
                        generoPeliA,
                        comentariosPeliA,
                        uriImg
                    )
                }
            }
            "B" -> {
                // Película B
                // Si aún no se editó nada, muestra la pelicula B
                if (nombrePeliB.isNullOrEmpty()){
                    val imgGuardada: Int = R.drawable.matrix_movie
                    val uriImgGuardada: Uri = Uri.parse("android.resource://$packageName/$imgGuardada")
                    actualizarInterfaz(getString(R.string.nombrePeliB), getString(R.string.anioPeliB), getString(R.string.directorPeliB),  getString(R.string.imdb_url_B), getString(R.string.formatoPeliB), getString(R.string.GeneroPeliB), getString(R.string.comentariosB), uriImgGuardada)
                } else {
                    // Luego de la edición muestra los nuevos valores
                    if (editedData != null) {
                        val newImageUriString = editedData!!.getStringExtra(IMAGE_URI_KEY)
                        if (!newImageUriString.isNullOrEmpty()) {
                            try {
                                uriImg = Uri.parse(newImageUriString)
                                ivCartel.setImageURI(uriImg)
                            } catch (e: Exception) {
                                // Manejar la excepción
                                e.printStackTrace()
                            }
                        }
                    }
                    actualizarInterfaz(
                        nombrePeliB,
                        anioPeliB,
                        directorPeliB,
                        imdbUrlB,
                        formatoPeliB,
                        generoPeliB,
                        comentariosPeliB,
                        uriImg
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
        // Logica para REQUEST_EDIT_FILM
        if (requestCode == REQUEST_EDIT_FILM && resultCode == RESULT_OK) {
            // El usuario guardó los cambios
            actualizarInterfazSegunID(data)
            Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show()

            setResult(Activity.RESULT_OK, resultIntent)
        } else if (resultCode == RESULT_CANCELED) {
            // El usuario canceló la operación
            Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show()
        }

        // Manejo para la selección de imágenes
        if (requestCode == PEDIDO_ELEGIR_IMAGEN && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data

            // Actualizar la vista previa de la imagen
            ivCartel.setImageURI(imageUri)

            // Inicializar resultIntent antes de usarla
            resultIntent = Intent()

            // Actualizar resultIntent con la nueva imageUri
            resultIntent.putExtra(IMAGE_URI_KEY, imageUri.toString())
        }

        // Almacenar la información en la variable editedData
        editedData = data

        // Almacenar la información en la variable imageUri
        uriImg = try {
            Uri.parse(data?.getStringExtra(IMAGE_URI_KEY))
        } catch (e: Exception) {
            Uri.parse("android.resource://$packageName/${FilmEditActivity.DEFAULT_IMAGE_RESOURCE}")
        }

        // Inicializar resultIntent antes de usarla
        resultIntent = Intent()

        // Actualizar resultIntent con la nueva imageUri
        resultIntent.putExtra(IMAGE_URI_KEY, uriImg.toString())
    }

    private fun navigateToList() {
        val intent= Intent(this,FilmListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun actualizarInterfaz(nombre: String, anio: String, director: String, enlaceIMDB: String, formato: String, genero: String, comentarios: String, imageUri: Uri?) {
        // Actualiza los elementos de la interfaz con la info de la película
        // que viene desde la otra actividad en el intent
        val tvNombrePelicula: TextView = findViewById(R.id.tvNombrePelicula)
        val tvAnioEstreno: TextView = findViewById(R.id.tvAnioEstreno)
        val tvDirector: TextView = findViewById(R.id.tvDirector)
        val tvFormato: TextView = findViewById(R.id.tvGenero)
        val tvGenero: TextView = findViewById(R.id.tvFormato)
        val tvComentarios: TextView = findViewById(R.id.tvComentarios)

        // Cargar la imagen desde el URI
        // ivCartel.setImageURI(imageUri)
        try {
            val inputStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
            val bitmap = BitmapFactory.decodeStream(inputStream)
            ivCartel.setImageBitmap(bitmap)
            inputStream?.close()
        } catch (e: Exception) {
            // Manejar la excepción
            e.printStackTrace()
        }

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
            "A", "B" -> {
                // Recuperar el URI de la imagen como cadena y convertirlo a Uri
                val uriString = data?.getStringExtra(IMAGE_URI_KEY)
                uriImg = Uri.parse(uriString)
                // Película A
                actualizarInterfaz(
                    data?.getStringExtra("nombre") ?: "Ingresar nombre",
                    data?.getStringExtra("anio") ?: "Ingresar anio",
                    data?.getStringExtra("director") ?: "Ingresar director",
                    data?.getStringExtra("enlaceImdb") ?: "",
                    data?.getStringExtra("formato") ?: "Ingresar director",
                    data?.getStringExtra("genero") ?: "Ingresar director",
                    data?.getStringExtra("comentario") ?: "Ingresar comentarios",
                    uriImg
                )
            }
        }
    }
}