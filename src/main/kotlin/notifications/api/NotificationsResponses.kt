package notifications.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationResponse(
    val id: Int,
    @SerialName("user_id")
    val userId: Int,
    val timestamp: Int,
    val message: String,
    val isRead: Boolean
)