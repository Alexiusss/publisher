package repository.writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Status;
import model.Writer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static util.Util.*;

public class GsonWriterRepositoryImpl implements WriterRepository {

    Gson gson;
    String fileName;

    public GsonWriterRepositoryImpl(String fileName) {
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        this.fileName = fileName;
    }

    @Override
    public Writer getById(String id) throws IOException {
        List<Writer> list = getListFromFile(gson, fileName, Writer.class);
        if (list.size() > 0) {
            return getByIdFromList(list, id);
        }
        return new Writer();
    }

    @Override
    public List<Writer> getAll() throws IOException {
        return getListFromFile(gson, fileName, Writer.class);
    }

    @Override
    public Writer save(Writer writer) throws IOException {
        List<Writer> writerList = getListFromFile(gson, fileName, Writer.class);
        writer.setId(generateNewId());
        writerList.add(writer);
        saveToFile(writerList, gson, fileName);
        return writer;
    }

    @Override
    public Writer update(Writer updatedWriter) throws IOException {
        List<Writer> writerList = updateEntityInList(getListFromFile(gson, fileName, Writer.class), updatedWriter);
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