package org.example


import com.github.michaelbull.result.Err
import com.github.michaelbull.result.mapBoth
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.example.cache.CacheRegistrosImpl
import org.example.aemet.errors.StorageError
import org.example.aemet.errors.ValidatorError
import org.example.aemet.repository.RegistrosRepositoryImpl
import org.example.aemet.service.RegistrosServiceImpl
import org.example.aemet.storage.StorageCsvImpl
import org.example.aemet.storage.StorageJsonImpl
import org.example.aemet.validators.validateArgsEntrada
import org.example.config.Config
import java.io.File

fun main(args: Array<String>) = runBlocking {
    if(args.isEmpty()) {
        println("No arguments provided.")
    }
    validateArgsEntrada(args[0]).mapBoth(
        success = { println("Archivo válido: $it") },
        failure = {
            Err(ValidatorError.InvalidExtension("Error: archivo inválido."))
        }
    )

    val service = RegistrosServiceImpl(
        cache = CacheRegistrosImpl(Config.cacheSize),
        repository = RegistrosRepositoryImpl(),
        storageJson = StorageJsonImpl(),
        storageCsv = StorageCsvImpl()
    )

    service.readCsv(File(args[0])).mapBoth(
        success = { println("CSV leído correctamente.") },
        failure = { println("Error al leer CSV: $it") }
    )

    val listaRegistros = service.getAllRegistros().value
    println("Registros: $listaRegistros")
}