package org.example.database

import org.example.config.Config
import org.lighthousegames.logging.logging
import java.sql.Connection
import java.sql.DriverManager

private val logger = logging()
class DatabaseConnection {
    var connection: Connection? = null
    private set

    init {
        initConexion()
        if(Config.databasInitTables){
            initTables()
        }

    }

    private fun initTables() {
        logger.debug { "Inicializando tablas de la base de datos" }
        try{
            val data = ClassLoader.getSystemResourceAsStream("data.sql")?.bufferedReader()!!
                .use{ connection?.createStatement().use { statement -> statement!!.execute(it.readText()) } }
        }catch (e: Exception){
            logger.error { "Error al inicializar las tablas de la base de datos: ${e.message}" }
        }
    }

    private fun initConexion() {
        logger.debug { "Iniciando conexión con la base de datos" }
        if (connection == null || connection!!.isClosed) {
            connection = DriverManager.getConnection(Config.dataBaseUrl)
        }
        logger.debug { "Conexión con la base de datos inicializada" }
    }


    fun <T> useConnection(block: (DatabaseConnection) -> T) {
        try {
            initConexion()
            block(this)
        } catch (e: Exception) {
            logger.error { "Error en la base de datos: ${e.message}" }
        } finally {
            close()
        }
    }

    fun close() {
        logger.debug { "Cerrando conexión con la base de datos" }
        if (!connection!!.isClosed) {
            connection!!.close()
        }
        logger.debug { "Conexión con la base de datos cerrada" }
    }

}