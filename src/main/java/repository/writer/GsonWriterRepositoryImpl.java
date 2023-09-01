package repository.writer;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import model.Status;
import model.Writer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static util.Util.*;

@AllArgsConstructor
public class GsonWriterRepositoryImpl implements WriterRepository {

    Gson gson;
    String fileName;

    @Override
    public Writer getById(String id) throws IOException {
        return getListFromFile(gson, fileName, Writer.class).stream()
                .filter(writer -> writer.getId().equals(id))
                .findAny()
                .orElseThrow();
    }

    @Override
    public List<Writer> getAll() throws IOException {
        return getListFromFile(gson, fileName, Writer.class);
    }

    @Override
    public Writer save(Writer writer) throws IOException {
        writer.setId(generateNewId());
        List<Writer> writerList = getListFromFile(gson, fileName, Writer.class);
        writerList.add(writer);
        saveToFile(writerList, gson, fileName);
        return writer;
    }

    @Override
    public Writer update(Writer updatedWriter) throws IOException {
        List<Writer> writerList = getListFromFile(gson, fileName, Writer.class).stream()
                .map(writer -> {
                    if (writer.getId().equals(updatedWriter.getId())) {
                        return updatedWriter;
                    }
                    return writer;
                })
                .collect(Collectors.toList());
        saveToFile(writerList, gson, fileName);
        return updatedWriter;
    }

    @Override
    public void deleteById(String id) throws IOException {
        List<Writer> writerList = getListFromFile(gson, fileName, Writer.class).stream()
                .peek(writer -> {
                    if (writer.getId().equals(id)) {
                        writer.setStatus(Status.DELETED);
                    }
                }).collect(Collectors.toList());
        saveToFile(writerList, gson, fileName);
    }
}