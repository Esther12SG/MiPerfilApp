package com.example.miperfilapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        databaseHelper = DatabaseHelper(this)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtCorreo = findViewById<EditText>(R.id.edtCorreo)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val edtConfirmarPassword =
            findViewById<EditText>(R.id.edtConfirmarPassword)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        val btnVolverLogin = findViewById<Button>(R.id.btnVolverLogin)

        btnRegistrar.setOnClickListener {

            val nombre = edtNombre.text.toString().trim()
            val correo = edtCorreo.text.toString().trim()
            val password = edtPassword.text.toString()
            val confirmarPassword =
                edtConfirmarPassword.text.toString()

            if (
                nombre.isEmpty() ||
                correo.isEmpty() ||
                password.isEmpty() ||
                confirmarPassword.isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "Completa todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (password != confirmarPassword) {
                Toast.makeText(
                    this,
                    "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (databaseHelper.existeCorreo(correo)) {
                Toast.makeText(
                    this,
                    "Este correo ya está registrado",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val registrado =
                databaseHelper.registrarUsuario(
                    nombre,
                    correo,
                    password
                )

            if (registrado) {

                Toast.makeText(
                    this,
                    "Usuario registrado correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                val intent =
                    Intent(this, MainActivity::class.java)

                startActivity(intent)
                finish()

            } else {

                Toast.makeText(
                    this,
                    "No se pudo registrar el usuario",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnVolverLogin.setOnClickListener {

            val intent =
                Intent(this, MainActivity::class.java)

            startActivity(intent)
            finish()
        }
    }
}