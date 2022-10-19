package notifications.persistence

import org.jetbrains.exposed.sql.ResultRow

/**
 * Mapper function to create a new NotificationRecord from the given ResultRow
 */
fun ResultRow.toNotificationRecord(): NotificationRecord = NotificationRecord(
    id = this[NotificationTable.id].value,
    userId = this[NotificationTable.userId],
    timestamp = this[NotificationTable.timestamp],
    isRead = this[NotificationTable.isRead],
    message = this[NotificationTable.message]
)