package repository.post;

import model.Post;
import repository.GenericRepository;

import java.io.IOException;
import java.util.List;

public interface PostRepository extends GenericRepository<Post, String> {
    List<Post> getAllByWriterId(String writerId) throws IOException;
}