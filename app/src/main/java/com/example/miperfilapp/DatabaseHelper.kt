package com.example.miperfilapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "MiPerfilDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val crearTabla = """
            CREATE TABLE usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                correo TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(crearTabla)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }

    fun registrarUsuario(
        nombre: String,
        correo: String,
        password: String
    ): Boolean {

        val db = writableDatabase

        val valores = ContentValues().apply {
            put("nombre", nombre)
            put("correo", correo)
            put("password", password)
        }

        val resultado = db.insert(
            "usuarios",
            null,
            valores
        )

        db.close()

        return resultado != -1L
    }

    fun existeCorreo(correo: String): Boolean {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT id FROM usuarios WHERE correo = ?",
            arrayOf(correo)
        )

        val existe = cursor.moveToFirst()

        cursor.close()
        db.close()

        return existe
    }

    fun validarLogin(
        correo: String,
        password: String
    ): Boolean {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT id FROM usuarios WHERE correo = ? AND password = ?",
            arrayOf(correo, password)
        )

        val valido = cursor.moveToFirst()

        cursor.close()
        db.close()

        return valido
    }

    fun obtenerNombre(correo: String): String {

        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT nombre FROM usuarios WHERE correo = ?",
            arrayOf(correo)
        )

        var nombre = ""

        if (cursor.moveToFirst()) {
            nombre = cursor.getString(
                cursor.getColumnIndexOrThrow("nombre")
            )
        }

        cursor.close()
        db.close()

        return nombre
    }
}