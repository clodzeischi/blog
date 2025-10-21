package swf.army.mil.blog.comment;

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
import swf.army.mil.blog.post.PostService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper mapper;

    private final String apiEndPoint = "/api/comments";

    @Test
    void shouldCreateNewComment() throws Exception {
        Comment testComment = Comment.builder()
                .id(1L)
                .post(null)
                .author("author")
                .content("content")
                .build();

        String testCommentJSON = mapper.writeValueAsString(testComment);

        when(commentService.save(any(Comment.class))).thenReturn(testComment);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(apiEndPoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testCommentJSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value("author"));

        verify(commentService).save(any(Comment.class));
    }

    @Test
    void shouldFindCommentByID() throws Exception{
        Long myID = 1L;

        Comment testComment = Comment.builder()
                .id(1L)
                .post(null)
                .author("author")
                .content("content")
                .build();

        when(commentService.findByID(myID)).thenReturn(testComment);

        mockMvc.perform(get(apiEndPoint + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
        verify(commentService).findByID(myID);
    }

    @Test
    void shouldFindCommentsByPost() throws Exception {
        List<Comment> testPosts = List.of(
                Comment.builder()
                        .id(1L)
                        .post(null)
                        .author("author 1")
                        .content("content 1")
                        .build(),
                Comment.builder()
                        .id(2L)
                        .post(null)
                        .author("author 2")
                        .content("content 2")
                        .build()
        );

        when(commentService.findByPostID(1L)).thenReturn(testPosts);

        mockMvc.perform(get(apiEndPoint + "/bypost/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)));

        verify(commentService).findByPostID(1L);
    }

    @Test
    void shouldNotFindIfDoesntExist() throws Exception {
        Long missingID = 1L;

        when(commentService.findByID(missingID))
                .thenThrow(new EntityNotFoundException("Comment not found"));

        mockMvc.perform(get(apiEndPoint + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Comment not found"));
    }

    @Test
    void shouldDeleteCommentByID() throws Exception {
        Long myID = 1L;

        doNothing().when(commentService).delete(myID);

        mockMvc.perform(delete(apiEndPoint + "/1"))
                .andExpect(status().isNoContent());

        verify(commentService).delete(myID);
    }

    @Test
    void shouldNotDeleteIfDoesntExist() throws Exception {
        Long missingID = 1L;

        doThrow(new EntityNotFoundException("Comment not found"))
                .when(commentService).delete(missingID);

        mockMvc.perform(delete(apiEndPoint + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Comment not found"));
    }
}
