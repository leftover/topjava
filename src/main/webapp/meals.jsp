<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 4px;
            text-align: left;
            font-family: "Segoe UI";
        }

    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>My Meals</h2>
<table>
    <tr bgcolor="#98A6FF">
        <th>Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <%
        List<MealTo> meals = (List<MealTo>) request.getAttribute("MealList");
        for (MealTo meal : meals) {
            String rowColor = meal.isExcess() ? "#FF661E" : "#AAECFF";
            out.println(String.format("<tr bgcolor=%s>%n" +
                            "<td> %s %s </td>%n" +
                            "<td> %s </td>%n" +
                            "<td> %d </td>",
                    rowColor,
                    meal.getDateTime().toLocalDate(),
                    meal.getDateTime().toLocalTime(),
                    meal.getDescription(),
                    meal.getCalories()
            ));
        }
    %>
</table>
</body>
</html>
