package notifications.persistence

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class NotificationRepository(
    private val database: Database
) {
    /**
     * Inserts a new notification and maps the result into NotificationRecord
     * @param record the notification to be inserted
     * @return the inserted notification or null if fails
     */
    fun create(record: NotificationInsertRecord): NotificationRecord? = transaction(database) {
        NotificationTable.insert {
            it[userId] = record.userId
            it[timestamp] = record.timestamp
            it[isRead] = record.isRead
            it[message] = record.message
        }.resultedValues?.firstOrNull()?.toNotificationRecord()
    }

    /**
     * Selects the user notifications, sorted by its timestamp in descending order,
     * and maps the results into a list of NotificationRecords
     * @param userId the user id
     * @param count the amount of notifications to be retrieved
     * @return a list with the retrieved user notifications or empty if there isn't any
     */
    fun findByUserId(userId: Int, count: Int): List<NotificationRecord> = transaction {
        NotificationTable.select {
            NotificationTable.userId eq userId
        }.orderBy(
            NotificationTable.timestamp,
            SortOrder.DESC
        ).limit(count).map {
            it.toNotificationRecord()
        }
    }

    /**
     * Updates the user notification
     * @param id the notification id
     * @param record the fields to be updated
     * @return true if the notification has been successfully updated otherwise false
     */
    fun updateById(id: Int, record: NotificationUpdateRecord): Boolean = transaction {
        NotificationTable.update ({
            NotificationTable.id eq id
        }) {
            record.userId?.let { newUserId ->
                it[userId] = newUserId
            }
            record.timestamp?.let { newTimestamp ->
                it[timestamp] = newTimestamp
            }
            record.message?.let { newMessage ->
                it[message] = newMessage
            }
            record.isRead?.let { newIsRead ->
                it[isRead] = newIsRead
            }
        } > 0
    }

    /**
     * Selects a notification by its id and maps the result into NotificationRecord
     * @param id the notification id
     * @return the retrieved notification or null if it doesn't exist
     */
    fun findById(id: Int): NotificationRecord? = transaction {
        NotificationTable.select {
            NotificationTable.id eq id
        }.firstOrNull()?.toNotificationRecord()
    }
}