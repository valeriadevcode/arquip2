package com.example.personaltrainer.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.model.Ejercicio
import com.example.personaltrainer.model.Rutina
import android.widget.Toast
import com.bumptech.glide.Glide

class AsignarRutinaActivity : AppCompatActivity() {

    private val ejercicios = mutableListOf<Ejercicio>() // Lista de ejercicios
    private lateinit var telefonoCliente: String // Variable para almacenar el número de teléfono del cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_rutina)

        // Obtener el número de teléfono del Intent
        telefonoCliente = intent.getStringExtra("telefonoCliente") ?: ""

        // Inicializa los ejercicios
        cargarEjercicios()

        // Cargar los GIFs en las tarjetas
        mostrarEjercicios()

        // Configurar el botón de enviar
        configurarBotonEnviar()
    }

    private fun cargarEjercicios() {
        // Agrega ejercicios a la lista con sus GIFs (los GIFs deben estar en res/drawable)
        ejercicios.add(Ejercicio("Ejercicio 1", "gif_ejercicio1"))
        ejercicios.add(Ejercicio("Ejercicio 2", "gif_ejercicio2"))
//        ejercicios.add(Ejercicio("Ejercicio 3", "gif_ejercicio3"))
//        ejercicios.add(Ejercicio("Ejercicio 4", "gif_ejercicio4"))
//        ejercicios.add(Ejercicio("Ejercicio 5", "gif_ejercicio5"))
    }

    private fun mostrarEjercicios() {
        val ejercicios = listOf(
            Pair(R.id.ivEjercicio1, "gif_ejercicio1"),
            Pair(R.id.ivEjercicio2, "gif_ejercicio2"),
            // Añadir más ejercicios aquí
        )

        ejercicios.forEach { (imageViewId, gifName) ->
            val gifId = resources.getIdentifier(gifName, "drawable", packageName)

            // Verificar si el recurso fue encontrado
            if (gifId != 0) {
                val imageView = findViewById<ImageView>(imageViewId)
                if (imageView != null) {
                    // Usa Glide para cargar el GIF
                    Glide.with(this)
                        .asGif() // Indicar que es un GIF animado
                        .load(gifId) // Cargar el GIF desde drawable
                        .into(imageView) // Cargar el GIF en el ImageView
                } else {
                    Log.e("AsignarRutinaActivity", "ImageView con ID $imageViewId no encontrado")
                }
            } else {
                Log.e("AsignarRutinaActivity", "GIF con nombre $gifName no encontrado")
            }
        }
    }


    private fun configurarBotonEnviar() {
        val botonEnviar = findViewById<Button>(R.id.btnEnviarRutina)

        botonEnviar.setOnClickListener {
            enviarRutina()
        }
    }

    private fun enviarRutina() {
        val rutinasAsignadas = mutableListOf<Rutina>()

        for (i in 1..ejercicios.size) { // Ajusta según la cantidad de ejercicios
            val serie = findViewById<EditText>(resources.getIdentifier("etSerie$i", "id", packageName)).text.toString()
            val repeticion = findViewById<EditText>(resources.getIdentifier("etRepeticion$i", "id", packageName)).text.toString()

            // Verificar que serie y repetición no estén vacíos
            if (serie.isNotEmpty() && repeticion.isNotEmpty()) {
                val rutina = Rutina(repeticion.toInt(), serie.toInt()) // Crear nueva instancia de Rutina
                rutinasAsignadas.add(rutina) // Agregar a la lista de rutinas asignadas
            }
        }

        // Aquí procesamos la rutina para enviar
        if (rutinasAsignadas.isNotEmpty()) {
            val mensaje = StringBuilder()
            mensaje.append("Rutina asignada:\n")
            for (i in rutinasAsignadas.indices) {
                mensaje.append("Ejercicio ${i + 1}: ${rutinasAsignadas[i].series} series, ${rutinasAsignadas[i].repeticiones} repeticiones\n")
            }

            // Crear el Intent para WhatsApp
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$telefonoCliente&text=${Uri.encode(mensaje.toString())}")
            startActivity(intent)
        } else {
            // Mostrar un mensaje indicando que no se han ingresado rutinas
            Toast.makeText(this, "No hay rutinas para enviar", Toast.LENGTH_SHORT).show()
        }
    }
}
