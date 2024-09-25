package org.example.aemet.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.aemet.dto.RegistroDto
import org.example.aemet.errors.StorageError
import org.example.aemet.mappers.toRegistro
import org.example.aemet.models.Registro
import org.lighthousegames.logging.logging
import java.io.File
private val logger = logging()
class StorageCsvImpl: Read<Registro, StorageError> {
    override fun readFile(file: File): Result<List<Registro>, StorageError> {
        return try {
            val lines = file.readLines()
            Ok(lines.map {
                val data = it.split(',')
                RegistroDto(
                    provincia = data[0],
                    localidad = data[1],
                    tempMax = data[2],
                    horaTempMax = data[3],
                    tempMin = data[4],
                    horaTempMin = data[5],
                    precipitacion = data[6]
                ).toRegistro()
            })
        }catch (e: Exception) {
            logger.error{"Error reading file $file"}
           return Err(StorageError.FileReadingError("Error loading registros from file $file"))
        }
    }
}