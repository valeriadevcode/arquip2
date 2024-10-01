package com.example.personaltrainer.controller

import android.content.Context
import com.example.personaltrainer.model.Rutina
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class RutinaController(private val context: Context) {

    private val filename = "rutinas.json"

    // Método para agregar una nueva rutina
    fun agregarRutina(rutina: Rutina) {
        val rutinas = cargarRutinas() // Cargar rutinas existentes

        // Crear un objeto JSON para la nueva rutina
        val rutinaJson = JSONObject().apply {
            put("repeticiones", rutina.repeticiones)
            put("series", rutina.series)
        }

        // Añadir la nueva rutina al array
        rutinas.put(rutinaJson)

        // Guardar la lista actualizada en el archivo
        guardarRutinas(rutinas)
    }

    // Método para obtener la lista de rutinas
    fun obtenerRutinas(): List<Rutina> {
        val rutinasJson = cargarRutinas()
        val listaRutinas = mutableListOf<Rutina>()

        for (i in 0 until rutinasJson.length()) {
            val rutinaJson = rutinasJson.getJSONObject(i)
            val repeticiones = rutinaJson.getInt("repeticiones")
            val series = rutinaJson.getInt("series")

            // Crear un objeto Rutina y agregarlo a la lista
            val rutina = Rutina(repeticiones, series)
            listaRutinas.add(rutina)
        }

        return listaRutinas
    }

    // Método para cargar la lista de rutinas desde el archivo JSON
    private fun cargarRutinas(): JSONArray {
        val file = File(context.filesDir, filename)
        if (!file.exists()) {
            return JSONArray() // Retorna un array vacío si no existe el archivo
        }
        return JSONArray(file.readText()) // Leer el archivo y convertirlo en JSONArray
    }

    // Método para guardar la lista de rutinas en el archivo JSON
    private fun guardarRutinas(rutinas: JSONArray) {
        val file = File(context.filesDir, filename)
        file.writeText(rutinas.toString()) // Escribir el JSONArray en el archivo
    }
}
