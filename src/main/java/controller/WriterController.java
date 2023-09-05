package controller;

import lombok.AllArgsConstructor;
import model.Writer;
import repository.writer.WriterRepository;

import java.io.IOException;


import java.util.List;

@AllArgsConstructor
public class WriterController {

    WriterRepository writerRepository;

    public Writer getById(String id) throws IOException {
        return writerRepository.getById(id);
    }

    public List<Writer> getAll() throws IOException {
        return writerRepository.getAll();
    }

    public Writer save(Writer writer) throws IOException {
        return writerRepository.save(writer);
    }

    public Writer update(Writer writer) throws IOException {
        return writerRepository.update(writer);
    }

    public void deleteById(String id) throws IOException {
        writerRepository.deleteById(id);
    }
}