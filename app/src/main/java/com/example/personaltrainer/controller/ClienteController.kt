package com.example.personaltrainer.controller

import android.content.Context
import com.example.personaltrainer.model.Cliente
import com.example.personaltrainer.model.Entrenador
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException

class ClienteController(private val context: Context) {

    private val filename = "clientes.json"
    private val filenameEntrenadores = "entrenadores.json"

    // Método para registrar un cliente
    fun registrarCliente(cliente: Cliente) {
        if (usuarioYaRegistrado(cliente.usuario)) {
            throw IllegalArgumentException("El usuario ya está registrado.")
        }

        val clientes = cargarClientes() // Cargar clientes existentes

        // Crear un nuevo objeto JSON para el cliente
        val nuevoCliente = JSONObject()
        nuevoCliente.put("nombre", cliente.nombre)
        nuevoCliente.put("telefono", cliente.telefono)
        nuevoCliente.put("altura", cliente.altura)
        nuevoCliente.put("peso", cliente.peso)
        nuevoCliente.put("usuario", cliente.usuario)
        nuevoCliente.put("password", cliente.password)
        nuevoCliente.put("entrenadorId", cliente.id) // Añadir ID del entrenador si aplica

        // Agregar el nuevo cliente a la lista
        clientes.put(nuevoCliente)

        // Guardar la lista actualizada en el archivo
        guardarClientes(clientes)
    }

    // Método para cargar la lista de clientes desde el archivo JSON
    fun cargarClientes(): JSONArray {
        val file = File(context.filesDir, filename)
        if (!file.exists()) {
            return JSONArray() // Retorna un array vacío si no existe el archivo
        }
        return JSONArray(file.readText()) // Leer el archivo y convertirlo en JSONArray
    }

    // Método para guardar la lista de clientes en el archivo JSON
    private fun guardarClientes(clientes: JSONArray) {
        val file = File(context.filesDir, filename)
        try {
            file.writeText(clientes.toString()) // Escribir el JSONArray en el archivo
        } catch (e: IOException) {
            e.printStackTrace() // Manejar el error
        }
    }

    // Método para verificar si el usuario ya está registrado
    private fun usuarioYaRegistrado(usuario: String): Boolean {
        val clientes = cargarClientes()
        for (i in 0 until clientes.length()) {
            val cliente = clientes.getJSONObject(i)
            if (cliente.getString("usuario") == usuario) {
                return true // Retorna verdadero si el usuario ya está registrado
            }
        }
        return false // Retorna falso si el usuario no está registrado
    }

    // Método para iniciar sesión
    fun iniciarSesion(usuario: String, password: String): Boolean {
        val clientes = cargarClientes() // Cargar clientes existentes
        for (i in 0 until clientes.length()) {
            val cliente = clientes.getJSONObject(i)
            if (cliente.getString("usuario") == usuario && cliente.getString("password") == password) {
                return true // Retorna verdadero si el usuario y la contraseña son correctos
            }
        }
        return false // Retorna falso si no se encuentra el usuario
    }

    // Método para asociar un cliente a un entrenador
    fun asociarClienteAEntrenador(clienteId: String, entrenador: Entrenador) {
        val clientes = cargarClientes()
        for (i in 0 until clientes.length()) {
            val cliente = clientes.getJSONObject(i)
            if (cliente.getString("usuario") == clienteId) {
                cliente.put("entrenadorId", entrenador.id) // Asociar el cliente al entrenador
                guardarClientes(clientes)
                return
            }
        }
        throw IllegalArgumentException("Cliente no encontrado.")
    }

    // Método para actualizar el cliente
    fun actualizarCliente(clienteActualizado: Cliente) {
        val clientes = cargarClientes()
        for (i in 0 until clientes.length()) {
            val cliente = clientes.getJSONObject(i)
            if (cliente.getInt("id") == clienteActualizado.id) {
                // Actualizar los datos del cliente
                cliente.put("nombre", clienteActualizado.nombre)
                cliente.put("telefono", clienteActualizado.telefono)
                cliente.put("altura", clienteActualizado.altura)
                cliente.put("peso", clienteActualizado.peso)
                cliente.put("usuario", clienteActualizado.usuario)
                cliente.put("password", clienteActualizado.password)
                guardarClientes(clientes) // Guardar la lista actualizada en el archivo
                return
            }
        }
        throw IllegalArgumentException("Cliente no encontrado.")
    }
}
