<%--
  Created by IntelliJ IDEA.
  User: konha
  Date: 30.04.2022
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.getLogin()}</title>
    <link rel="stylesheet" href="css/client.css" type="text/css"/>
</head>
<body bgcolor="#90ee90">
<form action="controller" class="form">
    <div class="div">
        <h3>Hello ${user.getName()} ${user.getSurname()}</h3>
        <h4>Login: ${user.getLogin()}</h4>
        <h4>Email: ${user.getEmail()}</h4>
        <h4>Phone number: ${user.getPhoneNumber()}</h4>
        <h4>Loyalty points: ${user.getLoyaltyPoints()}</h4>
        <br/>
    </div>
    <button class="Mybutton" name="command" value="logout">LogOut</button>
</form>
</body>
</html>
