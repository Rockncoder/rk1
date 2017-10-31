package com.tekadept.rk1

import ratpack.server.RatpackServer

object Main {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println("hola kimosabi")
        RatpackServer.start { spec ->
            spec.serverConfig {c -> c.port(getAssignedPort())}
            spec.handlers { chain ->
                chain.get { ctx -> ctx.render("Hello, BoscoX!") }
            }
        }
    }

    fun getAssignedPort(): Int {
        val processBuilder = ProcessBuilder()
        val envPort = processBuilder.environment()["PORT"];
        return envPort?.toInt() ?: DEFAULT_PORT
    }
}