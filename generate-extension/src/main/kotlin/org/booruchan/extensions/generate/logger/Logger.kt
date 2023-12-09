package org.booruchan.extensions.generate.logger

interface Logger {
    fun info(string: () -> String)

    fun debug(string: () -> String)

    fun error(string: () -> String)
}
