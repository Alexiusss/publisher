package controller;

import model.Label;
import repository.GenericRepository;

public class LabelController extends BaseController<Label, String>{

    public LabelController(GenericRepository <Label, String> repository) {
        super(repository);
    }
}