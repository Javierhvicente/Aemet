package aemet.validators

import org.example.aemet.validators.validateCsvEntries
import org.junit.jupiter.api.Test

class ValidatorCsvImplTests {
    @Test fun validateCsvEntries() {
        val entries = listOf("Estaca de Bares","A Coru�a", "18.3", "12:50", "15.9" , "12:50", "0")
        val result = validateCsvEntries(entries)
        assert(result.isOk)
    }

    @Test fun validateCsvEntriesInvalidLocalidadVacio(){
        val entries = listOf("","A Coru�a", "18.3", "12:50", "15.9" , "12:50", "0")
        val result = validateCsvEntries(entries)
        assert(result.isErr)
    }
}