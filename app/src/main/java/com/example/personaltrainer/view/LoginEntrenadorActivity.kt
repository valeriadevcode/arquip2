package com.example.personaltrainer.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.controller.EntrenadorController

class LoginEntrenadorActivity : AppCompatActivity() {

    private lateinit var usuarioInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var entrenadorController: EntrenadorController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_entrenador)

        entrenadorController = EntrenadorController(this)

        usuarioInput = findViewById(R.id.edtUsuarioLoginEntrenador)
        passwordInput = findViewById(R.id.edtPasswordLoginEntrenador)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesionEntrenador)

        btnIniciarSesion.setOnClickListener {
            val usuario = usuarioInput.text.toString()
            val password = passwordInput.text.toString()

            if (entrenadorController.iniciarSesion(usuario, password)) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                // Navegar a la actividad de clientes
                startActivity(Intent(this, ListClienteActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
