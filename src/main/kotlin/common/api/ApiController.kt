package common.api

import io.ktor.server.routing.Route

interface ApiController {
    fun Route.registerApi()
}
