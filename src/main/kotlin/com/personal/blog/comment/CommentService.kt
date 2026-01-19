package com.personal.blog.comment

import com.personal.blog.post.PostService
import org.springframework.stereotype.Service

@Service
class CommentService(val repo: CommentRepository, val postService: PostService) {

    fun create(comment: CreateCommentRequest): Comment {
        val originalPost = postService.retrieve(comment.parent)

        return repo.save(Comment(
            parentPost = originalPost,
            text = comment.text))
    }

    fun retrieveByID(id: Long): Comment {
        val myComment = repo.findById(id)
            .orElseThrow { NoSuchElementException("Comment not found") }

        return myComment
    }

    fun retrieveByPostID(id: Long): List<Comment> = repo.findAllByParentPost_Id(id)

    fun delete(id: Long) {
        val existingComment = repo.findById(id)
            .orElseThrow { NoSuchElementException("Comment not found") }

        repo.delete(existingComment)
    }

    fun deleteByPostID(id: Long) = repo.deleteAllByParentPost_Id(id)
}