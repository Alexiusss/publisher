package repository.writer;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import model.Writer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class GsonWriterRepositoryImpl implements WriterRepository {

    Gson gson;

    @Override
    public Writer getById(String id) {
        return null;
    }

    @Override
    public List<Writer> getAll() throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/store/writers.json"));
        return Arrays.asList(gson.fromJson(reader, Writer[].class));
    }

    @Override
    public Writer save(Writer writer) {
        return null;
    }

    @Override
    public Writer update(Writer writer) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}