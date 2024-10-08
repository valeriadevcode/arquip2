package com.example.personaltrainer.controller.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.personaltrainer.R
import com.example.personaltrainer.controller.EjercicioController
import com.example.personaltrainer.controller.RutinaController
import com.example.personaltrainer.model.Ejercicio
import com.example.personaltrainer.model.Rutina
import android.widget.Toast

class AsignarRutinaActivity : AppCompatActivity() {

    private lateinit var ejercicioController: EjercicioController
    private lateinit var rutinaController: RutinaController
    private lateinit var telefonoCliente: String // Variable para almacenar el número de teléfono del cliente
    private var ejercicios = mutableListOf<Ejercicio>() // Lista de ejercicios

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_asignar_rutina)

        // Inicializar controladores
        ejercicioController = EjercicioController(this)
        rutinaController = RutinaController(this)

        // Obtener el número de teléfono del Intent
        telefonoCliente = intent.getStringExtra("telefonoCliente") ?: ""

        // Cargar los ejercicios desde EjercicioController
        ejercicios = ejercicioController.obtenerEjercicios().toMutableList()

        // Mostrar los ejercicios en la UI
        mostrarEjercicios()

        // Configurar el botón de enviar
        configurarBotonEnviar()
    }

    private fun mostrarEjercicios() {
        // Mostrar los ejercicios en la interfaz
        for (i in 1..ejercicios.size) {
            val ejercicio = ejercicios[i - 1]
            val gifId = resources.getIdentifier(ejercicio.multimedia, "drawable", packageName)

            // Cargar el GIF correspondiente en la ImageView
            val imageView = findViewById<ImageView>(resources.getIdentifier("ivEjercicio$i", "id", packageName))
            imageView.setImageResource(gifId)
        }
    }

    private fun configurarBotonEnviar() {
        val botonEnviar = findViewById<Button>(R.id.botonEnviar)

        botonEnviar.setOnClickListener {
            enviarRutina()
        }
    }

    private fun enviarRutina() {
        val rutinasAsignadas = mutableListOf<Rutina>()

        for (i in 1..ejercicios.size) {
            val serie = findViewById<EditText>(resources.getIdentifier("etSerie$i", "id", packageName)).text.toString()
            val repeticion = findViewById<EditText>(resources.getIdentifier("etRepeticion$i", "id", packageName)).text.toString()

            // Verificar que serie y repetición no estén vacíos
            if (serie.isNotEmpty() && repeticion.isNotEmpty()) {
                val rutina = Rutina(repeticion.toInt(), serie.toInt())
                rutinasAsignadas.add(rutina)
            }
        }

        if (rutinasAsignadas.isNotEmpty()) {
            // Guardar rutinas asignadas usando RutinaController
            for (rutina in rutinasAsignadas) {
                rutinaController.agregarRutina(rutina)
            }

            // Crear el mensaje para enviar por WhatsApp
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
            Toast.makeText(this, "No hay rutinas para enviar", Toast.LENGTH_SHORT).show()
        }
    }
}
