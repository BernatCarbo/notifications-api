package notifications.persistence

/**
 * This class contains all the fields that are at the notifications table
 */
data class NotificationRecord(
    val id: Int,
    val userId: Int,
    val timestamp: Int,
    val message: String,
    val isRead: Boolean
)

/**
 * This class contains the necessary fields to create a new notification
 */
data class NotificationInsertRecord(
    val userId: Int,
    val timestamp: Int,
    val message: String,
    val isRead: Boolean = false
)

/**
 * This class contains all the fields that can be updated in a notification
 */
data class NotificationUpdateRecord(
    val userId: Int? = null,
    val timestamp: Int? = null,
    val message: String? = null,
    val isRead: Boolean? = null
)