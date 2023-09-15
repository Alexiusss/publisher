package controller;

import model.Writer;
import repository.post.PostRepository;
import repository.writer.WriterRepository;
import service.WriterService;

import java.io.IOException;

public class WriterController extends BaseController<Writer, String>{

    WriterService service;

    public WriterController(WriterRepository writerRepository, PostRepository postRepository) {
        super(writerRepository);
        service = new WriterService(writerRepository, postRepository);
    }

    public Writer getByIdWithPosts(String id) throws IOException {
        return service.getByIdWithPosts(id);
    }
}