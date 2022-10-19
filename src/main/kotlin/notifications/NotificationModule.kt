package notifications

import notifications.api.NotificationController
import common.api.ApiController
import notifications.persistence.NotificationRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val notificationModule = module {
    single { NotificationRepository(get()) }
    single { NotificationController(get()) } bind ApiController::class
    single { NotificationsService(get()) }
}