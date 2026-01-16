package com.personal.blog.post

import org.springframework.stereotype.Service

@Service
class PostService(val repo: PostRepository) {

    fun create(title: String, content: String): Post =
        repo.save(Post(title = title, content = content))

    fun retrieve(): List<Post> = repo.findAll()

    fun update(post: Post): Post {
        val existingPost = repo.findById(post.id)
            .orElseThrow { NoSuchElementException("Post not found") }

        existingPost.title = post.title
        existingPost.content = post.content

        return repo.save(existingPost)
    }

    fun delete(id: Long) {
        val existingPost = repo.findById(id)
            .orElseThrow { NoSuchElementException("Post not found") }

        repo.delete(existingPost)
    }
}