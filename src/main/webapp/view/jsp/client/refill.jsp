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
    <title><fmt:message key="create_order.main_name" bundle="${lang}"/></title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">

    <link href="${abs}/view/css/bootstrap.min.css" rel="stylesheet">
    <link href="${abs}/view/css/list-groups.css" rel="stylesheet">

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

        .body {
            display: flex;
            align-items: center;
            padding-top: 1px;
            padding-bottom: 80px;
            background-color: #f5f5f5;
        }

        .form-signin {
            width: 100%;
            max-width: 330px;
            padding: 15px;
            margin: auto;
        }

        .form-signin .checkbox {
            font-weight: 400;
        }

        .form-signin .form-floating:focus-within {
            z-index: 2;
        }

        .form-signin input[type="email"] {
            margin-bottom: -1px;
            border-bottom-right-radius: 0;
            border-bottom-left-radius: 0;
        }

        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
    <script>
        window.history.forward();
    </script>
</head>
<c:import url="${abs}/view/jsp/header/user_header.jsp"/>
<div class="body">
    <main class="form-signin text-center">
        <form action="${abs}/controller" method="post">

            <img class="mb-4" src="${abs}/view/img/pngtree-vector-coffee-icon-png-image_449722.jpg" alt="" width="72"
                 height="57">
            <h1 class="h3 mb-3 fw-normal"><fmt:message key="enter.sum" bundle="${lang}"/></h1>

            <div class="form-floating">
                <input type="text" class="form-control" name="price" min="1"
                       value="" required>
            </div>
            <label id="labelDate"></label>
            <button class="w-100 btn btn-lg btn-primary" name="command" value="refill">
                <fmt:message key="top.up" bundle="${lang}"/>
            </button>
            <br/>
            <p class="mt-5 mb-3 text-muted">${msg}</p>
        </form>

    </main>
</div>
</body>
</html>
