package com.tekadept.rk1

import ratpack.handling.Context
import ratpack.server.RatpackServer.start

object Main {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        start { spec ->
            // We need to get the port since Heroku will assign one to our app
            spec.serverConfig { config -> config.port(getAssignedPort()) }
            spec.handlers { chain ->
                chain
                        .path("fizz", ::fizzHandler)
                        .get("buzz", ::buzzHandler)
                        .get("users") { ctx -> ctx.render("Hello, User Kotlin") }
                        // all of the endpoints which begin with "vehicles"
                        .prefix("vehicles", ::vehicleHandler)
                        // alias cars to vehicles as well
                        .prefix("cars", ::vehicleHandler)
                        // here when no matches found
                        .get() { ctx -> ctx.render("Hello KotlinConf 404") }
            }
        }
    }
}

// all of the methods for the "fizz" path are handled here
fun fizzHandler(context: Context) {
    context.byMethod { t ->
        t
                .get { context.render("GET FIZZ") }
                .post { context.render("POST FIZZ") }
                .patch { context.render("PATCH FIZZ") }
                .put { context.render("PATCH FIZZ") }
                .options { context.render("OPTIONS FIZZ") }
                .delete { context.render("DELETE FIZZ") }
    }
}

fun buzzHandler(context: Context) {
    context.render("from the baz handler")
}

fun getAssignedPort() = ProcessBuilder().environment()["PORT"]?.toInt() ?: DEFAULT_PORT




