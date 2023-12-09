package org.booruchan.extensions.generate.logger

import io.github.oshai.kotlinlogging.KotlinLogging

class LoggerImpl : Logger {

    private val logger = KotlinLogging.logger { }

    override fun error(string: () -> String) {
        logger.error(string)
    }

    override fun debug(string: () -> String) {
        logger.debug(string)
    }

    override fun info(string: () -> String) {
        logger.info(string)
    }
}