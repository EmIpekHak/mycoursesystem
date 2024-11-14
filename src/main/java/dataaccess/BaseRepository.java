package dataaccess;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T,I>{

    Optional<T> insert(T entity);
    Optional<T> update(T entity);
    Optional<T> getById(I id);
    List<T> getAll();
    void deleteById(I id);
}
