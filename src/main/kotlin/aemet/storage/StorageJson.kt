package org.example.aemet.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.aemet.dto.RegistroDto
import org.example.aemet.errors.StorageError
import org.example.aemet.mappers.toRegistroDto
import org.example.aemet.models.Registro
import org.lighthousegames.logging.logging
import java.io.File
private val logger = logging()
class StorageJson: Write<Registro, StorageError> {
    override fun writeFile(file: File, lista: List<Registro>): Result<Unit, StorageError> {
        logger.debug { "Storing registros into $file" }
        return try {
            val json = Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }
            val jsonString = json.encodeToString<List<RegistroDto>>(lista.map { it.toRegistroDto() })
            file.writeText(jsonString)
            Ok(Unit)
        }catch (e: Exception) {
            logger.error { "Error writing file $file" }
            return Err(StorageError.FileWritingError("Error writing registros to file $file"))
        }
    }
}