package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        //todo hard code userId in MealUtils...
        MealsUtil.meals.forEach(m -> save(m, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save meal {}, userId {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }

        if (get(meal.getId(), userId) != null) {
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal {}, userId {}", id, userId);
        if (get(id, userId) == null) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal {}, userId {}", id, userId);
        Meal meal = repository.get(id);
        if (meal != null && meal.getUserId().equals(userId)) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll meals, userId {}", userId);
        return repository.values().stream()
                .filter(m -> m.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

