package org.example.aemet.errors

sealed class StorageError(message: String) {
    class FileReadingError(message: String): StorageError(message)
    class FileWritingError(message: String): StorageError(message)


}