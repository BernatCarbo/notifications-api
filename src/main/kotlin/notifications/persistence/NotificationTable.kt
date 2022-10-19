package notifications.persistence

import org.jetbrains.exposed.dao.id.IntIdTable

object NotificationTable : IntIdTable("notifications", "id") {
    val userId = integer("user_id")
    val timestamp = integer("timestamp")
    val isRead = bool("is_read")
    val message = varchar("message", 255)
}