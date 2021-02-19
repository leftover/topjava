package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private MealService service;

    public MealRestController(@Autowired MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll meals");
        int userId = SecurityUtil.authUserId();
        int caloriesPerDay = SecurityUtil.authUserCaloriesPerDay();
        return MealsUtil.getTos(service.getAll(userId), caloriesPerDay);
    }

    public MealTo get(int id) {
        log.info("get meal {}", id);
        int userId = SecurityUtil.authUserId();
        return MealsUtil.createTo(service.get(id, userId), false);
    }

    public MealTo create(MealTo mealTo) {
        log.info("create meal {}", mealTo);
        int userId = SecurityUtil.authUserId();
        Meal meal = service.create(MealsUtil.create(mealTo), userId);
        return MealsUtil.createTo(meal, false);
    }

    public void delete(int id) {
        log.info("delete meal {}", id);
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
    }

    public void update(MealTo mealTo, int id) {
        log.info("update meal {}, id={}", mealTo, id);
        int userId = SecurityUtil.authUserId();
        Meal meal = MealsUtil.create(mealTo);
        meal.setId(id);
        service.update(meal, userId);
    }
}