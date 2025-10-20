package swf.army.mil.blog.post;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    @Test
    void shouldSaveNewPost() {
        Post testPost = new Post(1L, LocalDateTime.of(
                2025, 10, 20, 15, 20),
                "Author", "Subject", "Content", 5, null);

        when(postService.save(testPost)).thenReturn(testPost);

        Post actualPost = postService.save(testPost);
        verify(postRepository, times(1)).save(any(Post.class));
        assertThat(testPost).isEqualTo(actualPost);
    }

    @Test
    void shouldFindPostByID() {
        Long myID = 1L;

        Post testPost = new Post(myID, LocalDateTime.of(
                2025, 10, 20, 15, 20),
                "Author", "Subject", "Content", 5, null);

        when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));

        Post actualPost = postService.findByID(myID);
        verify(postRepository, times(1)).findById(any(Long.class));
        assertThat(testPost).isEqualTo(actualPost);
    }

    @Test
    void shouldNotFindIfDoesntExist() {
        Long missingID = 1L;

        when(postRepository.findById(missingID)).thenReturn(Optional.empty());

        verify(postRepository, never()).findById(missingID);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            postService.findByID(missingID);
        });

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void shouldFindAllPosts() {
        List<Post> testPosts = List.of(new Post(
            1L, LocalDateTime.of(2020, 10, 10, 10, 10),
                "Author", "Subject 1", "Content 1", 5, null
        ), new Post(
                2L, LocalDateTime.of(2020, 10, 10, 10, 11),
                "Author", "Subject 2", "Content 2", 6, null
        ));

        when(postRepository.findAll()).thenReturn(testPosts);

        List<Post> actualPosts = postService.findAll();
        verify(postRepository, times(1)).findAll();
        assertThat(testPosts).isEqualTo(actualPosts);
    }

    @Test
    void shouldDeletePostByID() {
        Long myID = 1L;

        Post testPost = new Post(myID, LocalDateTime.of(
                2025, 10, 20, 15, 20),
                "Author", "Subject", "Content", 5, null);

        when(postRepository.findById(myID)).thenReturn(Optional.of(testPost));

        postService.delete(myID);

        verify(postRepository).delete(testPost);
    }

    @Test
    void shouldNotDeleteIfDoesntExist() {
         Long missingID = 1L;

         when(postRepository.findById(missingID)).thenReturn(Optional.empty());

         verify(postRepository, never()).delete(any());

         assertThrows(EntityNotFoundException.class, () -> postService.delete(missingID));
    }

    // Should edit post
    @Test
    void shouldEditPostByID() {
        Long myID = 1L;

        Post post = new Post(myID, LocalDateTime.of(
                2025, 10, 20, 15, 20),
                "Author", "Subject", "Content", 5, null);

        String updatedContent = "Updated content";

        when(postRepository.findById(myID)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Post result = postService.updateContent(myID, updatedContent);

        verify(postRepository, times(1)).save(any(Post.class));
        assertEquals(updatedContent, result.getContent());
    }

    @Test
    void shouldNotEditIfDoesntExist() {
        Long missingID = 1L;
        String updatedContent = "this is updated content";

        when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> {
                    postService.updateContent(missingID, updatedContent);
                });

        verify(postRepository, never()).save(any());
        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void shouldLikeByID() {
        Long myID = 1L;
        int likes = 10;

        Post post = new Post(myID, LocalDateTime.of(
                2025, 10, 20, 15, 20),
                "Author", "Subject", "Content", likes, null);

        when(postRepository.findById(myID)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Post result = postService.like(myID);

        verify(postRepository, times(1)).save(any(Post.class));
        assertEquals(likes + 1, result.getLikes());
    }

    @Test
    void shouldNotLikeIfDoesntExist() {
        Long missingID = 1L;

        when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> {
                    postService.like(missingID);
                });

        verify(postRepository, never()).save(any());
        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void shouldDisikeByID() {
        Long myID = 1L;
        int likes = 10;

        Post post = new Post(myID, LocalDateTime.of(
                2025, 10, 20, 15, 20),
                "Author", "Subject", "Content", likes, null);

        when(postRepository.findById(myID)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Post result = postService.dislike(myID);

        verify(postRepository, times(1)).save(any(Post.class));
        assertEquals(likes - 1, result.getLikes());
    }

    @Test
    void shouldNotDislikeIfDoesntExist() {
        Long missingID = 1L;

        when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class, () -> {
                    postService.dislike(missingID);
                });

        verify(postRepository, never()).save(any());
        assertEquals("Post not found", exception.getMessage());
    }
}
