package com.example.personaltrainer.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R

class MainActivity : AppCompatActivity() {

    private lateinit var radioGroup: RadioGroup
    private lateinit var btnIniciarSesion: Button
    private lateinit var btnRegistrar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioGroup = findViewById(R.id.radioGroup)
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        btnIniciarSesion.setOnClickListener {
            iniciarSesion()
        }

        btnRegistrar.setOnClickListener {
            registrar()
        }
    }

    private fun iniciarSesion() {
        val tipoUsuario = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)

        when (tipoUsuario.text) {
            "Cliente" -> {
                startActivity(Intent(this, LoginClienteActivity::class.java))
            }
            "Entrenador" -> {
                startActivity(Intent(this, LoginEntrenadorActivity::class.java))
            }
        }
    }

    private fun registrar() {
        val tipoUsuario = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)

        when (tipoUsuario.text) {
            "Cliente" -> {
                startActivity(Intent(this, RegistroClienteActivity::class.java))
            }
            "Entrenador" -> {
                startActivity(Intent(this, RegistroEntrenadorActivity::class.java))
            }
        }
    }
}
