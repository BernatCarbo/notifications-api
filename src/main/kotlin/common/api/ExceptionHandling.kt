package common.api

import common.createLogger
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

private val logger = createLogger {}

@Serializable
private data class ApiError(
    val message: String
)

fun Application.installExceptionHandling() {
    install(StatusPages) {
        exception<Throwable> { call, exception ->
            when(exception) {
                is BadRequestException -> call.respond(HttpStatusCode.BadRequest, ApiError(exception.message ?: ""))
                is SerializationException -> call.respond(HttpStatusCode.BadRequest, ApiError("Failed to parse the payload: ${exception.message}"))
                is NotificationInsertFailedException -> call.respond(HttpStatusCode.InternalServerError, ApiError(exception.message))
                is NotificationUpdateFailedException -> call.respond(HttpStatusCode.InternalServerError, ApiError(exception.message))
                is NotificationNotFoundException -> call.respond(HttpStatusCode.NotFound, ApiError(exception.message))
                is NotificationMessageTooLongException -> call.respond(HttpStatusCode.BadRequest, ApiError(exception.message))
                is NotificationInvalidUserIdException -> call.respond(HttpStatusCode.BadRequest, ApiError(exception.message))
                else -> {
                    logger.warn(exception) { "Unhandled error during a request" }
                    call.respond(HttpStatusCode.InternalServerError, ApiError("Internal server error: ${exception.message}"))

                }
            }
        }
    }
}