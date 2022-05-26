<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization.pagecontent" var="lang"/>
<head>
    <title>${user.getLogin()}</title>
    <link rel="stylesheet" href="${abs}/view/css/client.css" type="text/css"/>
    <script type="text/javascript">
    </script>
</head>
<body bgcolor="#90ee90">
<input type="hidden" name="old_page" id="old_page" value="${old_page}">
<div class="form">
    <form method="post" action="${abs}/controller">
        <div class="div">
            <h3>Hello ${user.getName()} ${user.getSurname()}</h3>
            <h4>Login: ${user.getLogin()}</h4>
            <h4>Email: ${user.getEmail()}</h4>
            <h4>Phone number: ${user.getPhoneNumber()}</h4>
            <h4>Loyalty points: ${user.getLoyaltyPoints()}</h4>
            <br/>
        </div>
        <div class="div3">
            <c:forEach var="order" items="${orders}">
                <div class="div2">
                    <a href="${abs}/controller?command=order&order_id=${order.getIdOrder()}&order_date=${order.getDate()}
                    &order_id_client=${order.getIdClient()}&order_sum=${order.getSum()}
                    &order_name=${order.getName()}"><c:out value="${order.getName()}"/></a><br/>
                    <c:out value="${order.getDate()}"/>
                </div>
            </c:forEach>
        </div>
        <button class="button" name="command" value="middle_page">LogOut</button>
    </form>
    <form method="post" action="${abs}/language">
        <button class="button3" name="command" value="language_page"><fmt:message key="login.language"
                                                                                  bundle="${lang}"/></button>
    </form>
</div>
</body>
</html>
