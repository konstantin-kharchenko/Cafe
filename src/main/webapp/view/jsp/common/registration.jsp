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
    <title><fmt:message key="registration.main_name" bundle="${lang}"/></title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">

    <!-- Bootstrap core CSS -->
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
    </style>

    <script src="${abs}/view/js/registration.js"></script>
    <!-- Custom styles for this template -->
    <link href="${abs}/view/css/signin.css" rel="stylesheet">
</head>
<body class="text-center" onload="load()">
<input type="hidden" id="hidRepeat" name="hidRepeat" value="${repeat}"/>
<input type="hidden" id="hiddenRoles" name="hidroles" value="${user.get("role")}"/>
<input type="hidden" id="hiddenName" name="hidname" value="${user.get("name")}"/>
<input type="hidden" id="hiddenSurname" name="hidsurname" value="${user.get("surname")}"/>
<input type="hidden" id="hiddenAge" name="hidage" value="${user.get("birthday")}"/>
<input type="hidden" id="hiddenEmail" name="hidemail" value="${user.get("email")}"/>
<input type="hidden" id="hiddenPhone" name="hidphone" value="${user.get("phone_number")}"/>
<input type="hidden" id="hiddenLogin" name="hidlogin" value="${user.get("login")}"/>
<input type="hidden" id="hiddenPassword" name="hidpassword" value="${user.get("password")}"/>
<main class="form-signin">
    <form method="post" action="${abs}/controller">
        <img class="mb-4" src="${abs}/view/img/pngtree-vector-coffee-icon-png-image_449722.jpg" alt="" width="72"
             height="57">
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="registration.main_name" bundle="${lang}"/></h1>

        <div class="list-group list-group-checkable">
            <input class="list-group-item-check" type="radio" name="role" id="radioRole1" value="client"
                   checked>
            <label class="list-group-item py-3" for="radioRole1">
                <fmt:message key="registration.client" bundle="${lang}"/>
            </label>

            <input class="list-group-item-check" type="radio" name="role" id="radioRole2"
                   value="administrator">
            <label class="list-group-item py-3" for="radioRole2">
                <fmt:message key="registration.administrator" bundle="${lang}"/>
            </label>
        </div>

        <div class="form-floating">
            <input type="text" class="form-control" id="floatingName" placeholder="name@example.com" name="name"
                   value="${user.get("name")}">
            <label for="floatingInput"><fmt:message key="registration.name" bundle="${lang}"/></label>
        </div>
        <label id="labelName"></label>
        <div class="form-floating">
            <input type="text" class="form-control" id="floatingSurname" required placeholder="name@example.com"
                   name="surname" value="${user.get("surname")}">
            <label for="floatingSurname"><fmt:message key="registration.surname" bundle="${lang}"/></label>
        </div>
        <label id="labelSurname"></label>
        <div class="form-floating">
            <input type="email" class="form-control" id="floatingPhone" required placeholder="name@example.com"
                   name="email" value="${user.get("email")}"/>
            <label for="floatingInput"><fmt:message key="registration.email" bundle="${lang}"/></label>
        </div>
        <label id="labelEmail"></label>
        <div class="form-floating">
            <input type="text" class="form-control" id="floatingEmail" required placeholder="name@example.com"
                   name="phone_number" value="${user.get("phone_number")}"/>
            <label for="floatingInput"><fmt:message key="registration.phone" bundle="${lang}"/></label>
        </div>
        <label id="labelPhone"></label>
        <div class="form-floating">
            <input type="date" class="form-control" id="date" name="birthday" max="2002.01.01"
                   value="${user.get("birthday")}" required>
            <label for="floatingSurname"><fmt:message key="registration.birthday" bundle="${lang}"/></label>
        </div><label id="labelAge"></label>
        <div class="form-floating">
            <input type="text" class="form-control" id="floatingInput" required placeholder="name@example.com"
                   name="login" value="${user.get("login")}"/>
            <label for="floatingInput"><fmt:message key="registration.login" bundle="${lang}"/></label>
        </div>
        <label id="labelLogin"></label>
        <div class="form-floating">
            <input type="password" class="form-control" id="floatingPassword" required placeholder="Password"
                   name="password" value="${user.get("password")}"/>
            <label for="floatingPassword"><fmt:message key="registration.password" bundle="${lang}"/></label>
        </div>
        <label id="labelPassword"></label>
        <button class="w-100 btn btn-lg btn-primary" name="command" value="registration"><fmt:message key="registration.sign_up" bundle="${lang}"/></button>
        <br/>
        <p class="mt-5 mb-3 text-muted">${msg}</p>
    </form>

</main>
</body>
</html>
