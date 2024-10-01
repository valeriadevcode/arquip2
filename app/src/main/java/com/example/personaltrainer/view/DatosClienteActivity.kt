package com.example.personaltrainer.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.controller.ClienteController
import com.example.personaltrainer.model.Cliente

class DatosClienteActivity : AppCompatActivity() {

    private lateinit var nombreInput: EditText
    private lateinit var telefonoInput: EditText
    private lateinit var alturaInput: EditText
    private lateinit var pesoInput: EditText
    private lateinit var usuarioInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var btnActualizar: Button

    private lateinit var cliente: Cliente // Supón que ya tienes el cliente cargado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_cliente)

        // Inicializa las vistas
        nombreInput = findViewById(R.id.nombreInput) // debe coincidir con @+id/nombreInput en XML
        telefonoInput = findViewById(R.id.telefonoInput) // @+id/telefonoInput
        alturaInput = findViewById(R.id.alturaInput) // @+id/alturaInput
        pesoInput = findViewById(R.id.pesoInput) // @+id/pesoInput
        usuarioInput = findViewById(R.id.usuarioInput) // @+id/usuarioInput
        passwordInput = findViewById(R.id.passwordInput) // @+id/passwordInput
        btnActualizar = findViewById(R.id.btnActualizar) // @+id/btnActualizar


        // Muestra los datos del cliente en los EditTexts
        mostrarDatosCliente()

        btnActualizar.setOnClickListener {
            actualizarCliente()
        }
    }

    private fun mostrarDatosCliente() {
        // Asigna los datos del cliente a los EditTexts
        nombreInput.setText(cliente.nombre)
        telefonoInput.setText(cliente.telefono)
        alturaInput.setText(cliente.altura.toString())
        pesoInput.setText(cliente.peso.toString())
        usuarioInput.setText(cliente.usuario)
        passwordInput.setText(cliente.password)
    }

    private fun actualizarCliente() {
        // Obtiene los datos actualizados
        val nombre = nombreInput.text.toString()
        val telefono = telefonoInput.text.toString()
        val altura = alturaInput.text.toString().toDoubleOrNull() ?: 0.0
        val peso = pesoInput.text.toString().toDoubleOrNull() ?: 0.0
        val usuario = usuarioInput.text.toString()
        val password = passwordInput.text.toString()

        // Validaciones simples
        if (nombre.isEmpty() || telefono.isEmpty() || usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        // Crea el objeto Cliente actualizado
        val clienteActualizado = Cliente(
            altura = altura,
            peso = peso,
            id = cliente.id, // Mantiene el mismo ID
            nombre = nombre,
            telefono = telefono,
            usuario = usuario,
            password = password
        )

        // Llama al controlador para guardar los cambios
        val clienteController = ClienteController(this) // Asegúrate de pasar el contexto
        clienteController.actualizarCliente(clienteActualizado)

        Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
        // Puedes redirigir a otra actividad o realizar otras acciones aquí
    }
}
