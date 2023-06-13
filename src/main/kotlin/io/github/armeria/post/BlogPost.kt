package io.github.armeria.post

import java.util.concurrent.ConcurrentHashMap




data class BlogPost(
    val id: Long = 0,
    val title: String? = null,
    val content: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val modifiedAt: Long = 0,
)


class BlogService {
    private val blogPosts: Map<Int, BlogPost> = ConcurrentHashMap()
}