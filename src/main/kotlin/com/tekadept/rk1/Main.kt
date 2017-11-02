package com.tekadept.rk1

import ratpack.handling.Context
import ratpack.server.RatpackServer.start


object Main {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        start { spec ->
            spec.serverConfig { config -> config.port(getAssignedPort()) }
            spec.handlers { chain ->
                chain.
                        get("baz", ::bazHandler).
                        get("foo", ::fooHandler).
                        get("users") { ctx -> ctx.render("Hello, User Bosco") }.
                        prefix("vehicles", ::vehicleHandler).
                        prefix("cars", ::vehicleHandler).
                        get { ctx -> ctx.render("Hello, Bosco") }
            }
        }
    }
}

fun bazHandler(context: Context) {
    context.render("from the baz handler")
}

fun fooHandler(context: Context) {
    context.response.headers.add("Custom-Header", "custom-header-value")
    context.response.status(400)
    context.render("from the foo handler")
}

fun getAssignedPort(): Int {
    val pb = ProcessBuilder()
    val bub = pb.environment()["PORT"]?.toInt()?: DEFAULT_PORT
    println("Assigned port = $bub")

    val envPort = pb.environment()["PORT"]
    return envPort?.toInt() ?: DEFAULT_PORT
}


