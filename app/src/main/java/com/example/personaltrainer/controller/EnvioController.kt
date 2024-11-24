package com.example.personaltrainer.controller

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.personaltrainer.model.Envio
import com.example.personaltrainer.model.Ejercicio
import com.example.personaltrainer.model.Rutina

class EnvioController(private val context: Context) {

    fun enviarRutina(envio: Envio) {
        val mensaje = StringBuilder()
        mensaje.append("Rutina asignada para el ejercicio ${envio.ejercicio.nombre}:\n")
        mensaje.append("${envio.rutina.series} series, ${envio.rutina.repeticiones} repeticiones\n")

        // Crear el Intent para WhatsApp
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=${envio.telefono}&text=${Uri.encode(mensaje.toString())}")

        // Iniciar la actividad de WhatsApp o mostrar un mensaje de error
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show()
        }
    }
}
