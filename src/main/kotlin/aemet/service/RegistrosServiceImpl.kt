package org.example.aemet.service

import com.github.michaelbull.result.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.example.aemet.cache.CacheRegistrosImpl
import org.example.aemet.errors.ServiceError
import org.example.aemet.errors.StorageError
import org.example.aemet.models.Registro
import org.example.aemet.repository.RegistrosRepository
import org.example.aemet.storage.StorageCsvImpl
import org.example.aemet.storage.StorageJsonImpl
import org.lighthousegames.logging.logging
import java.io.File
import java.util.*
private val logger = logging()
class RegistrosServiceImpl(
    private val storageJson: StorageJsonImpl,
    private val storageCsv: StorageCsvImpl,
    private val repository: RegistrosRepository,
    private val cache: CacheRegistrosImpl
): RegistrosService {
    override fun getAllRegistros(): Result<List<Registro>, ServiceError> {
        logger.debug { "Getting all registros" }
        return Ok(repository.getAllRegistros())
    }

    override fun getRegistroById(id: UUID): Result<Registro, ServiceError> {
        logger.debug { "Getting registro by id $id" }
        return cache.get(id).mapBoth(
            success = {
                logger.debug { "Registro encontrado en cache" }
                Ok(it)
            },
            failure = {
                logger.info { "Registro no encontrado en cache" }
                repository.getRegistroById(id)
                    ?.let { Ok(it) }?.andThen { tenista ->
                        logger.debug { "Guardando en cache" }
                        cache.put(tenista.id, tenista)
                    }
                    ?: Err(ServiceError.RegistroNotFound("Registro no encontrado con id: $id"))
            }
        )
    }

    override fun getRegistroByLocalidad(localidad: String): Result<List<Registro?>, ServiceError> {
        logger.debug { "Getting registro by localidad $localidad" }
        val registros = repository.getRegistrosByLocalidad(localidad)
        if(registros.isEmpty()) {
            return Err(ServiceError.RegistroNotFound("No se han encontrado registros con localidad $localidad"))
        }else{
            return Ok(registros)
        }

    }

    override fun getRegistroByProvincia(provincia: String): Result<List<Registro?>, ServiceError> {
        val registros = repository.getRegistrosByProvincia(provincia)
        if(registros.isEmpty()) {
            return Err(ServiceError.RegistroNotFound("No se han encontrado registros con provincia $provincia"))
        }else{
            return Ok(registros)
        }
    }

    override fun createRegistro(registro: Registro): Result<Registro, ServiceError> {
        logger.debug { "Creating registro" }
        return Ok(repository.addRegistro(registro).also { cache.put(it.id, it) })
    }

    override fun updateRegistro(id: UUID, registro: Registro): Result<Registro, ServiceError> {
        logger.debug { "Updating registro with id $id" }
        return repository.updateRegistro(id, registro)
            ?.let { Ok(it) }?.andThen { registro ->
                logger.debug { "Updating cache" }
                cache.put(registro.id, registro)
            }
            ?: Err(ServiceError.RegistroUpdateError("Registro no se pudo actualizar con id: $id")
        )
    }

    override fun deleteRegistroById(id: UUID): Result<Registro, ServiceError> {
        logger.debug { "Deleting registro with id $id" }
        return repository.deleteRegistroById(id)
            ?.let {
                logger.debug { "Deleting from cache" }
                cache.remove(id)
                Ok(it)
            }
            ?: Err(ServiceError.RegistroDeletionError("Registro no borrado con id: $id"))
    }

    suspend override fun readCsv(file: File): Result<List<Registro>, ServiceError> {
        logger.debug { "Reading csv file $file" }
        return withContext(Dispatchers.IO) {
            val result = storageCsv.readFile(file)
            result.mapBoth(
                success = { registros ->
                    registros.map { p ->
                        async {
                            repository.addRegistro(p)
                            logger.debug { "Stored tenista: $p" }
                        }
                    }.forEach { it.await() }
                    Ok(registros)
                },
                failure = {
                    logger.error { "Error loading Registros from file: $file" }
                    Err(ServiceError.FileReadingReadError("Error loading Registros from file: $file"))
                }
            )
        }
    }

    suspend override fun writeJson(file: File, registros: List<Registro>): Result<File, ServiceError> {
        logger.debug { "Writing json file $file" }
        return withContext(Dispatchers.IO) {
            val result = storageJson.writeFile(file, registros)
            result.mapBoth(
                success = {
                    logger.debug { "Registros stored in file $file" }
                    Ok(file)
                },
                failure = {
                    logger.error { "Error writing Registros to file $file" }
                    Err(ServiceError.FileWritingError("Error writing Registros to file $file"))
                }
            )
        }
    }
}