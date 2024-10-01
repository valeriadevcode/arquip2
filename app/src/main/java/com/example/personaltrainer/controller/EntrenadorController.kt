package com.example.personaltrainer.controller

import android.content.Context
import com.example.personaltrainer.model.Entrenador
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class EntrenadorController(private val context: Context) {

    private val filename = "entrenadores.json"
    private val filenameClientes = "clientes.json" // Archivo para clientes

    // Método para iniciar sesión
    fun iniciarSesion(usuario: String, password: String): Boolean {
        val entrenadores = cargarEntrenadores() // Cargar entrenadores existentes

        for (i in 0 until entrenadores.length()) {
            val entrenador = entrenadores.getJSONObject(i)
            if (entrenador.getString("usuario") == usuario && entrenador.getString("password") == password) {
                return true // Inicio de sesión exitoso
            }
        }
        return false // Usuario o contraseña incorrectos
    }

    // Método para cargar la lista de entrenadores desde el archivo JSON
    private fun cargarEntrenadores(): JSONArray {
        val file = File(context.filesDir, filename)
        if (!file.exists()) {
            return JSONArray() // Retorna un array vacío si no existe el archivo
        }
        return JSONArray(file.readText()) // Leer el archivo y convertirlo en JSONArray
    }

    // Método para registrar un nuevo entrenador
    fun registrarEntrenador(entrenador: Entrenador) {
        val file = File(context.filesDir, filename)
        val entrenadoresJsonArray = if (file.exists()) {
            JSONArray(file.readText())
        } else {
            JSONArray() // Crea un nuevo array si el archivo no existe
        }

        // Crear un objeto JSON para el nuevo entrenador
        val entrenadorJson = JSONObject().apply {
            put("nombre", entrenador.nombre)
            put("telefono", entrenador.telefono)
            put("usuario", entrenador.usuario)
            put("password", entrenador.password)
            put("id", entrenadoresJsonArray.length() + 1) // Asignar un ID único
        }

        // Añadir el nuevo entrenador al array
        entrenadoresJsonArray.put(entrenadorJson)

        // Guardar el array JSON de vuelta en el archivo
        file.writeText(entrenadoresJsonArray.toString())
    }

    // Método para asociar un cliente a un entrenador
    fun asociarClienteAEntrenador(clienteId: String, entrenadorId: String) {
        val clientes = cargarClientes() // Cargar clientes existentes
        var clienteEncontrado = false

        for (i in 0 until clientes.length()) {
            val cliente = clientes.getJSONObject(i)
            if (cliente.getString("usuario") == clienteId) {
                cliente.put("entrenadorId", entrenadorId) // Asociar el cliente al entrenador
                clienteEncontrado = true
                break
            }
        }

        if (clienteEncontrado) {
            guardarClientes(clientes) // Guardar los cambios en el archivo
        } else {
            throw IllegalArgumentException("Cliente no encontrado.")
        }
    }

    // Método para cargar la lista de clientes desde el archivo JSON
    private fun cargarClientes(): JSONArray {
        val file = File(context.filesDir, filenameClientes)
        if (!file.exists()) {
            return JSONArray() // Retorna un array vacío si no existe el archivo
        }
        return JSONArray(file.readText()) // Leer el archivo y convertirlo en JSONArray
    }

    // Método para guardar la lista de clientes en el archivo JSON
    private fun guardarClientes(clientes: JSONArray) {
        val file = File(context.filesDir, filenameClientes)
        file.writeText(clientes.toString()) // Escribir el JSONArray en el archivo
    }

    // Otros métodos adicionales pueden ser añadidos aquí
}
