package org.example.aemet.storage

import com.github.michaelbull.result.Result
import java.io.File

interface Read<T, E> {
    fun readFile(file: File): Result<T, E>
}