package com.personal.blog.post

import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    lateinit var service: PostService

    val endpoint = "/api/posts"

    val json_req = """
        {"title": "Hello", "content": "World"}
    """

    val responsePost = Post(1L, "Hello", "World")

    @Test
    fun `should return empty list of posts`() {
        whenever(service.retrieve()).thenReturn(emptyList<Post>())

        mockMvc.perform(get(endpoint))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"))
    }

    @Test
    fun `should return a list of two`() {
        whenever(service.retrieve()).thenReturn(listOf<Post>(
            Post(1, "hello", "world"),
            Post(2, "fu", "bar")
        ))

        mockMvc.perform(get(endpoint))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
    }

    @Test
    fun `should create a post`() {
        whenever(service.create(any(), any())).thenReturn(responsePost)

        mockMvc.perform(
            post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json_req))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value("Hello"))
            .andExpect(jsonPath("$.content").value("World"))
    }

    @Test
    fun `should not edit post if not found`() {
        whenever(service.update(any())).thenThrow(NoSuchElementException())

        mockMvc.perform(
            patch(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json_req))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should edit post`() {
        whenever(service.update(any())).thenReturn(responsePost)

        mockMvc.perform(
            patch(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json_req))
            .andExpect(status().isAccepted)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value("Hello"))
            .andExpect(jsonPath("$.content").value("World"))
    }

    @Test
    fun `should not delete if not found`() {
        whenever(service.delete(any())).thenThrow(NoSuchElementException())

        mockMvc.perform(
            delete("$endpoint/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should delete post`() {

        mockMvc.perform(
            delete("$endpoint/1"))
            .andExpect(status().isNoContent)

        verify(service).delete(any())
    }
}