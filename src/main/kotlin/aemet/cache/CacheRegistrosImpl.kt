package org.example.aemet.cache

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.aemet.cache.error.CacheError
import org.example.aemet.models.Registro
import com.github.michaelbull.result.Result
import org.lighthousegames.logging.logging
import java.util.UUID
private val logger = logging()

open class CacheRegistrosImpl(
    private val size: Int
) : Cache<UUID, Registro> {
    private val cache = mutableMapOf<UUID, Registro>()

    override fun get(key: UUID): Result<Registro, CacheError> {
        logger.debug { "Obteniendo valor de la cache" }
        return if (cache.containsKey(key)) {
            logger.debug { "Obtenido valor de la cache con �xito" }
            Ok(cache.getValue(key))
        } else {
            logger.info { "No exite el valor en la cache" }
            Err(CacheError("No existe el valor en la cache"))
        }
    }

    override fun put(key: UUID, value: Registro): Result<Registro, Nothing> {
        logger.debug { "Guardando valor en la cache" }
        if (cache.size >= size && !cache.containsKey(key)) {
            logger.debug { "Eliminando valor de la cache" }
            cache.remove(cache.keys.first())
        }
        cache[key] = value
        logger.debug { "Guardado valor en la cache con �xito" }
        return Ok(value)
    }

    override fun remove(key: UUID):Result<Registro, CacheError> {
        logger.debug { "Eliminando valor de la cache" }
        return if (cache.containsKey(key)) {
            logger.debug { "Eliminado valor de la cache con �xito" }
            Ok(cache.remove(key)!!)
        } else {
            logger.info { "No exite el valor en la cache" }
            Err(CacheError("No existe el valor en la cache"))
        }
    }

    override fun clear(): Result<Unit, Nothing> {
        logger.debug { "Limpiando cache" }
        cache.clear()
        logger.debug { "Cache limpiada con �xito" }
        return Ok(Unit)
    }
}