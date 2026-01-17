package com.personal.blog.post

import org.springframework.stereotype.Service

@Service
class PostService(val repo: PostRepository) {

    fun create(post: CreatePostRequest): Post =
        repo.save(Post(
            title = post.title,
            content = post.content))

    fun retrieve(): List<Post> = repo.findAll()

    fun retrieve(id: Long): Post {
        val myPost = repo.findById(id)
            .orElseThrow { NoSuchElementException("Post not found") }

        return myPost
    }

    fun update(post: UpdatePostRequest): Post {
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