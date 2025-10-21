package swf.army.mil.blog.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService s) { this.commentService = s; }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment c) {
        return ResponseEntity.ok(commentService.save(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommmentByID(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findByID(id));
    }

    @GetMapping("/bypost/{id}")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findByPostID(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
