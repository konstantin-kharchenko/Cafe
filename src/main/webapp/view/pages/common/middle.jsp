<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        window.history.forward();
    </script>
</head>
<body bgcolor="#90ee90">
<input type="hidden" id="page" value="${old_page}">
<center>
    Are you shore?
    <br/>
    <form method="post" action="${abs}/controller">
        <button name="command" value="logout">yes</button>
        <button name="command" value="back_page">no</button>
    </form>
</center>
</body>
</html>
