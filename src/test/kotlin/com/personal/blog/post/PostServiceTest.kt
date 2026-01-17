package com.personal.blog.post

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.*

class PostServiceTest {

    private val repo: PostRepository = mock()
    private val service = PostService(repo)

    @Test
    fun `should save and return new post`() {
        val req = CreatePostRequest("Hello", "World")
        val saved = Post(id = 1L, title = "Hello", content = "World")

        whenever(repo.save(any())).thenReturn(saved)

        val result = service.create(req)

        assertEquals(1L, result.id)
        assertEquals("Hello", result.title)
        assertEquals("World", result.content)

        verify(repo).save(any())
    }

    @Test
    fun `should return all posts`() {
        val posts = listOf(
            Post(1L, "A", "B"),
            Post(2L, "C", "D")
        )

        whenever(repo.findAll()).thenReturn(posts)

        val result = service.retrieve()

        assertEquals(2, result.size)
        verify(repo).findAll()
    }

    @Test
    fun `should return post by id`() {
        val post = Post(1L, "Hello", "World")

        whenever(repo.findById(1L)).thenReturn(Optional.of(post))

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
        val existing = Post(1L, "Old", "Post")
        val req = UpdatePostRequest(id = 1L, title = "New", content = "Content")

        whenever(repo.findById(any())).thenReturn(Optional.of(existing))
        whenever(repo.save(existing)).thenReturn(existing)

        val result = service.update(req)

        assertEquals("New", result.title)
        assertEquals("Content", result.content)

        verify(repo).findById(any())
        verify(repo).save(existing)
    }

    @Test
    fun `should throw if not found on update`() {
        val req = UpdatePostRequest(id = 1L, title = "X", content = "Y")

        whenever(repo.findById(any())).thenReturn(Optional.empty())

        assertThrows(NoSuchElementException::class.java) {
            service.update(req)
        }

        verify(repo).findById(any())
        verify(repo, never()).save(any())
    }

    @Test
    fun `should delete post`() {
        val existing = Post(1L, "Hello", "World")

        whenever(repo.findById(any())).thenReturn(Optional.of(existing))

        service.delete(1L)

        verify(repo).findById(any())
        verify(repo).delete(existing)
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
