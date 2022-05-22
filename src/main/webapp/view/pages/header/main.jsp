<%--
  Created by IntelliJ IDEA.
  User: konha
  Date: 10.05.2022
  Time: 08:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization.pagecontent" var="lang"/>
<head>
    <title>CAFE</title>
    <link rel="stylesheet" href="../css/main.css" type="text/css"/>
</head>
<body>
<div class="main_div"><center><h1><fmt:message key="login.project_name" bundle="${lang}"/></h1></center></div>
</body>
</html>
