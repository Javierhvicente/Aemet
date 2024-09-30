package org.example.aemet.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.aemet.dto.RegistroDto
import org.example.aemet.errors.StorageError
import org.example.aemet.mappers.toRegistro
import org.example.aemet.models.Registro
import org.example.aemet.validators.validateCsvEntries
import org.lighthousegames.logging.logging
import java.io.File
import java.util.*
import kotlin.math.log

private val logger = logging()
class StorageCsvImpl: Read<Registro, StorageError> {
    override fun readFile(file: File): Result<List<Registro>, StorageError> {
        logger.debug { "Reading file $file" }
        return try {
            val lista = file.readLines()
            return Ok(lista
                .map {
                    val data = it.split(";")
                    if(validateCsvEntries(data).isErr) {
                        return Err(StorageError.FileReadingError("Error: entrada inválida."))
                    }
                    RegistroDto(
                        localidad = data[0],
                        provincia = data[1],
                        tempMax = data[2],
                        horaTempMax = data[3],
                        tempMin = data[4],
                        horaTempMin = data[5],
                        precipitacion = data[6]
                    ).toRegistro()
                }
            )
        }catch (e: Exception) {
            logger.error { "Error reading file $file" }
            Err(StorageError.FileReadingError("Error: loading file $file."))
        }
    }
}