package com.example.personaltrainer.model

class Cliente(
    val altura: Double,
    val peso: Double,
    id: Int,                // Pasar el ID a la clase padre
    nombre: String,
    telefono: String,
    usuario: String,
    password: String
) : Persona(id, nombre, telefono, usuario, password) {

    private var rutina: Rutina? = null

    fun asignarRutina(rutina: Rutina) {
        this.rutina = rutina
    }

    fun obtenerRutina(): Rutina? {
        return rutina
    }
}
