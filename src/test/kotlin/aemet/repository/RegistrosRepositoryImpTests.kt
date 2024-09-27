package aemet.repository

import org.example.aemet.models.Registro
import org.example.aemet.repository.RegistrosRepositoryImpl
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalTime
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegistrosRepositoryImpTests {
    private lateinit var repository: RegistrosRepositoryImpl
    val registro = Registro(
        UUID.fromString("e0ad1335-5ba3-43f0-a3c2-297e9f32deec"),
        "localidad",
        "provincia",
        10.0,
        LocalTime.parse("00:00:00"),
        10.0,
        LocalTime.parse("00:00:00"),
        10.0
    )
    @BeforeEach
    fun setUp(){
        repository = RegistrosRepositoryImpl()
        repository.addRegistro(registro)
    }

    @Test
    fun getAllRegistros() {
        val result = repository.getAllRegistros()
        assertAll(
            {assert(result.size == 1)},
            {assert(result[0].id == registro.id)},
        )
    }

    @Test
    fun addRegistro() {
        val registroAdd = Registro(
            UUID.fromString("fb3e2dc3-9ef3-4675-ab78-2243972bf3de"),
            "localidadAdd",
            "provinciaAdd",
            10.0,
            LocalTime.parse("00:00:00"),
            10.0,
            LocalTime.parse("00:00:00"),
            10.0
        )
        val result = repository.addRegistro(registro)
        assertAll(
            {assert(result.id == registroAdd.id)},
            {assert(result.localidad == registroAdd.localidad)},
        )
    }

    @Test
    fun getRegistroById() {
        val result = repository.getRegistroById(registro.id)
        assertAll(
            {assert(result?.id == registro.id)},
            {assert(result?.localidad == registro.localidad)},
        )
    }

    @Test
    fun getRegistroByIdNotFound() {
        val result = repository.getRegistroById(UUID.fromString("fb3e2dc3-9ef3-4675-ab78-2243972bf3de"))
        assert(result == null)
    }

    @Test
    fun getRegistroByLocalidad(){
        val result = repository.getRegistrosByLocalidad("localidad")
        assertAll(
            {assert(result.size == 1)},
            {assert(result[0]?.id == registro.id)},
        )
    }

    @Test
    fun getRegistroByLocalidadNotFound(){
        val result = repository.getRegistrosByLocalidad("localidadNotFound")
        assert(result.isEmpty())
    }

    @Test
    fun getRegistroByProvincia(){
        val result = repository.getRegistrosByProvincia("provincia")
        assertAll(
            {assert(result.size == 1)},
            {assert(result[0]?.id == registro.id)},
        )
    }

    @Test
    fun getRegistroByProvinciaNotFound(){
        val result = repository.getRegistrosByProvincia("provinciaNotFound")
        assert(result.isEmpty())
    }

   @Test
   fun updateRegistro(){
         val registroUpdate = Registro(
              UUID.fromString("e0ad1335-5ba3-43f0-a3c2-297e9f32deec"),
              "localidadUpdate",
              "provinciaUpdate",
              10.0,
              LocalTime.parse("00:00:00"),
              10.0,
              LocalTime.parse("00:00:00"),
              10.0
         )
         val result = repository.updateRegistro(registro.id, registroUpdate)
         assertAll(
              {assert(result?.id == registroUpdate.id)},
              {assert(result?.localidad == registroUpdate.localidad)},
         )
   }

    @Test
    fun updateRegistroNotFound(){
        val registroUpdate = Registro(
            UUID.fromString("fb3e2dc3-9ef3-4675-ab78-2243972bf3de"),
            "localidadUpdate",
            "provinciaUpdate",
            10.0,
            LocalTime.parse("00:00:00"),
            10.0,
            LocalTime.parse("00:00:00"),
            10.0
        )
        val result = repository.updateRegistro(UUID.fromString("b7fbc903-6f34-4b9b-9da1-e1391ca09677"), registroUpdate)
        assert(result == null)
    }

     @Test
     fun deleteRegistro(){
          val result = repository.deleteRegistroById(registro.id)
          assertAll(
                {assert(result?.id == registro.id)},
                {assert(result?.localidad == registro.localidad)},
          )
     }

     @Test
     fun deleteRegistroNotFound(){
          val result = repository.deleteRegistroById(UUID.fromString("454db5b4-ea52-4784-8e9c-759cd0d3c38f"))
          assert(result == null)
     }
}