package app.daos;

import java.util.List;

public interface IDAO<T> {
    List<T> getAll();
    T getById(int id);
    T create(T t);
    T update(int id, T t);
    void delete(int id);
}