package com.example.miperfilapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        val edtCorreo = findViewById<EditText>(R.id.edtCorreoLogin)
        val edtPassword = findViewById<EditText>(R.id.edtPasswordLogin)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnIrRegistro = findViewById<Button>(R.id.btnIrRegistro)

        btnLogin.setOnClickListener {

            val correo = edtCorreo.text.toString().trim()
            val password = edtPassword.text.toString()

            if (correo.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val loginCorrecto =
                databaseHelper.validarLogin(correo, password)

            if (loginCorrecto) {

                val intent =
                    Intent(this, PerfilActivity::class.java)

                intent.putExtra("correoUsuario", correo)

                startActivity(intent)
                finish()

            } else {

                Toast.makeText(
                    this,
                    "Correo o contraseña incorrectos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnIrRegistro.setOnClickListener {

            val intent =
                Intent(this, RegistroActivity::class.java)

            startActivity(intent)
        }
    }
}