package swf.army.mil.blog.comment;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository r) { this.commentRepository = r; }

    public Comment save(Comment c) {
        return commentRepository.save(c);
    }

    public Comment findByID(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
    }

    public List<Comment> findByPostID(Long postID) {
        return commentRepository.findByPostId(postID);
    }

    public void delete(Long id) {
        Comment c = findByID(id);

        commentRepository.delete(c);
    }
}
