package org.example.aemet.errors

sealed class ValidatorError(message: String) {
    class FileDoesNotExistError(message: String) : ValidatorError(message)
    class InvalidExtension(message: String) : ValidatorError(message)
    class InvalidRegistroFormat(message: String) : ValidatorError(message)
}