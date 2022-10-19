package notifications

import notifications.api.NotificationRequest
import notifications.api.NotificationResponse
import notifications.api.NotificationUpdateRequest
import notifications.persistence.NotificationInsertRecord
import notifications.persistence.NotificationRecord
import notifications.persistence.NotificationUpdateRecord

/**
 * Mapper function to create a new NotificationInsertRecord from the given NotificationRequest
 */
fun NotificationRequest.toNotificationInsertRecord(): NotificationInsertRecord = NotificationInsertRecord(
    userId = userId,
    timestamp = timestamp,
    message = message,
    isRead = isRead
)

/**
 * Mapper function to create a new NotificationResponse from the given NotificationRecord
 */
fun NotificationRecord.toNotificationResponse(): NotificationResponse = NotificationResponse(
    id = id,
    userId = userId,
    timestamp = timestamp,
    isRead = isRead,
    message = message
)

/**
 * Mapper function to create a new NotificationUpdateRecord from the given NotificationUpdateRequest
 */
fun NotificationUpdateRequest.toNotificationUpdateRecord(): NotificationUpdateRecord = NotificationUpdateRecord(
    userId = userId,
    timestamp = timestamp,
    message = message,
    isRead = isRead
)