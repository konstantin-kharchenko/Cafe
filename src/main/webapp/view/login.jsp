<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>LogIn</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/login.css" type="text/css"/>
</head>
<body bgcolor="#90ee90">
<c:import url="pages/header/main.jsp" />
<form method="post" action="../controller" onload="load()">
    <br/><br/>
    <h1>SIGN IN</h1><br/><br/>
    <p>
        <label>LOGIN</label>
        <label>
            <input type="text" name="login" value="${login}" placeholder="LogIn" minlength="2" maxlength="20">
        </label><label id="labelLogin"></label>
    </p>
    <p>
        <label>PASSWORD</label>
        <label>
            <input type="password" name="password" value="${password}" placeholder="Password" minlength="6" maxlength="20">
        </label><label id="labelPassword"></label>
    </p>
    <button name="command" value="login">SIGN IN</button>
    <br/><br/><label>${exception_msg}</label><br/><br/>
    <button name="command" value="registration_page">Registration</button>
</form>
</body>
</html>

