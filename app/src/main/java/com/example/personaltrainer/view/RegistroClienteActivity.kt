package com.example.personaltrainer.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.controller.ClienteController
import com.example.personaltrainer.model.Cliente

class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var nombreCliente: EditText
    private lateinit var telefonoCliente: EditText
    private lateinit var alturaCliente: EditText
    private lateinit var pesoCliente: EditText
    private lateinit var usuarioCliente: EditText
    private lateinit var passwordCliente: EditText
    private lateinit var btnRegistrar: Button

    private lateinit var clienteController: ClienteController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_cliente)

        nombreCliente = findViewById(R.id.nombreCliente)
        telefonoCliente = findViewById(R.id.telefonoCliente)
        alturaCliente = findViewById(R.id.alturaCliente)
        pesoCliente = findViewById(R.id.pesoCliente)
        usuarioCliente = findViewById(R.id.usuarioCliente)
        passwordCliente = findViewById(R.id.passwordCliente)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        // Inicializar el controlador
        clienteController = ClienteController(this)

        btnRegistrar.setOnClickListener {
            registrarCliente()
        }
    }

    private fun registrarCliente() {
        val nombre = nombreCliente.text.toString().trim()
        val telefono = telefonoCliente.text.toString().trim()
        val altura = alturaCliente.text.toString().trim()
        val peso = pesoCliente.text.toString().trim()
        val usuario = usuarioCliente.text.toString().trim()
        val password = passwordCliente.text.toString().trim()

        // Validar los campos
        if (nombre.isEmpty() || telefono.isEmpty() || altura.isEmpty() || peso.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            // Crear un objeto Cliente
            val cliente = Cliente(
                id = 0, // ID se puede generar automáticamente más tarde
                nombre = nombre,
                telefono = telefono,
                altura = altura.toDouble(), // Convertir altura a Double
                peso = peso.toDouble(),     // Convertir peso a Double
                usuario = usuario,
                password = password
            )

            // Registrar el cliente usando el controlador
            clienteController.registrarCliente(cliente)
            Toast.makeText(this, "Cliente registrado exitosamente.", Toast.LENGTH_SHORT).show()
            limpiarCampos()

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Por favor, ingresa valores numéricos válidos para altura y peso.", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalArgumentException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCampos() {
        nombreCliente.text.clear()
        telefonoCliente.text.clear()
        alturaCliente.text.clear()
        pesoCliente.text.clear()
        usuarioCliente.text.clear()
        passwordCliente.text.clear()
    }
}
