<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<head>
    <title>${order_name}</title>
</head>
<body bgcolor="#90ee90">
<div>
    <c:forEach var="menu" items="${menu_list}">
        <div>
            <c:out value="${menu.getName()}"/><br/>
            <c:out value="${menu.getDate()}"/>
        </div>
    </c:forEach>
    <center><h1>Максик лох</h1></center>
    <a href="${abs}/controller?command=back_page&old_page=view/pages/users/order.jsp">BACK</a>
</div>
</body>
</html>
