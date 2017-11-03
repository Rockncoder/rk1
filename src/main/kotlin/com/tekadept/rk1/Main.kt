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
                        path("fizz", ::fizzHandler).
//                        get("fizz", ::fizzHandler).
//                        post("fizz", { ctx -> ctx.render("Post Fizz")}).
                        get("buzz", ::buzzHandler).
                        get("users") { ctx -> ctx.render("Hello, User Bosco") }.
                        prefix("vehicles", ::vehicleHandler).
                        prefix("cars", ::vehicleHandler).
                        get() { ctx -> ctx.render("Hello, Bosco") }
            }
        }
    }
}

fun fizzHandler(context: Context) {
    context.byMethod { t ->
        t.
                get { context.render("GET FIZZ") }.
                post { context.render("POST FIZZ") }.
                patch { context.render("PATCH FIZZ") }.
                put { context.render("PATCH FIZZ") }.
                options { context.render("OPTIONS FIZZ") }.
                delete { context.render("DELETE FIZZ") }
    }
}

fun buzzHandler(context: Context) {
    context.render("from the baz handler")
}

fun getAssignedPort(): Int {
    val port = ProcessBuilder().environment()["PORT"]?.toInt() ?: DEFAULT_PORT
    println("PORT = $port")
    return port
//    return ProcessBuilder().environment()["PORT"]?.toInt() ?: DEFAULT_PORT
}



