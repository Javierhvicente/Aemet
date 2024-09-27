package org.example.aemet.repository

import org.example.aemet.models.Registro
import java.util.UUID

interface RegistrosRepository {
    fun getAllRegistros(): List<Registro>
    fun getRegistroById(id: UUID): Registro?
    fun getRegistrosByLocalidad(localidad: String): List<Registro?>
    fun getRegistrosByProvincia(provincia: String): List<Registro?>
    fun addRegistro(registro: Registro): Registro
    fun updateRegistro(id: UUID, registro: Registro): Registro?
    fun deleteRegistroById(id: UUID): Registro?
}