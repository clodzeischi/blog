package com.personal.blog.post

import com.personal.blog.comment.Comment
import com.personal.blog.comment.CommentRepository
import com.personal.blog.comment.CommentService
import com.personal.blog.comment.CreateCommentRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.*

class CommentServiceTest {

    private val repo: CommentRepository = mock()
    private val postService: PostService = mock()
    private val service = CommentService(repo, postService)

    val req = CreateCommentRequest(1L, "Hello")
    val testComment = Comment(
        parentPost = Post(1L, "Test", "Post"),
        id = 1L,
        text = "Hello"
    )

    @Test
    fun `should save and return new comment`() {
        whenever(postService.retrieve(any()))
            .thenReturn(Post(1L, "Hello", "World"))
        whenever(repo.save(any())).thenReturn(testComment)

        val result = service.create(req)

        assertEquals(1L, result.id)
        assertEquals("Hello", result.text)

        verify(repo).save(any())
    }

    @Test
    fun `should return all comments on post`() {
        val posts = listOf(testComment)

        whenever(repo.findAllByParentPost_Id(any())).thenReturn(posts)

        val result = service.retrieveByPostID(1L)

        assertEquals(1, result.size)

        verify(repo).findAllByParentPost_Id(any())
    }

    @Test
    fun `should return comment by id`() {
        whenever(repo.findById(any())).thenReturn(Optional.of(testComment))

        val result = service.retrieveByID(1L)

        assertEquals("Hello", result.text)
        verify(repo).findById(any())
    }

    @Test
    fun `should throw if not found on retrieve`() {
        whenever(repo.findById(any())).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            service.retrieveByID(99L)
        }

        verify(repo).findById(any())
    }

    @Test
    fun `should delete comment`() {
        whenever(repo.findById(any())).thenReturn(Optional.of(testComment))

        service.delete(1L)

        verify(repo).findById(any())
        verify(repo).delete(testComment)
    }

    @Test
    fun `should throw if not found on delete`() {
        whenever(repo.findById(any())).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            service.delete(1L)
        }

        verify(repo).findById(any())
        verify(repo, never()).delete(any())
    }
}
