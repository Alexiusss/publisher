package controller;

import lombok.AllArgsConstructor;
import model.Label;
import repository.label.LabelRepository;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class LabelController {

    LabelRepository labelRepository;

    public Label getById(String id) throws IOException {
        return labelRepository.getById(id);
    }

    public List<Label> getAll() throws IOException {
        return labelRepository.getAll();
    }

    public Label save(Label label) throws IOException {
        return labelRepository.save(label);
    }

    public Label update(Label label) throws IOException {
        return labelRepository.update(label);
    }

    public void deleteById(String id) throws IOException {
        labelRepository.deleteById(id);
    }
}