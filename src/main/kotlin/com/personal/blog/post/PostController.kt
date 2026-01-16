package com.personal.blog.post

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api/posts")
class PostController(val service: PostService) {

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody req: CreatePostRequest): PostDTO {
        val newPost = service.create(req.title, req.content)
        return PostDTO(
            newPost.id, 
            newPost.title, 
            newPost.content, 
            newPost.created, 
            newPost.updated)
    }

    @GetMapping()
    fun getAll(): List<PostDTO> = service.retrieve().map {
        PostDTO(
            it.id, 
            it.title, 
            it.content, 
            it.created, 
            it.updated)
    }

    @PatchMapping
    fun patch(@RequestBody req: Post): ResponseEntity<PostDTO> {
        return try {
            val updatedPost = service.update(req)
            val response = PostDTO(
                updatedPost.id, 
                updatedPost.title, 
                updatedPost.content,
                updatedPost.created,
                updatedPost.updated)
            ResponseEntity.accepted().body(response)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            service.delete(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }
}

data class PostDTO(
    val id: Long, 
    val title: String, 
    val content: String,
    val created: Instant,
    val updated: Instant)
data class CreatePostRequest(val title: String, val content: String)