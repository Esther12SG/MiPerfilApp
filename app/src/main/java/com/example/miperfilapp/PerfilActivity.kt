package com.example.miperfilapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var imgPerfil: ImageView

    // Abrir la cámara
    private val tomarFoto =
        registerForActivityResult(
            ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->

            if (bitmap != null) {
                imgPerfil.setPadding(0, 0, 0, 0)
                imgPerfil.setImageBitmap(bitmap)
            }
        }

    // Abrir la galería
    private val seleccionarImagen =
        registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->

            if (uri != null) {
                imgPerfil.setPadding(0, 0, 0, 0)
                imgPerfil.setImageURI(uri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        databaseHelper = DatabaseHelper(this)

        imgPerfil = findViewById(R.id.imgPerfil)

        val txtNombre =
            findViewById<TextView>(R.id.txtNombrePerfil)

        val txtCorreo =
            findViewById<TextView>(R.id.txtCorreoPerfil)

        val btnTomarFoto =
            findViewById<Button>(R.id.btnTomarFoto)

        val btnGaleria =
            findViewById<Button>(R.id.btnGaleria)

        val btnCerrarSesion =
            findViewById<Button>(R.id.btnCerrarSesion)

        // Recibimos el correo enviado desde el login
        val correoUsuario =
            intent.getStringExtra("correoUsuario") ?: ""

        // Buscamos el nombre del usuario en SQLite
        val nombreUsuario =
            databaseHelper.obtenerNombre(correoUsuario)

        // Mostramos los datos
        txtNombre.text = nombreUsuario
        txtCorreo.text = correoUsuario

        // Tomar fotografía
        btnTomarFoto.setOnClickListener {
            tomarFoto.launch(null)
        }

        // Elegir imagen de galería
        btnGaleria.setOnClickListener {
            seleccionarImagen.launch("image/*")
        }

        // Cerrar sesión
        btnCerrarSesion.setOnClickListener {

            val intent =
                Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }
    }
}