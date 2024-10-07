package com.example.personaltrainer.model

class Cliente(
    val altura: Double,
    val peso: Double,
    id: Int,
    nombre: String,
    telefono: String,
    usuario: String,
    password: String
) : Persona(id, nombre, telefono, usuario, password) {

    private var rutina: Rutina? = null

    // Método para asignar una rutina al cliente
    fun asignarRutina(rutina: Rutina) {
        this.rutina = rutina
    }

    // Método para obtener la rutina del cliente
    fun obtenerRutina(): Rutina? {
        return rutina
    }
}
