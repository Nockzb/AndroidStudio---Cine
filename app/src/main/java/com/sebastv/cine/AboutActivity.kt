package com.sebastv.cine

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class AboutActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val btnIrWeb: Button = findViewById(R.id.btnPelicRelac)
        val btnSoporte: Button = findViewById(R.id.btnEditarPelic)
        val btnVolver: Button = findViewById(R.id.btnVolverPrincipio)

        // Manejador para el botón "Ir al Sitio Web"
        btnIrWeb.setOnClickListener {
            val webpage = Uri.parse("https://www.unsplash.com/es")
            val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(intent)
        }

        // Manejador para el botón "Obtener Soporte"
        btnSoporte.setOnClickListener {
            val email = Uri.parse("mailto:8761804@iesplayamar.es")
            val intent = Intent(Intent.ACTION_SENDTO, email)
            startActivity(intent)
        }

        // Manejador para el botón "Volver"
        btnVolver.setOnClickListener {
            finish()
        }

    }
}