package org.example.config

import org.lighthousegames.logging.logging
import java.io.InputStream
import java.util.*

private val logger = logging()
private const val CONFIG_FILE = "config.properties"
object Config {
    private val actualDirectory = System.getProperty("user.dir")

    val dataBaseUrl: String by lazy {
        readProperty("dataBaseUrl") ?: "jdbc:sqlite:aemet.db"
    }

    val databaseInmemory: Boolean by lazy {
        readProperty("databaseInmemory")?.toBoolean() ?: true
    }

    val databasInitTables: Boolean by lazy {
        readProperty("database.init.Tables")?.toBoolean() ?: true
    }

    val cacheSize: Int by lazy {
        readProperty("cacheSize")?.toInt() ?: 5
    }

    val removeDatabase: Boolean by lazy {
        readProperty("database.removedata")?.toBoolean() ?: true
    }


    private fun readProperty(propiedad: String): String? {
        return try {
            logger.debug { "Leyendo propiedad: $propiedad" }
            val properties = Properties()
            val inputStream: InputStream = ClassLoader.getSystemResourceAsStream(CONFIG_FILE)
                ?: throw Exception("No se puede leer el fichero de configuración $CONFIG_FILE")
            properties.load(inputStream)
            properties.getProperty(propiedad)
        }catch (e: Exception){
            logger.error { "Error al leer la propiedad $propiedad: ${e.message}" }
            null
        }
    }
}