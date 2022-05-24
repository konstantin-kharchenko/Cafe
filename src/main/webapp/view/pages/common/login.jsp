<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization.pagecontent" var="lang"/>
<head>
    <title><fmt:message key="login.sign_in" bundle="${lang}"/></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${abs}/view/css/login.css" type="text/css"/>
    <script type="text/javascript">
        window.history.forward();
    </script>

</head>
<body bgcolor="#90ee90">

<c:import url="${abs}/view/pages/header/main.jsp"/>
<form method="post" action="${abs}/language">
    <button class="button2" name="command" value="language_page"><fmt:message key="login.language"
                                                                              bundle="${lang}"/></button>
</form>
<div class="form">
    <form method="post" action="${abs}/controller" onload="load()">
        <br/><br/>
        <h1><fmt:message key="login.sign_in" bundle="${lang}"/></h1><br/><br/>
        <p>
            <label><fmt:message key="login.login" bundle="${lang}"/></label>
            <label>
                <input type="text" name="login" value="${login}"
                       placeholder="<fmt:message key="login.input.login" bundle="${lang}"/>" minlength="2"
                       maxlength="20">
            </label><label id="labelLogin"></label>
        </p>
        <p>
            <label><fmt:message key="login.password" bundle="${lang}"/></label>
            <label>
                <input type="password" name="password" value="${password}"
                       placeholder="<fmt:message key="login.input.password" bundle="${lang}"/>" minlength="6"
                       maxlength="20">
            </label><label id="labelPassword"></label>
        </p>
        <button class="button" name="command" onclick="noBack()" value="login"><fmt:message key="login.button.sign_in"
                             bundle="${lang}"/></button>
        <br/><br/><label>${exception_msg}</label><br/><br/>
    </form>
    <form method="post" action="${abs}/registration">
        <button class="button" name="command" value="registration_page"><fmt:message key="login.button.registration"
                                                                                     bundle="${lang}"/></button>
    </form>
    </br></br></br>
</div>
</body>
</html>
