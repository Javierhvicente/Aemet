package org.example.aemet.mappers

import org.example.aemet.dto.RegistroDto
import org.example.aemet.models.Registro
import java.time.LocalTime
import java.util.*

fun RegistroDto.toRegistro(): Registro{
    return Registro(
        id = UUID.randomUUID(),
        localidad = this.localidad,
        provincia = this.provincia,
        tempMax = this.tempMax.toDouble(),
        horaTempMax = LocalTime.parse(this.horaTempMax) ,
        tempMin = this.tempMin.toDouble(),
        horaTempMin = LocalTime.parse(this.horaTempMin),
        precipitacion = this.precipitacion.toDouble()
    )
}

fun Registro.toRegistroDto(): RegistroDto {
    return RegistroDto(
        localidad = this.localidad,
        provincia = this.provincia,
        tempMax = this.tempMax.toString(),
        horaTempMax = this.horaTempMax.toString(),
        tempMin = this.tempMin.toString(),
        horaTempMin = this.horaTempMin.toString(),
        precipitacion = this.precipitacion.toString()
    )
}