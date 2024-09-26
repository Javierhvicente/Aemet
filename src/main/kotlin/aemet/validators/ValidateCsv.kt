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

    val regexNombres = Regex("^[a-zA-Z������������'-]+(\\s+[a-zA-Z������������'-]+)*\$")
    val regexHora = Regex("^(?:[01]\\d|2[0-3]):[0-5]\\d$")

    return when {
        item[0].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La localidad no puede ser un campo vac�o"))
        item[0].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La localidad no puede ser un campo vac�o"))
        !item[0].matches(regexNombres) -> Err(ValidatorError.InvalidRegistroFormat("La localidad no puede contener n�meros"))

        item[1].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La provincia no puede ser un campo vac�o"))
        item[1].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La provincia no puede ser un campo vac�o"))
        !item[1].matches(regexNombres) -> Err(ValidatorError.InvalidRegistroFormat("La provincia solo puede contener letras: ${item[1]}"))

        item[2].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La temperatura no puede ser un campo vac�o"))
        item[2].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La temperatura no puede ser un campo vac�o"))
        item[2].toDoubleOrNull() == null -> Err(ValidatorError.InvalidRegistroFormat("La temperatura debe ser un n�mero decimal"))

        item[3].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La hora de temperatura no puede ser un campo vac�o"))
        item[3].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La hora de temperatura no puede ser un campo vac�o"))
        !item[3].matches(regexHora) -> Err(ValidatorError.InvalidRegistroFormat("La hora debe tener el formato 'HH:MM'"))

        item[4].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La temperatura no puede ser un campo vac�o"))
        item[4].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La temperatura no puede ser un campo vac�o"))
        item[4].toDoubleOrNull() == null -> Err(ValidatorError.InvalidRegistroFormat("La temperatura debe ser un n�mero decimal"))

        item[5].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La hora de temperatura m�xima no puede ser un campo vac�o"))
        item[5].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La hora de temperatura no puede ser un campo vac�o"))
        item[5].matches(regexHora) -> Err(ValidatorError.InvalidRegistroFormat("La hora debe tener el formato 'HH:MM'"))

        item[6].isEmpty() -> Err(ValidatorError.InvalidRegistroFormat("La precipitaci�n no puede ser un campo vac�o"))
        item[6].isBlank() -> Err(ValidatorError.InvalidRegistroFormat("La precipitaci�n no puede ser un campo vac�o"))
        item[6].toDoubleOrNull() == null -> Err(ValidatorError.InvalidRegistroFormat("La precipitaci�n debe ser un n�mero decimal"))

        else ->{ Ok(item) }
    }
}
