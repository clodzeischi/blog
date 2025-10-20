package swf.army.mil.blog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateNewPost() throws Exception {

        Post testPost = Post.builder()
                .id(1L)
                .author("author")
                .subject("subject")
                .comments(List.of())
                .build();

        String testPostJSON = mapper.writeValueAsString(testPost);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testPostJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("author"));

        verify(postService).save(any(Post.class));
    }

    @Test
    void shouldFindPostByID() {

    }

    @Test
    void shouldNotFindIfDoesntExist() {

    }

    @Test
    void shouldFindAllPosts() {

    }

    @Test
    void shouldDeletePostByID() {

    }

    @Test
    void shouldNotDeleteIfDoesntExist() {

    }

    @Test
    void shouldEditPostByID() {

    }

    @Test
    void shouldNotEditIfDoesntExist() {

    }

    @Test
    void shouldLikePostByID() {

    }

    @Test
    void shouldNotLikeIfDoesntExist() {

    }

    @Test
    void shouldDislikePostByID() {

    }

    @Test
    void shouldNotDislikeIfDoesntExist() {

    }
}
