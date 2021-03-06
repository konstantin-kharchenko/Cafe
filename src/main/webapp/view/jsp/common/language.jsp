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
    <title><fmt:message key="language" bundle="${lang}"/></title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">


    <!-- Bootstrap core CSS -->
    <link href="${abs}/view/css/bootstrap.min.css" rel="stylesheet">
    <link href="${abs}/view/css/list-groups.css" rel="stylesheet">
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
<main class="form-signin">
    <form method="post" action="${abs}/controller">
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="language.choose_language" bundle="${lang}"/></h1>
        <div class="list-group list-group-checkable">
            <input class="list-group-item-check" type="radio" name="language" value="ru" id="radioRole1"
                   checked>
            <label class="list-group-item py-3" for="radioRole1">
                <fmt:message key="language.russian" bundle="${lang}"/>
            </label>
            <input class="list-group-item-check" type="radio" name="language" value="en" id="radioRole2">
            <label class="list-group-item py-3" for="radioRole2">
                <fmt:message key="language.english" bundle="${lang}"/>
            </label>
        </div>
        <button class="btn btn-primary" name="command" value="change_language"><fmt:message key="language.save"
                                                                                            bundle="${lang}"/></button>

    </form>
</main>


</body>
</html>

