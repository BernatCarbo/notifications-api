package common

import mu.KotlinLogging

fun createLogger(context: () -> Unit) = KotlinLogging.logger(context)