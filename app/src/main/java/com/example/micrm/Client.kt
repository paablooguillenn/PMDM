package com.example.micrm

/**
 * Data class que representa el modelo de un Cliente.
 *
 * @property id El identificador único del cliente en la base de datos.
 * @property name El nombre completo del cliente.
 * @property email La dirección de correo electrónico del cliente.
 * @property phone El número de teléfono del cliente.
 */
data class Client(
    val id: Int = -1, // Un ID por defecto para clientes nuevos que aún no están en la DB.
    val name: String,
    val email: String,
    val phone: String
)
