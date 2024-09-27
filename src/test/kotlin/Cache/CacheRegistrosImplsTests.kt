package Cache

import org.example.aemet.models.Registro
import org.example.cache.CacheRegistrosImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.LocalTime
import java.util.*
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CacheRegistrosImplsTests {
    private lateinit var cache: CacheRegistrosImpl

    @BeforeEach
    fun setup() {
        cache = CacheRegistrosImpl(5)
    }

    @Test
    fun getAndPut(){
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
        cache.put(registro.id, registro)
        val result = cache.get(registro.id)
        assert(result.isOk)
        assertEquals(registro.id, result.value.id)
    }

    @Test
    fun getNoEstaEnCache() {
        val result = cache.get(UUID.fromString("e0ad1335-5ba3-43f0-a3c2-297e9f32dees"))
        assertTrue(result.isErr)
        assertEquals("No existe el valor en la cache", result.error.message)
    }

    @Test
    fun removeOk() {
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


        cache.put(registro.id, registro)
        val resultadoRemove=cache.remove(registro.id)
        val resultadoGet=cache.get(registro.id)

        assertTrue(resultadoGet.isErr)
        assertEquals(registro,resultadoRemove.value)
        assertTrue(resultadoGet.isErr)
    }

    @Test
    fun removeNoEstaEnCache() {

        val resul=cache.remove(UUID.fromString("e0ad1335-5ba3-43f0-a3c2-297e9f32dees"))

        assertTrue(resul.isErr)
        assertEquals("No existe el valor en la cache",resul.error.message)

    }

    @Test
    fun clear() {
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
        cache.put(registro.id, registro)

        val resul = cache.clear()
        assertTrue(resul.isOk)
    }

}