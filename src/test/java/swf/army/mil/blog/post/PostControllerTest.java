package swf.army.mil.blog.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    private final String apiEndPoint = "/api/posts";

    @Test
    void shouldCreateNewPost() throws Exception {
        Post testPost = Post.builder()
                .id(1L)
                .author("author")
                .subject("subject")
                .comments(List.of())
                .build();

        String testPostJSON = mapper.writeValueAsString(testPost);

        when(postService.save(any(Post.class))).thenReturn(testPost);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(apiEndPoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testPostJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("author"));

        verify(postService).save(any(Post.class));
    }

    @Test
    void shouldFindPostByID() throws Exception{
        Long myID = 1L;

        Post testPost = new Post(myID, null, "Author",
                "Subject", "Content", 5, null);
        when(postService.findByID(myID)).thenReturn(testPost);

        mockMvc.perform(get(apiEndPoint + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
        verify(postService).findByID(myID);
    }

    @Test
    void shouldGetAllPosts() throws Exception {
        List<Post> testPosts = List.of(new Post(
                1L, null, "Author", "Subject 1",
                "Content 1", 5, null
        ), new Post(
                2L, null,  "Author", "Subject 2",
                "Content 2", 6, null
        ));

        when(postService.findAll()).thenReturn(testPosts);

        mockMvc.perform(get(apiEndPoint))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(postService).findAll();
    }

    @Test
    void shouldNotFindIfDoesntExist() throws Exception {
        Long missingID = 1L;

        when(postService.findByID(missingID))
                .thenThrow(new EntityNotFoundException("Post not found"));

        mockMvc.perform(get(apiEndPoint + "/1", missingID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Post not found"));
    }

    @Test
    void shouldDeletePostByID() throws Exception {
        Long myID = 1L;

        doNothing().when(postService).delete(myID);

        mockMvc.perform(delete(apiEndPoint + "/1"))
                        .andExpect(status().isNoContent());

        verify(postService).delete(myID);
    }

    @Test
    void shouldNotDeleteIfDoesntExist() throws Exception{
        Long missingID = 1L;

        doThrow(new EntityNotFoundException("Post not found"))
                .when(postService).delete(missingID);

        mockMvc.perform(delete(apiEndPoint + "/1", missingID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Post not found"));
    }

    @Test
    void shouldEditPostByID() throws Exception {
        Long myID = 1L;

        Post testPost = Post.builder()
                .id(myID)
                .author("author")
                .subject("subject")
                .content("updated content")
                .comments(List.of())
                .build();

        String jsonPayload = """
        {
          "content": "updated content"
        }
        """;

        when(postService.updateContent(myID, "updated content")).thenReturn(testPost);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(apiEndPoint + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("updated content"));

        verify(postService).updateContent(myID, "updated content");
    }

    @Test
    void shouldNotEditIfDoesntExist() throws Exception {
        Long missingID = 1L;

        // Simulate service throwing EntityNotFoundException
        when(postService.updateContent(missingID, "updated content"))
                .thenThrow(new EntityNotFoundException("Post not found"));

        String jsonPayload = """
        {
          "content": "updated content"
        }
        """;

        mockMvc.perform(MockMvcRequestBuilders
                        .patch(apiEndPoint + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Post not found"));
    }

    @Test
    void shouldLikePostByID() throws Exception {
        Post testPost = Post.builder()
                .id(1L)
                .author("author")
                .subject("subject")
                .comments(List.of())
                .likes(5)
                .build();

        when(postService.like(1L)).thenReturn(testPost);

        mockMvc.perform(post(apiEndPoint + "/1/like", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.likes").value(5));

        verify(postService).like(1L);
    }

    @Test
    void shouldNotLikeIfDoesntExist() throws Exception {
        Long missingID = 1L;

        when(postService.like(missingID))
                .thenThrow(new EntityNotFoundException("Post not found"));

        mockMvc.perform(post(apiEndPoint + "/1/like", missingID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Post not found"));

        verify(postService).like(missingID);
    }
}
