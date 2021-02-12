package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private MealDao dao = new MealDao();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "/meals.jsp";
        String action = req.getParameter("action");
        action = action != null ? action : "list";

        int id;
        switch (action) {
            case "edit":
                id = Integer.parseInt(req.getParameter("mealId"));
                req.setAttribute("meal", dao.getById(id));
            case "add":
                forward = "/editmeal.jsp";
                break;
            case "delete":
                id = Integer.parseInt(req.getParameter("mealId"));
                dao.delete(id);
                break;
        }
        List<MealTo> meals = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        meals.sort(Comparator.comparing(MealTo::getDateTime));
        req.setAttribute("mealList", meals);
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        if (isValid(req)) {
            LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("datetime"), formatter);
            String description = req.getParameter("description");
            int calories = Integer.parseInt(req.getParameter("calories"));

            Meal meal = new Meal(dateTime, description, calories);

            if (!req.getParameter("mealId").equals("")) {
                int id = Integer.parseInt(req.getParameter("mealId"));
                dao.delete(id);
            }
            dao.add(meal);
        }

        List<MealTo> meals = MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        meals.sort(Comparator.comparing(MealTo::getDateTime));
        req.setAttribute("mealList", meals);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    private boolean isValid(HttpServletRequest req) {
        String description = req.getParameter("description");
        if (description == null || description.equals("")) return false;
        String calories = req.getParameter("calories");
        if (calories == null || calories.equals("")) return false;
        String datetime = req.getParameter("datetime");
        return datetime != null && !datetime.equals("");
    }
}
