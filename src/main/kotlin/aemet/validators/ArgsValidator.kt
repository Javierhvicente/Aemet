package org.example.aemet.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.aemet.errors.ValidatorError
import org.lighthousegames.logging.logging
import java.io.File

private val logger = logging()

fun validateArgsEntrada(path: String): Result<String, ValidatorError>{
    logger.debug { "Validating input arguments" }
    logger.debug { "Comprobando si el archivo existe: $path" }
    if (!File(path).exists() || !File(path).canRead() || !File(path).isFile) {
        logger.error {"Error al leer el archivo $path. No se puede encontrar o leer. Verifique que el archivo exista y que tenga permisos de lectura."}
        Err(ValidatorError.FileDoesNotExistError("El archivo $path no existe o no se puede leer"))
    }
    val archivo = File(path)
    if (archivo.extension.equals("csv", ignoreCase = true)) {
        logger.error { "Error al leer el archivo $path. No tiene extensión.csv. Debe ser.csv. Verifique que el archivo tenga la extensión correcta."}
        Err(ValidatorError.InvalidExtension("El archivo $path no tiene extensión .csv"))
    }
    return Ok(path)
}