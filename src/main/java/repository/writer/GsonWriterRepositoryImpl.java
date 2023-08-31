package repository.writer;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import model.Status;
import model.Writer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static util.Util.generateNewId;

@AllArgsConstructor
public class GsonWriterRepositoryImpl implements WriterRepository {

    Gson gson;
    String fileName;

    @Override
    public Writer getById(String id) throws IOException {
        return getAll().stream().filter(writer -> writer.getId().equals(id)).findAny().orElseThrow();
    }

    @Override
    public List<Writer> getAll() throws IOException {
        Writer[] writers = gson.fromJson(Files.newBufferedReader(Paths.get(fileName)), Writer[].class);
        return writers != null ? new ArrayList<>(Arrays.asList(writers)) : new ArrayList<>();
    }

    @Override
    public Writer save(Writer writer) throws IOException {
        writer.setId(generateNewId());
        List<Writer> writerList = getAll();
        writerList.add(writer);
        saveToFile(writerList);
        return writer;
    }

    @Override
    public Writer update(Writer updatedWriter) throws IOException {
        List<Writer> writerList = getAll().stream()
                .map(writer -> {
                    if (writer.getId().equals(updatedWriter.getId())) {
                        return updatedWriter;
                    }
                    return writer;
                })
                .collect(Collectors.toList());
        saveToFile(writerList);
        return updatedWriter;
    }

    @Override
    public void deleteById(String id) throws IOException {
        List<Writer> writerList = getAll().stream()
                .peek(writer -> {
                    if (writer.getId().equals(id)) {
                        writer.setStatus(Status.DELETED);
                    }
                }).collect(Collectors.toList());
        saveToFile(writerList);
    }

    private void saveToFile(List<Writer> writerList) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)){
            gson.toJson(writerList, fileWriter);
        }
    }
}