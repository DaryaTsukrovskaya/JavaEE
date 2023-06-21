<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <title>Форма</title>
</head>
<body>
Пожалуйста заполните поля для авторизации
<form method="post" action="home">
    Логин:<input type="text" name="name"></br>
    Пароль:<input type="text" name="password"></br>
    <input type="submit" name="Войти">
</form>
</body>
</html>