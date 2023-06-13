package io.github.armeria

import com.linecorp.armeria.server.Server
import com.linecorp.armeria.server.docs.DocService
import io.github.armeria.web.BlogService
import org.slf4j.Logger
import org.slf4j.LoggerFactory


private val logger: Logger = LoggerFactory.getLogger(Runner::class.java)

object Runner {
    fun newServer(port: Int): Server = Server.builder().http(port)
        .annotatedService(BlogService())
        .serviceUnder("/docs", RunnerDoc.docService)
        .build()
}
object RunnerDoc {
    val docService: DocService = DocService.builder()
        .exampleRequests(BlogService::class.java, "create", "{\"title\":\"My first blog\", \"content\":\"Hello Armeria!\"}")


        .build()
}

fun main() {
    Runner.newServer(8080)
        .also {
            it.closeOnJvmShutdown();
            it.start().join()
            logger.info("Server started at {}", it.activeLocalPort())
        }
}