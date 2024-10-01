package com.example.personaltrainer.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.controller.ClienteController

class LoginClienteActivity : AppCompatActivity() {

    private lateinit var usuarioInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var btnIniciarSesion: Button
    private lateinit var clienteController: ClienteController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_cliente)

        clienteController = ClienteController(this)

        usuarioInput = findViewById(R.id.edtUsuarioLogin)
        passwordInput = findViewById(R.id.edtPasswordLogin)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        btnIniciarSesion.setOnClickListener {
            val usuario = usuarioInput.text.toString()
            val password = passwordInput.text.toString()

            if (clienteController.iniciarSesion(usuario, password)) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                // Navegar a la actividad de clientes
                startActivity(Intent(this, DatosClienteActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
