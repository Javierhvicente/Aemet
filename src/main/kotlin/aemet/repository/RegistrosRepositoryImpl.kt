package org.example.aemet.repository

import org.example.aemet.dto.RegistroDto
import org.example.aemet.mappers.toRegistro
import org.example.aemet.models.Registro
import org.example.database.DatabaseConnection
import org.lighthousegames.logging.logging
import java.util.*
private val logger = logging()
class RegistrosRepositoryImpl(
): RegistrosRepository {
    override fun getRegistros(): List<Registro> {
        logger.debug { "Obteniendo todos los registros de la base de datos" }

        val sql = "SELECT * FROM registros_entity"
        val registros = mutableListOf<Registro>()

        return try {
            DatabaseConnection.use { connection ->
                connection.prepareStatement(sql).use { preparedStatement ->
                    preparedStatement.executeQuery().use { resultSet ->
                        while (resultSet.next()) {
                            val registro = RegistroDto(
                                UUID.fromString(resultSet.getString("id")),
                                resultSet.getString("localidad"),
                                resultSet.getString("provincia"),
                                resultSet.getString("tempMax"),
                                resultSet.getString("horaTempMax"),
                                resultSet.getString("tempMin"),
                                resultSet.getString("horaTempMin"),
                                resultSet.getString("precipitacion")
                            ).toRegistro()
                            registros.add(registro)
                        }
                    }
                }
            }
            registros
        } catch (e: Exception) {
            logger.error { "Error al obtener los registros de la base de datos: ${e.message}" }
            emptyList()
        }
    }

    override fun getRegistroById(id: UUID): Registro? {
        logger.debug { "Obteniendo el registro con id $id de la base de datos" }
        try {
            var registro: Registro? = null
            DatabaseConnection.use { connection ->
                val sql = "SELECT * FROM registros_entity WHERE id = ?"
                val statement = connection.prepareStatement(sql)!!
                statement.setString(1, id.toString())
                val result = statement.executeQuery()
                if (result.next()) {
                    registro = RegistroDto(
                        UUID.fromString(result.getString("id")),
                        result.getString("localidad"),
                        result.getString("provincia"),
                        result.getString("tempMax"),
                        result.getString("horaTempMax"),
                        result.getString("tempMin"),
                        result.getString("horaTempMin"),
                        result.getString("precipitacion")
                    ).toRegistro()
                }
            }
            return registro
        }catch (e: Exception){
            logger.error { "Error al obtener el registro con id $id de la base de datos" }
            return null
        }
    }

    override fun getRegistrosByLocalidad(localidad: String): List<Registro> {
        logger.debug { "Obteniendo los registros de la localidad $localidad de la base de datos" }
         return try {
            val sql = "SELECT * FROM registros_entity WHERE localidad = ?"
            val registros = mutableListOf<Registro>()
            DatabaseConnection.use { connection ->
                val statement = connection.prepareStatement(sql)!!
                statement.setString(1, localidad)
                val result = statement.executeQuery()
                while (result.next()) {
                    val registro = RegistroDto(
                        UUID.fromString(result.getString("id")),
                        result.getString("localidad"),
                        result.getString("provincia"),
                        result.getString("tempMax"),
                        result.getString("horaTempMax"),
                        result.getString("tempMin"),
                        result.getString("horaTempMin"),
                        result.getString("precipitacion")
                    ).toRegistro()
                    registros.add(registro)
                }
            }
             registros
        }catch (e: Exception){
            logger.error { "Error al obtener los registros de la localidad $localidad de la base de datos" }
             emptyList()
        }
    }

    override fun getRegistrosByProvincia(provincia: String): List<Registro> {
        logger.debug { "Obteniendo los registros de la provincia $provincia de la base de datos" }
        return try{
            val sql = "SELECT * FROM registros_entity WHERE provincia =?"
            val registros = mutableListOf<Registro>()
            DatabaseConnection.use { connection ->
                val statement = connection.prepareStatement(sql)!!
                statement.setString(1, provincia)
                val result = statement.executeQuery()
                while (result.next()) {
                    val registro = RegistroDto(
                        UUID.fromString(result.getString("id")),
                        result.getString("localidad"),
                        result.getString("provincia"),
                        result.getString("tempMax"),
                        result.getString("horaTempMax"),
                        result.getString("tempMin"),
                        result.getString("horaTempMin"),
                        result.getString("precipitacion")
                    ).toRegistro()
                    registros.add(registro)
                }
            }
            registros
        }catch (e: Exception) {
            logger.error { "Error al obtener los registros de la provincia $provincia de la base de datos" }
            emptyList()
        }
    }

    override fun addRegistro(registro: Registro): Registro {
        logger.debug { "Añadiendo un nuevo registro a la base de datos" }
         try {
            val sql = "INSERT INTO registros_entity (id, localidad, provincia, tempMax, horaTempMax, tempMin, horaTempMin, precipitacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            DatabaseConnection.use { connection ->
                val statement = connection.prepareStatement(sql)!!
                statement.setString(1, registro.id.toString())
                statement.setString(2, registro.localidad)
                statement.setString(3, registro.provincia)
                statement.setString(4, registro.tempMax.toString())
                statement.setString(5, registro.horaTempMax.toString())
                statement.setString(6, registro.tempMin.toString())
                statement.setString(7, registro.horaTempMin.toString())
                statement.setString(8, registro.precipitacion.toString())
                statement.executeUpdate()
            }
        }catch (e: Exception){
            logger.error { "Error al añadir un nuevo registro a la base de datos" }
        }
        return registro
    }

    override fun updateRegistro(id: UUID, registro: Registro): Registro? {
        logger.debug { "Actualizando el registro con id $id en la base de datos" }
        return try {
            val sql = "UPDATE registros_entity SET localidad = ?, provincia = ?, tempMax = ?, horaTempMax = ?, tempMin = ?, horaTempMin = ?, precipitacion = ? WHERE id = ?"
            DatabaseConnection.use { connection ->
                val statement = connection?.prepareStatement(sql)!!
                statement.setString(1, registro.localidad)
                statement.setString(2, registro.provincia)
                statement.setString(3, registro.tempMax.toString())
                statement.setString(4, registro.horaTempMax.toString())
                statement.setString(5, registro.tempMin.toString())
                statement.setString(6, registro.horaTempMin.toString())
                statement.setString(7, registro.precipitacion.toString())
                statement.setString(8, id.toString())
                val rowsAffected = statement.executeUpdate()

                if (rowsAffected > 0) {
                   logger.debug { "registro actualizado correctamente." }
                    registro
                } else {
                    logger.error { "No se encontró el registro con id: ${registro.id}" }
                    null
                }
            }
            registro
        }catch (e: Exception){
            logger.error { "Error al actualizar el registro con id $id en la base de datos" }
            null
        }
    }

    override fun deleteRegistroById(id: UUID): Registro? {
        logger.debug { "Eliminando el registro con id $id de la base de datos" }
        return try {
            var registro: Registro? = null
            DatabaseConnection.use { connection ->
                val sql = "DELETE FROM registros_entity WHERE id = ?"
                val statement = connection?.prepareStatement(sql)!!
                statement.setString(1, id.toString())
                val rowsAffected = statement.executeUpdate()

                if (rowsAffected > 0) {
                    registro = getRegistroById(id)
                }
            }
            registro
        }catch (e: Exception){
            logger.error { "Error al eliminar el registro con id $id de la base de datos" }
            null
        }
    }
}