package com.personal.blog.post

import com.personal.blog.comment.CommentService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.*

class PostServiceTest {

    private val repo: PostRepository = mock()
    private val commentService: CommentService = mock()
    private val service = PostService(repo, commentService)

    val req = CreatePostRequest("Hello", "World")
    val reqUpdate = UpdatePostRequest(1L, "New", "Content")
    val testPost = Post(id = 1L, title = "Hello", content = "World")

    @Test
    fun `should save and return new post`() {
        whenever(repo.save(any())).thenReturn(testPost)

        val result = service.create(req)

        assertEquals(1L, result.id)
        assertEquals("Hello", result.title)
        assertEquals("World", result.content)

        verify(repo).save(any())
    }

    @Test
    fun `should return all posts`() {
        val posts = listOf(testPost)

        whenever(repo.findAll()).thenReturn(posts)

        val result = service.retrieve()

        assertEquals(1, result.size)
        verify(repo).findAll()
    }

    @Test
    fun `should return post by id`() {
        whenever(repo.findById(1L)).thenReturn(Optional.of(testPost))

        val result = service.retrieve(1L)

        assertEquals("Hello", result.title)
        verify(repo).findById(1L)
    }

    @Test
    fun `should throw if not found on retrieve`() {
        whenever(repo.findById(any())).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            service.retrieve(99L)
        }

        verify(repo).findById(any())
    }

    @Test
    fun `should modify and save existing post`() {
        whenever(repo.findById(any())).thenReturn(Optional.of(testPost))
        whenever(repo.save(any())).thenReturn(testPost)

        val result = service.update(reqUpdate)

        assertEquals("New", result.title)
        assertEquals("Content", result.content)

        verify(repo).findById(any())
        verify(repo).save(testPost)
    }

    @Test
    fun `should throw if not found on update`() {
        whenever(repo.findById(any())).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            service.update(reqUpdate)
        }

        verify(repo).findById(any())
        verify(repo, never()).save(any())
    }

    @Test
    fun `should delete post`() {
        whenever(repo.findById(any())).thenReturn(Optional.of(testPost))

        service.delete(1L)

        verify(repo).findById(any())
        verify(repo).delete(testPost)
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
