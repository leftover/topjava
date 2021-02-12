package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDao implements Dao<Meal> {

    @Override
    public void add(Meal meal) {
        RamStorage.add(meal);
    }

    @Override
    public void delete(int id) {
        RamStorage.deleteById(id);
    }

    @Override
    public void update(Meal meal) {
        RamStorage.deleteById(meal.getId());
        RamStorage.add(meal);
    }

    @Override
    public List<Meal> getAll() {
        return RamStorage.getAll();
    }

    @Override
    public Meal getById(int id) {
        return RamStorage.getById(id);
    }
}
