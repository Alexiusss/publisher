package controller;

import model.Post;
import repository.GenericRepository;
import repository.post.PostRepository;

import java.io.IOException;
import java.util.List;

public class PostController extends BaseController<Post, String>{

    PostRepository postRepository;

    public PostController(GenericRepository<Post, String> repository) {
        super(repository);
    }

    public List<Post> getAllByWriterId(String writerId) throws IOException {
        return postRepository.getAllByWriterId(writerId);
    }

}