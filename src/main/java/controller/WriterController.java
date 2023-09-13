package controller;

import model.Writer;
import repository.GenericRepository;

public class WriterController extends BaseController<Writer, String>{
    public WriterController(GenericRepository<Writer, String> repository) {
        super(repository);
    }
}