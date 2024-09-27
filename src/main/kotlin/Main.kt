package org.example


import com.github.michaelbull.result.Err
import com.github.michaelbull.result.mapBoth
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.example.cache.CacheRegistrosImpl
import org.example.aemet.errors.StorageError
import org.example.aemet.repository.RegistrosRepositoryImpl
import org.example.aemet.service.RegistrosServiceImpl
import org.example.aemet.storage.StorageCsvImpl
import org.example.aemet.storage.StorageJsonImpl
import org.example.config.Config
import java.io.File

fun main(args: Array<String>) = runBlocking {
    if (args.isEmpty()) {
        println("Se requieren 3 archivos CSV como argumentos.")
    }

    val service = RegistrosServiceImpl(
        cache = CacheRegistrosImpl(Config.cacheSize),
        repository = RegistrosRepositoryImpl(),
        storageJson = StorageJsonImpl(),
        storageCsv = StorageCsvImpl()
    )

    val results = args.map { arg ->
        async {
            service.readCsv(File(arg)).mapBoth(
                success = { println("CSV leído correctamente para archivo: $arg") },
                failure = {
                    println("Error: No se ha podido leer el archivo CSV $arg")
                }
            )
        }
    }
    results.awaitAll()

    val listaRegistros = service.getAllRegistros().value
    println("Registros: $listaRegistros")
}