package org.example.aemet.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.aemet.errors.ValidatorError
import org.lighthousegames.logging.logging
import sun.util.logging.resources.logging
import java.io.File
import java.time.LocalDate

private val logger = org.lighthousegames.logging.logging()

fun validateCsvEntries(item: List<String>): Result<List<String>, ValidatorError> {

    val regexNombres = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ'-]+(\\s+[a-zA-ZáéíóúÁÉÍÓÚñÑ'-]+)*\$")
    val regexHora = Regex("^(?:[01]\\d|2[0-3]):[0-5]\\d$")

    return when {
        item[0].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La localidad no puede ser un campo vacío"))
        item[0].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La localidad no puede ser un campo vacío"))
        !item[0].matches(regexNombres) -> Err(ValidatorError.InvalidRegistroFormat("La localidad no puede contener números"))

        item[1].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La provincia no puede ser un campo vacío"))
        item[1].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La provincia no puede ser un campo vacío"))
        !item[1].matches(regexNombres) -> Err(ValidatorError.InvalidRegistroFormat("La provincia solo puede contener letras: ${item[1]}"))

        item[2].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La temperatura no puede ser un campo vacío"))
        item[2].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La temperatura no puede ser un campo vacío"))
        item[2].toDoubleOrNull() == null -> Err(ValidatorError.InvalidRegistroFormat("La temperatura debe ser un número decimal"))

        item[3].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La hora de temperatura no puede ser un campo vacío"))
        item[3].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La hora de temperatura no puede ser un campo vacío"))
        !item[3].matches(regexHora) -> Err(ValidatorError.InvalidRegistroFormat("La hora debe tener el formato 'HH:MM'"))

        item[4].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La temperatura no puede ser un campo vacío"))
        item[4].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La temperatura no puede ser un campo vacío"))
        item[4].toDoubleOrNull() == null -> Err(ValidatorError.InvalidRegistroFormat("La temperatura debe ser un número decimal"))

        item[5].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La hora de temperatura máxima no puede ser un campo vacío"))
        item[5].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La hora de temperatura no puede ser un campo vacío"))
        item[5].matches(regexHora) -> Err(ValidatorError.InvalidRegistroFormat("La hora debe tener el formato 'HH:MM'"))

        item[6].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La precipitación no puede ser un campo vacío"))
        item[6].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La precipitación no puede ser un campo vacío"))
        item[6].toDoubleOrNull() == null -> Err(ValidatorError.InvalidRegistroFormat("La precipitación debe ser un número decimal"))

        else ->{ Ok(item) }
    }
}
