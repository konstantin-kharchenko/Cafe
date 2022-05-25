<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization.pagecontent" var="lang"/>
<head>
    <title><fmt:message key="login.language" bundle="${lang}"/></title>
    <link rel="stylesheet" href="${abs}/view/css/language.css" type="text/css"/>
    <script language="JavaScript" type="text/javascript">
        window.history.forward();
    </script>
</head>
<body bgcolor="#90ee90">
</br></br></br>
<form method="post" action="${abs}/controller">
    </br></br></br>
    CHOOSE LANGUAGE
    </br></br></br>
    Russian <input type="radio" checked name="language" value="russian"></br>
    English <input type="radio" name="language" value="english"></br></br>
    <button name="command" value="language">SAVE</button></br></br>
    <a href="${abs}/controller?command=back_page&old_page=view/pages/common/language.jsp">BACK</a>
    </br></br>
</form>

</body>
</html>
