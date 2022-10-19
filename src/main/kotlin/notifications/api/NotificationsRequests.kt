package notifications.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotificationRequest(
    @SerialName("user_id")
    val userId: Int,
    val timestamp: Int,
    val message: String,
    val isRead: Boolean = false
)

@Serializable
data class NotificationUpdateRequest(
    @SerialName("user_id")
    val userId: Int? = null,
    val timestamp: Int? = null,
    val message: String? = null,
    val isRead: Boolean? = null
)