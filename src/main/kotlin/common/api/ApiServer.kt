package common.api

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.defaultheaders.DefaultHeaders
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json
import common.createLogger

private val logger = createLogger {}

private const val LISTEN_PORT = 8080

class ApiServer(
    private val controllers: List<ApiController>
) {
    private var server: ApplicationEngine? = null

    /**
     * Starts the HTTP server
     */
    fun start() {
        if (server != null) {
            return
        }

        logger.info { "Starting HTTP API on port $LISTEN_PORT" }
        server = embeddedServer(Netty, port = LISTEN_PORT) {
            configure()
        }.start(wait = true)
    }

    fun stop() {
        server?.stop(300, 1000)
        server = null
    }

    private fun Application.configure() {
        install(DefaultHeaders)
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        installExceptionHandling()
        install(Resources)
        routing {
            for (controller in controllers) {
                with(controller) {
                    registerApi()
                }
            }
        }
    }
}