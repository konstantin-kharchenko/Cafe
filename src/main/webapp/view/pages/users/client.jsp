<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization.pagecontent" var="lang"/>
<head>
    <title>${user.getLogin()}</title>
    <link rel="stylesheet" href="${abs}/view/css/client.css" type="text/css"/>
    <script type="text/javascript">
        window.history.forward();
    </script>
</head>
<body bgcolor="#90ee90">
<div class="form">
    <form action="${abs}/controller">
        <div class="div">
            <h3>Hello ${user.getName()} ${user.getSurname()}</h3>
            <h4>Login: ${user.getLogin()}</h4>
            <h4>Email: ${user.getEmail()}</h4>
            <h4>Phone number: ${user.getPhoneNumber()}</h4>
            <h4>Loyalty points: ${user.getLoyaltyPoints()}</h4>
            <br/>
        </div>
        <button class="button" name="command" value="logout">LogOut</button>
    </form>
    <form action="${abs}/language">
        <button class="button3" name="command" value="language_page"><fmt:message key="login.language"
                                                                                  bundle="${lang}"/></button>
    </form>
</div>
</body>
</html>
