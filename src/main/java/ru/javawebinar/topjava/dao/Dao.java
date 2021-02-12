package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {
    void add(T element);

    void delete(int id);

    void update(T element);

    List<T> getAll();

    T getById(int id);
}
