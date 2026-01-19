package com.personal.blog.comment

import com.personal.blog.post.Post
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.Instant

@WebMvcTest(CommentController::class)
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(properties = [
    "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://fake"
])
class CommentControllerTest(@Autowired private val mockMvc: MockMvc) {

    @MockBean
    lateinit var service: CommentService

    val endpoint = "/api/comments"

    val json_req = """
        {"post": {"id": "1"}, "text": "World"}
    """

    val testPost = Post(1L, "Hello", "World")
    val responseComment = Comment(1L, testPost, "Hello", Instant.now())

    @Test
    fun `should return empty list of comments`() {
        whenever(service.retrieveByPostID(any())).thenReturn(emptyList())

        mockMvc.perform(get("$endpoint/bypost/1"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"))
    }

    @Test
    fun `should return a list of two`() {
        whenever(service.retrieveByPostID(any())).thenReturn(listOf(
            Comment(1, testPost, "foo"),
            Comment(2, testPost, "bar")
        ))

        mockMvc.perform(get("$endpoint/bypost/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
    }

    @Test
    fun `should find comment by ID`() {
        whenever(service.retrieveByID(any())).thenReturn(responseComment)

        mockMvc.perform(get("$endpoint/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.text").value("Hello"))
    }

    @Test
    fun `should not return if not found`() {
        whenever(service.retrieveByID(any())).thenThrow(NoSuchElementException())

        mockMvc.perform(get("$endpoint/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should create a post`() {
        whenever(service.create(any())).thenReturn(responseComment)

        mockMvc.perform(
            post(endpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json_req))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.text").value("Hello"))
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

    @Test
    fun `should delete all comments by post ID`() {
        mockMvc.perform(
            delete("$endpoint/bypost/1")
        )
            .andExpect(status().isNoContent)

        verify(service).deleteByPostID(any())
    }
}