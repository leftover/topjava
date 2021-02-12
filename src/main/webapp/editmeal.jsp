<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Edit Meal</title>
</head>
<body>
<form action="meals" name="edit" method="post">
    <input type="hidden" name="mealId" value="<c:out value="${requestScope.meal.id}"/>">
    <p>
        <label>Date/Time:
            <input type="datetime-local" name="datetime" value="<c:out value="${requestScope.meal.dateTime}"/>">
        </label><br>
    </p>
    <p>
        <label>Description:
            <input type="text" name="description" value="<c:out value="${requestScope.meal.description}"/>">
        </label><br>
    </p>
    <p>
        <label>Calories:
            <input type="number" name="calories" value="<c:out value="${requestScope.meal.calories}"/>">
        </label><br>
    <p>
        <input type="submit" value="Save"/>
</form>
</body>
</html>
