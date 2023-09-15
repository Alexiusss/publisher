package service;

import lombok.AllArgsConstructor;
import model.Post;
import model.Writer;
import repository.post.PostRepository;
import repository.writer.WriterRepository;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class WriterService {

    WriterRepository writerRepository;
    PostRepository postRepository;

    public Writer getByIdWithPosts(String id) throws IOException {
        Writer writer = writerRepository.getById(id);
        if (writer != null) {
            List<Post> posts = postRepository.getAllByWriterId(id);
            writer.setPosts(posts);
        }
        return writer;
    }
}