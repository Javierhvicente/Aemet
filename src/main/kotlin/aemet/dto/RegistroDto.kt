package org.example.aemet.dto

import java.time.LocalTime

data class RegistroDto(
    val localidad: String,
    val provincia: String,
    val tempMax: String,
    val horaTempMax: String,
    val tempMin: String,
    val horaTempMin: String,
    val precipitacion: String,
) {
}