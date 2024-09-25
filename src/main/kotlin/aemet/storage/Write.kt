package org.example.aemet.storage

import com.github.michaelbull.result.Result
import java.io.File

interface Write<T, E> {
    fun writeFile(file: File, lista: List<T>): Result<Unit, E>
}