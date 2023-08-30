package repository;

import java.io.IOException;
import java.util.List;

public interface GenericRepository<T,ID> {
    T getById(ID id) throws IOException;
    List<T> getAll() throws IOException;
    T save(T t);
    T update(T t);
    void deleteById(ID id);
}