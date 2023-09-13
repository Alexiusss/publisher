package controller;

import model.BaseEntity;
import repository.GenericRepository;

import java.io.IOException;
import java.util.List;

public abstract class BaseController<T extends BaseEntity, ID> {

    private final GenericRepository<T, ID> repository;

    public BaseController(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    public List<T> getAll() throws IOException {
        return repository.getAll();
    }

    public T getById(ID id) throws IOException {
        return repository.getById(id);
    }

    public void add(T entity) throws IOException {
        repository.save(entity);
    }

    public T update(T entity) throws IOException {
       return repository.update(entity);
    }

    public void deleteById(ID id) throws IOException {
        repository.deleteById(id);
    }
}