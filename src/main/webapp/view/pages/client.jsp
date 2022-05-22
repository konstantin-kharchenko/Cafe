<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.getLogin()}</title>
    <link rel="stylesheet" href="css/client.css" type="text/css"/>
    <script language="JavaScript" type="text/javascript">
        window.history.forward();
    </script>
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
