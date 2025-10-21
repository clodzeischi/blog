package swf.army.mil.blog.comment;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    void shouldSaveNewComment() {
        Comment testComment = new Comment(1L, null, null,
                "Author", "Content");

        when(commentRepository.save(testComment)).thenReturn(testComment);

        Comment actualComment = commentRepository.save(testComment);
        verify(commentRepository, times(1)).save(any(Comment.class));
        assertThat(testComment).isEqualTo(actualComment);
    }

    @Test
    void shouldFindCommentByID() {
        Long myID = 1L;

        Comment testComment = new Comment(myID, null, null,
                "Author", "Content");

        when(commentRepository.findById(myID)).thenReturn(Optional.of(testComment));

        Comment actualComment = commentService.findByID(myID);
        verify(commentRepository, times(1)).findById(any(Long.class));
        assertThat(testComment).isEqualTo(actualComment);
    }

    @Test
    void shouldNotFindIfDoesntExist() {
        Long missingID = 1L;

        when(commentRepository.findById(missingID)).thenReturn(Optional.empty());

        verify(commentRepository, never()).findById(missingID);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            commentService.findByID(missingID);
        });

        assertEquals("Comment not found", exception.getMessage());
    }

    @Test
    void shouldFindCommentsByPostID() {
        Long postID = 1L;

        List<Comment> testComments = List.of(new Comment(1L, null, null,
                "Author 1", "Content 1"),
                new Comment(2L, null, null,
                        "Author 2", "Content 2"));

        when(commentRepository.findByPostId(postID)).thenReturn(testComments);

        List<Comment> actualComments = commentService.findByPostID(postID);
        verify(commentRepository, times(1)).findByPostId(any(Long.class));
        assertThat(testComments).isEqualTo(actualComments);
    }

    @Test
    void shouldDeleteCommentByID() {
        Long myID = 1L;

        Comment testComment = new Comment(myID, null, null,
                "Author", "Content");

        when(commentRepository.findById(myID)).thenReturn(Optional.of(testComment));

        commentService.delete(myID);

        verify(commentRepository).delete(testComment);
    }

    @Test
    void shouldNotDeleteIfDoesntExist() {
        Long missingID = 1L;

        when(commentRepository.findById(missingID)).thenReturn(Optional.empty());

        verify(commentRepository, never()).delete(any());

        assertThrows(EntityNotFoundException.class, () -> commentService.delete(missingID));
    }
}
