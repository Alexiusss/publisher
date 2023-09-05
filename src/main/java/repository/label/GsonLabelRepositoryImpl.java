package repository.label;

import com.google.gson.Gson;
import model.Label;
import model.Status;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static util.Util.*;

public class GsonLabelRepositoryImpl implements LabelRepository {

    Gson gson;
    String fileName;

    @Override
    public Label getById(String id) throws IOException {
        return getByIdFromList(getListFromFile(gson, fileName, Label.class), id);
    }

    @Override
    public List<Label> getAll() throws IOException {
        return getListFromFile(gson, fileName, Label.class);
    }

    @Override
    public Label save(Label label) throws IOException {
        List<Label> labelsList = getListFromFile(gson, fileName, Label.class);
        label.setId(generateNewId());
        labelsList.add(label);
        saveToFile(labelsList, gson, fileName);
        return label;
    }

    @Override
    public Label update(Label updatedLabel) throws IOException {
        List<Label> writerList = updateEntityInList(getListFromFile(gson, fileName, Label.class), updatedLabel);
        saveToFile(writerList, gson, fileName);
        return updatedLabel;
    }

    @Override
    public void deleteById(String id) throws IOException {
        List<Label> postList = getListFromFile(gson, fileName, Label.class).stream()
                .peek(post -> {
                    if (post.getId().equals(id)) {
                        post.setStatus(Status.DELETED);
                    }
                }).collect(Collectors.toList());
        saveToFile(postList, gson, fileName);
    }
}