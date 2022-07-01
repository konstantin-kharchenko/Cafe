<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<!doctype html>
<html>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization.pagecontent" var="lang"/>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title><fmt:message key="login.name" bundle="${lang}"/></title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">

    <!-- Bootstrap core CSS -->
    <link href="${abs}/view/css/bootstrap.min.css" rel="stylesheet">
    <script>
        window.history.forward();
    </script>
    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>


    <!-- Custom styles for this template -->
    <link href="${abs}/view/css/signin.css" rel="stylesheet">
</head>
<body class="text-center">
<input type="hidden" name="back_page" value="view/pages/users/client.jsp">
<main class="form-signin">
    <form method="post" action="${abs}/controller">
        <img class="mb-4" src="${abs}/view/img/pngtree-vector-coffee-icon-png-image_449722.jpg" alt="" width="72"
             height="57">
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="login.message" bundle="${lang}"/></h1>

        <div class="form-floating">
            <input type="text" class="form-control" id="floatingInput" placeholder="name@example.com" name="login"
                   value="${login}">
            <label for="floatingInput"><fmt:message key="login.login" bundle="${lang}"/></label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" placeholder="Password" name="password"
                   value="${password}">
            <label for="floatingPassword"><fmt:message key="login.password" bundle="${lang}"/></label>
        </div>
        <button class="w-100 btn btn-lg btn-primary" name="command" value="sign_in"><fmt:message key="login.sign-in"
                                                                                                 bundle="${lang}"/></button>
        <br/>
        <h1 class="h3 mb-3 fw-normal">${msg}</h1>
    </form>
</main>


</body>
</html>

