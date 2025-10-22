package swf.army.mil.blog.post;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository r) { this.postRepository = r; }

    @Transactional
    public Post save(Post p) {
        return postRepository.save(p);
    }

    public Post findByID(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public void delete(Long id) {
        Post p = findByID(id);

        postRepository.delete(p);
    }

    @Transactional
    public Post updateContent(Long id, String content) {
        Post p = findByID(id);

        p.setContent(content);
        return postRepository.save(p);
    }

    @Transactional
    public Post like(Long id) {
        Post p = findByID(id);

        p.setLikes(p.getLikes() + 1);
        return postRepository.save(p);
    }
}
