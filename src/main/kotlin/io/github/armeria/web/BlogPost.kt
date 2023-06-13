package io.github.armeria.web

import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.common.HttpStatus
import com.linecorp.armeria.server.annotation.*
import java.util.concurrent.ConcurrentHashMap


class BlogService {
    private val blogPosts: Map<Int, BlogPost> = ConcurrentHashMap()

    @Post("/blogs")
    fun create(blogPost: BlogPost): HttpResponse = HttpResponse.ofJson(blogPost)

    @Get("/blogs")
    fun gets(): MutableList<BlogPost> = blogPosts.entries
        .stream()
        .map { it.value }
        .toList()

    @Get("/blogs/:id")
    fun get(@Param id: Int): HttpResponse = blogPosts[id]
        ?.let { HttpResponse.ofJson(it) }
        ?: throw IllegalArgumentException("No blog post found with id: $id")

    @Blocking
    @Put("/blogs/:id")
    fun update(@Param("id") id: Int, @RequestObject target: BlogPost): HttpResponse = blogPosts[id]
            ?.also { it -> it.update(target) }
            ?.let { HttpResponse.ofJson(it) }
            ?: HttpResponse.of(HttpStatus.NOT_FOUND);
}

data class BlogPost(
    val id: Long = 0,
    val title: String? = null,
    val content: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long = 0,
) {
    fun update(target: BlogPost) = copy(
        title = target.title ?: title,
        content = target.content ?: content,
        modifiedAt = System.currentTimeMillis()
    )
}