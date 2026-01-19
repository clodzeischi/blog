package com.personal.blog.comment

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/api/comments")
class CommentController(val service: CommentService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@RequestBody req: CreateCommentRequest): CommentDTO {
        val newComment = service.create(req)
        return CommentDTO(
            newComment.id,
            newComment.parentPost.id,
            newComment.text,
            newComment.created
        )
    }

    @GetMapping("/{id}")
    fun getByID(@PathVariable id: Long): ResponseEntity<CommentDTO> {
        return try {
            val myComment = service.retrieveByID(id)
            val response = CommentDTO(
                myComment.id,
                myComment.parentPost.id,
                myComment.text,
                myComment.created)
            ResponseEntity.ok().body(response)
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/bypost/{id}")
    fun getAllByPostID(@PathVariable id: Long): ResponseEntity<List<CommentDTO>> {
        return ResponseEntity<List<CommentDTO>>(service.retrieveByPostID(id).map{
            CommentDTO(
                it.id,
                it.parentPost.id,
                it.text,
                it.created)
        }, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteByID(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            service.delete(id)
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/bypost/{id}")
    fun deleteByPostID(@PathVariable id: Long): ResponseEntity<Void> {
        service.deleteByPostID(id)
        return ResponseEntity.noContent().build()
    }
}

data class CommentDTO(
    val id: Long,
    val parent: Long,
    val text: String,
    val created: Instant)

data class CreateCommentRequest(
    val parent: Long,
    val text: String
)