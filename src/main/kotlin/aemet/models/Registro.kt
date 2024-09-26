package org.example.aemet.models

import java.time.LocalTime
import java.util.*

data class Registro(
    val id: UUID,
    val localidad: String,
    val provincia: String,
    val tempMax: Double,
    val horaTempMax: LocalTime,
    val tempMin: Double,
    val horaTempMin: LocalTime,
    val precipitacion: Double,
) {
}