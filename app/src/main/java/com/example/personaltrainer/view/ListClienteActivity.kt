package com.example.personaltrainer.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.controller.ClienteController
import org.json.JSONArray
import org.json.JSONObject

class ListClienteActivity : AppCompatActivity() {

    private lateinit var edtTelefono: EditText
    private lateinit var btnAgregarCliente: Button
    private lateinit var linearLayoutClientes: LinearLayout
    private lateinit var clienteController: ClienteController

    // Conjunto para almacenar los teléfonos de clientes agregados
    private val telefonosAgregados = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_cliente)

        // Inicializa el controlador
        clienteController = ClienteController(this)

        // Inicializa las vistas
        edtTelefono = findViewById(R.id.edtTelefono)
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente)
        linearLayoutClientes = findViewById(R.id.linearLayoutClientes)

        // Configura el botón para agregar cliente
        btnAgregarCliente.setOnClickListener {
            agregarCliente()
        }
    }

    private fun agregarCliente() {
        val telefono = edtTelefono.text.toString()

        if (telefono.isNotBlank()) {
            // Verificar si el cliente ya ha sido agregado
            if (telefonosAgregados.contains(telefono)) {
                mostrarMensaje("Este cliente ya ha sido agregado.")
                return
            }

            // Cargar clientes existentes
            val clientes = clienteController.cargarClientes()
            var clienteEncontrado = false

            // Buscar si el cliente ya está registrado
            for (i in 0 until clientes.length()) {
                val cliente = clientes.getJSONObject(i)
                if (cliente.getString("telefono") == telefono) {
                    clienteEncontrado = true
                    // Crear un botón para el cliente con todos sus datos
                    crearBotonCliente(cliente)
                    // Agregar el teléfono al conjunto de agregados
                    telefonosAgregados.add(telefono)
                    break
                }
            }

            if (!clienteEncontrado) {
                mostrarMensaje("No se encontró un cliente con ese número de teléfono.")
            }

            // Limpiar el campo de entrada
            edtTelefono.text.clear()
        } else {
            mostrarMensaje("Por favor, ingresa un número de teléfono.")
        }
    }

    private fun crearBotonCliente(cliente: JSONObject) {
        // Extraer datos del cliente
        val nombre = cliente.getString("nombre")
        val telefono = cliente.getString("telefono")
        val peso = cliente.getString("peso") // Asegúrate de que estos campos existen en tu JSON
        val altura = cliente.getString("altura")

        // Crear un botón dinámicamente
        val botonCliente = Button(this).apply {
            text = "Nombre: $nombre\nTeléfono: $telefono\nPeso: $peso kg\nAltura: $altura cm"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                // Crear un Intent para navegar a AsignarRutinaActivity
                val intent = Intent(this@ListClienteActivity, AsignarRutinaActivity::class.java)
                // Pasar el número de teléfono al Intent
                intent.putExtra("telefonoCliente", telefono)
                startActivity(intent)
            }
        }

        // Agregar el botón al layout
        linearLayoutClientes.addView(botonCliente)
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
