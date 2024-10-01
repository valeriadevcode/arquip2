package com.example.personaltrainer.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.controller.EntrenadorController
import com.example.personaltrainer.model.Entrenador

class RegistroEntrenadorActivity : AppCompatActivity() {

    private lateinit var nombreInput: EditText
    private lateinit var telefonoInput: EditText
    private lateinit var usuarioInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var btnRegistrar: Button

    private lateinit var entrenadorController: EntrenadorController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_entrenador)

        entrenadorController = EntrenadorController(this)

        // Inicializa las vistas
        nombreInput = findViewById(R.id.edtNombreEntrenador)
        telefonoInput = findViewById(R.id.edtTelefonoEntrenador)
        usuarioInput = findViewById(R.id.edtUsuarioEntrenador)
        passwordInput = findViewById(R.id.edtPasswordEntrenador)
        btnRegistrar = findViewById(R.id.btnRegistrarEntrenador)

        // Configura el botón de registrar
        btnRegistrar.setOnClickListener {
            registrarEntrenador()
        }
    }

    private fun registrarEntrenador() {
        val nombre = nombreInput.text.toString()
        val telefono = telefonoInput.text.toString()
        val usuario = usuarioInput.text.toString()
        val password = passwordInput.text.toString()

        // Validaciones simples
        if (nombre.isEmpty() || telefono.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        // Generar un ID para el entrenador (puedes cambiar la forma de generar el ID si es necesario)
        val entrenadorId = generarIdUnico()

        // Crear el objeto Entrenador con el ID generado
        val nuevoEntrenador = Entrenador(
            id = entrenadorId,  // Generar o asignar un valor para el id
            nombre = nombre,
            telefono = telefono,
            usuario = usuario,
            password = password
        )

        // Registrar el entrenador (esto llamaría al método del controlador o base de datos)
        entrenadorController.registrarEntrenador(nuevoEntrenador)

        // Mostrar un mensaje de éxito
        Toast.makeText(this, "Entrenador registrado exitosamente", Toast.LENGTH_SHORT).show()

        // Limpiar los campos
        limpiarCampos()
    }

    // Genera un ID único para el entrenador (esto es solo un ejemplo)
    private fun generarIdUnico(): Int {
        return (Math.random() * 10000).toInt() // Genera un ID aleatorio
    }

    // Limpia los campos del formulario
    private fun limpiarCampos() {
        nombreInput.text.clear()
        telefonoInput.text.clear()
        usuarioInput.text.clear()
        passwordInput.text.clear()
    }

}
