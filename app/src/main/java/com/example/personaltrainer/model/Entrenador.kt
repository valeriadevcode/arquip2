package com.example.personaltrainer.model

class Entrenador(
    id: Int,                 // Acepta un id como parámetro
    nombre: String,
    telefono: String,
    usuario: String,
    password: String
) : Persona(id, nombre, telefono, usuario, password) {  // Pasa el id al constructor de Persona

    private val clientes = mutableListOf<Cliente>()

    fun agregarCliente(cliente: Cliente) {
        clientes.add(cliente)
    }

    fun obtenerClientes(): List<Cliente> {
        return clientes
    }

    override fun toString(): String {
        return "Entrenador(id=$id, nombre='$nombre', telefono='$telefono')"
    }
}
