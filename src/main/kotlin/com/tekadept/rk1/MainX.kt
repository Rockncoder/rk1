package com.tekadept.rk1

import ratpack.server.RatpackServer.start


object MainX {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        start { server ->
            server.handlers { chain ->
                chain
                        .get { ctx -> ctx.render("Hello World!") }
                        .get(":name") { ctx -> ctx.render("Hello " + ctx.pathTokens["name"] + "!") }
            }
        }
    }
}



