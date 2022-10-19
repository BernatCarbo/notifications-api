@file:JvmName("NotificationsApi")

package notifications

import common.api.ApiServer
import common.api.apiModule
import common.createLogger
import common.persistence.persistenceModule
import org.koin.core.context.startKoin

private val logger = createLogger {}

fun main() {
    // Start the dependency injection
    val koin = startKoin {
        modules(
            listOf(
                persistenceModule,
                apiModule,
                notificationModule
            )
        )
    }.koin

    val apiServer: ApiServer = koin.get()
    try {
        apiServer.start()
    } catch (exception: Exception) {
        apiServer.stop()
        logger.error(exception) { "There was an exception during startup" }
    }
    logger.info { "Exiting..." }
}