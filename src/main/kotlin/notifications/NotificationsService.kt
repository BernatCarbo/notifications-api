package notifications

import notifications.api.NotificationRequest
import notifications.api.NotificationResponse
import notifications.api.NotificationUpdateRequest
import common.api.NotificationInsertFailedException
import common.api.NotificationNotFoundException
import common.api.NotificationUpdateFailedException
import notifications.persistence.NotificationRepository

class NotificationsService(
    private val notificationRepository: NotificationRepository
) {
    /**
     * Creates a new notification and maps the result into a NotificationResponse
     * @throws NotificationInsertFailedException if it fails to create the notification
     * @param request the notification request
     * @return the created notification
     */
    fun createNotification(request: NotificationRequest): NotificationResponse {
        val notification = request.toNotificationInsertRecord()
        return notificationRepository.create(notification)?.toNotificationResponse()
            ?: throw NotificationInsertFailedException("Failed to create the notification")
    }

    /**
     * Finds the user notifications and maps the result into a list of NotificationResponse
     * @param userId the user id
     * @param count the amount of notifications to be retrieved
     * @return a list with the retrieved user notifications or empty if there isn't any
     */
    fun findNotificationsByUser(userId: Int, count: Int): List<NotificationResponse> {
        val notifications = notificationRepository.findByUserId(userId, count)
        return notifications.map {
            it.toNotificationResponse()
        }
    }

    /**
     * Updates the user notification and maps the result into a NotificationResponse
     * @throws NotificationNotFoundException if the notification id doesn't exist
     * @param id the notification id
     * @param request the notification update request
     * @return the updated notification
     */
    fun updateNotificationById(id: Int, request: NotificationUpdateRequest): NotificationResponse {
        if (request.userId == null && request.timestamp == null && request.message == null && request.isRead == null) {
            throw NotificationUpdateFailedException("No recognized fields were found in the payload")
        }
        val currentNotification = notificationRepository.findById(id)
            ?: throw NotificationNotFoundException("Notification not found for the id: $id")
        val notificationUpdate = request.toNotificationUpdateRecord()
        notificationRepository.updateById(id, notificationUpdate)
        return currentNotification.copy(
            userId = notificationUpdate.userId ?: currentNotification.userId,
            timestamp = notificationUpdate.timestamp ?: currentNotification.timestamp,
            message = notificationUpdate.message ?: currentNotification.message,
            isRead = notificationUpdate.isRead ?: currentNotification.isRead
        ).toNotificationResponse()
    }
}