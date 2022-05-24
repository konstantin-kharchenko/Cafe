<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization.pagecontent" var="lang"/>
<head>
    <title>CAFE</title>
    <link rel="stylesheet" href="${abs}/view/css/main.css" type="text/css"/>
</head>
<body>
<div class="main_div"><center><h1><fmt:message key="login.project_name" bundle="${lang}"/></h1></center></div>
</body>
</html>
