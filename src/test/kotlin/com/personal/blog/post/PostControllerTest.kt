package com.personal.blog.post

import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(PostController::class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = [
    "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://fake"
])
class PostControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    lateinit var service: PostService

    val endpoint = "/api/posts"

    val json_req = """
        {"title": "Hello", "content": "World"}
    """

    val responsePost = Post(1L, "Hello", "World", Instant.now(), Instant.now())

    @Test
    fun `should return empty list of posts`() {
        whenever(service.retrieve()).thenReturn(emptyList<Post>())

        mockMvc.perform(MockMvcRequestBuilders.get(endpoint))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"))
    }

    @Test
    fun `should return a list of two`() {
        whenever(service.retrieve()).thenReturn(listOf<Post>(
            Post(1, "hello", "world", Instant.now(), Instant.now()),
            Post(2, "fu", "bar", Instant.now(), Instant.now())
        ))

        mockMvc.perform(get(endpoint))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
    }

    @Test
    fun `should find post by ID`() {
        whenever(service.retrieve(any())).thenReturn(responsePost)

        mockMvc.perform(get("$endpoint/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Hello"))
    }

    @Test
    fun `should not return if not found`() {
        whenever(service.retrieve(any())).thenThrow(NoSuchElementException())

        mockMvc.perform(get("$endpoint/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should create a post`() {
        whenever(service.create(any())).thenReturn(responsePost)

        mockMvc.perform(
            MockMvcRequestBuilders.post(endpoint)
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
            MockMvcRequestBuilders.patch(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json_req))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should edit post`() {
        whenever(service.update(any())).thenReturn(responsePost)

        mockMvc.perform(
            MockMvcRequestBuilders.patch(endpoint)
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
            MockMvcRequestBuilders.delete("$endpoint/1")
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should delete post`() {
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$endpoint/1")
        )
            .andExpect(status().isNoContent)

        verify(service).delete(any())
    }
}