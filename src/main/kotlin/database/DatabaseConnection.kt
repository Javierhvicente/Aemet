package org.example.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.config.Config
import org.lighthousegames.logging.logging
import java.sql.Connection

private val logger = logging()
object DatabaseConnection {
    private var dataSource: HikariDataSource? = null

    init {
        initPoolConnection()
        if(Config.databasInitTables){
            initTables()
        }

    }

    private fun initPoolConnection(){
        logger.debug { "Inicializando el pool de conexiones" }
        val config = HikariConfig().apply{
            jdbcUrl = Config.dataBaseUrl
            driverClassName = "org.sqlite.JDBC"
        }
    }

    private fun initTables() {
        logger.debug { "Inicializando tablas de la base de datos" }
        try{
            val data = ClassLoader.getSystemResourceAsStream("data.sql")?.bufferedReader()!!
                .use{ dataSource?.connection?.createStatement().use { statement -> statement!!.execute(it.readText()) } }
        }catch (e: Exception){
            logger.error { "Error al inicializar las tablas de la base de datos: ${e.message}" }
        }
    }



    fun <T> use(block: (Connection) -> T): T {
        return dataSource?.connection.use { connection ->
            try {
                block(connection!!)
            } catch (e: Exception) {
                logger.error { "Error en la base de datos: ${e.message}" }
                throw e
            } finally {
                connection?.close()
            }
        }
    }

    fun close() {
        logger.debug { "Cerrando pool de conexiones" }
        dataSource?.close()
        logger.debug { "Pool de conexiones cerrado" }
    }

}