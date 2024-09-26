package org.example.aemet.dto

import java.time.LocalTime
import java.util.UUID

data class RegistroDto(
    val id: UUID = UUID.randomUUID(),
    val localidad: String,
    val provincia: String,
    val tempMax: String,
    val horaTempMax: String,
    val tempMin: String,
    val horaTempMin: String,
    val precipitacion: String,
) {
}