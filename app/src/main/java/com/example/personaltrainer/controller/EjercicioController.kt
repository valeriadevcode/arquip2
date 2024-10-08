package com.example.personaltrainer.controller

import android.content.Context
import com.example.personaltrainer.model.Ejercicio
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class EjercicioController(private val context: Context) {

    private val filename = "ejercicios.json"

    // Método para agregar un nuevo ejercicio
    fun agregarEjercicio(ejercicio: Ejercicio) {
        val ejercicios = cargarEjercicios()

        // Crear un objeto JSON para el nuevo ejercicio
        val ejercicioJson = JSONObject().apply {
            put("nombre", ejercicio.nombre)
            put("multimedia", ejercicio.multimedia)
        }

        // Añadir el nuevo ejercicio al array
        ejercicios.put(ejercicioJson)

        // Guardar la lista actualizada en el archivo
        guardarEjercicios(ejercicios)
    }

    // Método para obtener la lista de ejercicios
    fun obtenerEjercicios(): List<Ejercicio> {
        val ejerciciosJson = cargarEjercicios()
        val listaEjercicios = mutableListOf<Ejercicio>()

        for (i in 0 until ejerciciosJson.length()) {
            val ejercicioJson = ejerciciosJson.getJSONObject(i)
            val nombre = ejercicioJson.getString("nombre")
            val multimedia = ejercicioJson.getString("multimedia")

            // Crear un objeto Ejercicio y agregarlo a la lista
            val ejercicio = Ejercicio(nombre, multimedia)
            listaEjercicios.add(ejercicio)
        }

        return listaEjercicios
    }

    // Método para cargar la lista de ejercicios desde el archivo JSON
    private fun cargarEjercicios(): JSONArray {
        val file = File(context.filesDir, filename)
        if (!file.exists()) {
            return JSONArray() // Retorna un array vacío si no existe el archivo
        }
        return JSONArray(file.readText()) // Leer el archivo y convertirlo en JSONArray
    }

    // Método para guardar la lista de ejercicios en el archivo JSON
    private fun guardarEjercicios(ejercicios: JSONArray) {
        val file = File(context.filesDir, filename)
        file.writeText(ejercicios.toString()) // Escribir el JSONArray en el archivo
    }
}
