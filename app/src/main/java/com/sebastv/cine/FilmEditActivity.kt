package com.sebastv.cine

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

class FilmEditActivity : AppCompatActivity() {
    companion object {
        var ID_EDIT = ""
        const val IMAGE_URI_KEY = "imageUri"
        const val PEDIDO_CAPTURA_IMAGEN = 1
        const val PEDIDO_ELEGIR_IMAGEN = 2
        var DEFAULT_IMAGE_RESOURCE = R.drawable.insertar_img
        private val CODIGO_DE_SOLICITUD_DE_PERMISO = 42
        // Variable para almacenar el URI de la img
        var imageUri: Uri? = null
    }
    // Inputs e imagen
    private lateinit var ivFotoPeli: ImageView
    private lateinit var etTituloPeli: EditText
    private lateinit var etDirectorPeli: EditText
    private lateinit var etAnioPeli: EditText
    private lateinit var etEnlaceIMDB: EditText
    private lateinit var etComentarios: EditText
    private lateinit var spnFormato: Spinner
    private lateinit var spnGenero: Spinner


    // Propiedad para almacenar el Intent de resultado
    private lateinit var resultIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_edit)

        if (savedInstanceState != null) {
            val savedImageUri = savedInstanceState.getString(IMAGE_URI_KEY)
            if (!savedImageUri.isNullOrEmpty()) {
                imageUri = Uri.parse(savedImageUri)
                ivFotoPeli.setImageURI(imageUri)
            }
        }

        // Verifica si se tiene el permiso
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Si se tiene el permiso, realiza la operación que necesitas
            // Puedes poner aquí la lógica para cargar una imagen, si es necesario
        } else {
            // Si no se tiene el permiso, solicítalo al usuario
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                CODIGO_DE_SOLICITUD_DE_PERMISO
            )
        }

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
        ivFotoPeli = findViewById(R.id.ivFotoPeli)

        // Botones
        val btnTomarFoto: Button = findViewById(R.id.btnTomarFoto)
        val btnElegirImg: Button = findViewById(R.id.btnElegirImg)
        val btnGuardar: Button = findViewById(R.id.btnGuardar)
        val btnCancelar: Button = findViewById(R.id.btnCancelar)

        // Actualiza los elementos de la interfaz según el id de la película
        // para pre-rellenar los EditTexts
        when (ID_EDIT) {
            "A" -> {
                // Película A
                val nombreA = getString(R.string.nombrePeliA)
                val anioA = getString(R.string.anioPeliA)
                val directorA = getString(R.string.directorPeliA)
                val imdbA = getString(R.string.imdb_url_A)
                val comentariosA = getString(R.string.comentariosA)
                val imageResourceId: Int = R.drawable.insertar_img

                etTituloPeli.setText(nombreA)
                etAnioPeli.setText(anioA)
                etDirectorPeli.setText(directorA)
                etEnlaceIMDB.setText(imdbA)
                etComentarios.setText(comentariosA)
                ivFotoPeli.setImageResource(imageResourceId)
            }
            "B" -> {
                // Película B
                val nombreB = getString(R.string.nombrePeliB)
                val anioB = getString(R.string.anioPeliB)
                val directorB = getString(R.string.directorPeliB)
                val imdbB = getString(R.string.imdb_url_B)
                val comentariosB = getString(R.string.comentariosB)
                val imageResourceId: Int = R.drawable.insertar_img

                etTituloPeli.setText(nombreB)
                etAnioPeli.setText(anioB)
                etDirectorPeli.setText(directorB)
                etEnlaceIMDB.setText(imdbB)
                etComentarios.setText(comentariosB)
                ivFotoPeli.setImageResource(imageResourceId)
            }
        }

        // Manejadores para botones
        // Manejador para el botón "Tomar Foto"
        btnTomarFoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                lanzarTomarFotoIntent()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CAMERA),
                    CODIGO_DE_SOLICITUD_DE_PERMISO
                )
            }
        }


        // Manejador para el botón "Elegir Imagen"
        btnElegirImg.setOnClickListener {
            lanzarElegirImagenIntent()
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

        // Crear el Intent de resultado
        resultIntent = Intent()
        resultIntent.apply {
            // Asignar un valor predeterminado a imageUri si es null
            val uri = imageUri ?: Uri.parse("android.resource://$packageName/${DEFAULT_IMAGE_RESOURCE}")
            putExtra(IMAGE_URI_KEY, uri.toString())
            putExtra("nombre", nuevoNombre)
            putExtra("anio", nuevoAnio)
            putExtra("director", nuevoDirector)
            putExtra("enlaceImdb", nuevoImdb)
            putExtra("formato", opcFormato)
            putExtra("genero", opcGenero)
            putExtra("comentario", nuevoComentario)
        }

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

    // Función para solicitud de permisos de la cámara
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CODIGO_DE_SOLICITUD_DE_PERMISO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                lanzarTomarFotoIntent()
            } else {
                Toast.makeText(
                    this,
                    "Permiso de la cámara denegado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Función para manejar el resultado de la camara o la galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PEDIDO_CAPTURA_IMAGEN -> {
                if (resultCode == RESULT_OK && data != null) {
                    val imageBitmap = data.extras?.get("data") as Bitmap?
                    imageUri = getImageUri(imageBitmap)
                    ivFotoPeli.setImageURI(imageUri)
                }
            }
            PEDIDO_ELEGIR_IMAGEN -> {
                if (resultCode == RESULT_OK && data != null) {
                    data.data?.let {
                        imageUri = it
                        ivFotoPeli.setImageURI(imageUri)
                    }
                }
            }
        }
    }

    // Función para iniciar la actividad de la cámara
    private fun lanzarTomarFotoIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, PEDIDO_CAPTURA_IMAGEN)
            }
        }
    }

    private fun lanzarElegirImagenIntent() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickIntent, PEDIDO_ELEGIR_IMAGEN)
    }

    private fun getImageUri(inImage: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        inImage?.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            inImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }
}