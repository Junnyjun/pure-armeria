package io.github.armeria

import com.linecorp.armeria.common.HttpRequest
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.ServiceRequestContext
import io.github.armeria.post.BlogService
import org.slf4j.Logger
import org.slf4j.LoggerFactory


private val logger: Logger = LoggerFactory.getLogger(Main::class.java)

object Main {

    fun newServer(port: Int): Server = Server.builder().http(port)
        .service("/") { _: ServiceRequestContext?, _: HttpRequest? -> HttpResponse.of("Hello, Armeria!") }
        .annotatedService(BlogService())
        .build()
}

fun main() {
    Main.newServer(8080)
        .also {
            it.closeOnJvmShutdown();
            it.start().join()
            logger.info("Server started at {}", it.activeLocalPort())
        }
}