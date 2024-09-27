package org.example.aemet.service

import com.github.michaelbull.result.Result
import org.example.aemet.errors.ServiceError
import org.example.aemet.errors.StorageError
import org.example.aemet.models.Registro
import java.io.File
import java.util.UUID

interface RegistrosService {
    fun getAllRegistros(): Result<List<Registro>, ServiceError>
    fun getRegistroById(id: UUID): Result<Registro, ServiceError>
    fun getRegistroByLocalidad(localidad: String): Result<List<Registro?>, ServiceError>
    fun getRegistroByProvincia(provincia: String): Result<List<Registro?>, ServiceError>
    fun createRegistro(registro: Registro): Result<Registro, ServiceError>
    fun updateRegistro(id: UUID, registro: Registro): Result<Registro, ServiceError>
    fun deleteRegistroById(id: UUID): Result<Registro, ServiceError>
    suspend fun readCsv(file: File): Result<List<Registro>, ServiceError>
    suspend fun writeJson(file: File, registros: List<Registro>): Result<File, ServiceError>

}