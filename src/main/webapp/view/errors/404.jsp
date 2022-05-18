<%--
  Created by IntelliJ IDEA.
  User: konha
  Date: 12.04.2022
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404</title>
</head>
<body>
Request from: ${pageContext.errorData.requestURI} is failed <br/>
Servlet name: ${pageContext.errorData.servletName}<br/>
Status code: ${pageContext.errorData.statusCode}<br/>
Exception: ${pageContext.exception}<br/>
Exception message: ${pageContext.exception.message}<br/>
<br/><br/><br/>
</body>
</html>
