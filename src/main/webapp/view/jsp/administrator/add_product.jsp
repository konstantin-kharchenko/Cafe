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
    <title><fmt:message key="create.product" bundle="${lang}"/></title>

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
    <script src="${abs}/view/js/create_product.js"></script>
    <script>
        window.history.forward();
    </script>
</head>
<body onload="load()">
<c:import url="${abs}/view/jsp/header/user_header.jsp"/>
<div class="body">
    <main class="form-signin text-center">
        <form method="post" action="${abs}/controller">
            <input type="hidden" id="hidRepeat" name="hidRepeat" value="${repeat}"/>
            <input type="hidden" id="hiddenPrice" name="hiddenPaymentType" value="${product.get("price")}"/>
            <input type="hidden" id="hiddenName" name="hiddenName" value="${product.get("name")}"/>
            <input type="hidden" id="hiddenDate" name="hiddenDate" value="${product.get("date")}"/>
            <img class="mb-4" src="${abs}/view/img/pngtree-vector-coffee-icon-png-image_449722.jpg" alt="" width="72"
                 height="57">
            <h1 class="h3 mb-3 fw-normal"><fmt:message key="create.product" bundle="${lang}"/></h1>

            <div class="form-floating">
                <input type="text" class="form-control" id="floatingName" placeholder="name@example.com" name="name"
                       value="${product.get("name")}">
                <label for="floatingName"><fmt:message key="create_order.name" bundle="${lang}"/></label>
            </div>
            <label id="labelName"></label>
            <div class="form-floating">
                <input type="text" class="form-control" id="floatingPrice" placeholder="name@example.com" name="price"
                       value="${product.get("price")}">
                <label for="floatingName"><fmt:message key="product.price" bundle="${lang}"/></label>
            </div>
            <label id="labelPrice"></label>
            <div class="form-floating">
                <input type="date" class="form-control" id="date" name="date" max="2002.01.01"
                       value="${product.get("date")}" required>
                <label for="date"><fmt:message key="create_order.date" bundle="${lang}"/></label>
            </div>
            <label id="labelDate"></label>
            <button class="w-100 btn btn-lg btn-primary" name="command" value="add_product">
                <fmt:message key="create_order.create" bundle="${lang}"/>
            </button>
            <br/>
            <p class="mt-5 mb-3 text-muted">${msg}</p>
        </form>

    </main>
</div>
</body>
</html>
