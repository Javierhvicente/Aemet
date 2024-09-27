package org.example.aemet.errors

sealed class ServiceError(message: String) {
     class RegistroNotFound(message: String): ServiceError(message)
     class LocalidadNotFound(message: String): ServiceError(message)
     class ProvinciaNotFound(message: String): ServiceError(message)
     class RegistroCreationError(message: String): ServiceError(message)
     class RegistroUpdateError(message: String): ServiceError(message)
     class RegistroDeletionError(message: String): ServiceError(message)
     class FileReadingReadError(message: String): ServiceError(message)
}