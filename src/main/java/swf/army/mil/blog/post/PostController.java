package swf.army.mil.blog.post;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService s) { this.postService = s; }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post p) {
        return ResponseEntity.ok(postService.save(p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostByID(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findByID(id));
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> updatePostContent(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String content = payload.get("content");
        return ResponseEntity.ok(postService.updateContent(id, content));
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Post> like(@PathVariable Long id) {
        return ResponseEntity.ok(postService.like(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
