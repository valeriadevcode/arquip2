package com.example.personaltrainer.model

class Envio(
    val telefono: String,
    val rutina: Rutina,
    val ejercicio: Ejercicio
) {
    override fun toString(): String {
        return "Envio(telefono='$telefono', rutina=$rutina, ejercicio=$ejercicio)"
    }
}