package notifications.api

import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import notifications.NotificationsService
import common.api.ApiController
import common.api.NotificationInvalidUserIdException
import common.api.NotificationMessageTooLongException

@Serializable
@Resource("notifications")
private class NotificationsPath

@Serializable
@Resource("notifications/by_user/{user_id}")
private data class NotificationsByUserIdPath(
    @SerialName("user_id")
    val userId: Int,
    val count: Int = 20 // Default count is 20
)

@Serializable
@Resource("notifications/update/{id}")
private class NotificationsUpdateByIdPath(
    val id: Int
)

class NotificationController(
    private val notificationsService: NotificationsService
) : ApiController {
    override fun Route.registerApi() {
        post<NotificationsPath> {
            val request: NotificationRequest = call.receive()
            request.message.validateNotificationMessage()
            request.userId.validateNotificationUserId()

            val response = notificationsService.createNotification(request)
            call.respond(response)
        }
        get<NotificationsByUserIdPath> { path ->
            val response = notificationsService.findNotificationsByUser(path.userId, path.count)
            call.respond(response)
        }
        put<NotificationsUpdateByIdPath> { path ->
            val request: NotificationUpdateRequest = call.receive()
            request.message?.validateNotificationMessage()
            request.userId?.validateNotificationUserId()

            val response = notificationsService.updateNotificationById(path.id, request)
            call.respond(response)
        }
    }

    /**
     * Validates the notification message length
     * @throws NotificationMessageTooLongException if the notification length exceeds the limit of 255 characters
     */
    private fun String?.validateNotificationMessage() {
        this?.let {
            if (it.length > 255) {
                throw NotificationMessageTooLongException("The notification message exceeds the maximum length of 255 characters")
            }
        }
    }

    /**
     * Validates the notification userId
     * @throws NotificationInvalidUserIdException if the notification userId is negative
     */
    private fun Int?.validateNotificationUserId() {
        this?.let {
            if (it < 0) {
                throw NotificationInvalidUserIdException("The notification user id shouldn't be negative")
            }
        }
    }

    // TODO Validate the timestamp as well..?
}