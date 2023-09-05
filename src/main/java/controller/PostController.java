package controller;

import lombok.AllArgsConstructor;
import model.Post;
import repository.post.PostRepository;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class PostController {

    PostRepository postRepository;

    public Post getById(String id) throws IOException {
        return postRepository.getById(id);
    }

    public List<Post> getAll() throws IOException {
        return postRepository.getAll();
    }

    public List<Post> getAllByWriterId(String writerId) throws IOException {
        return postRepository.getAllByWriterId(writerId);
    }

    public Post save(Post post) throws IOException {
        return postRepository.save(post);
    }

    public Post update(Post post) throws IOException {
        return postRepository.update(post);
    }

    public void deleteById(String id) throws IOException {
        postRepository.deleteById(id);
    }
}