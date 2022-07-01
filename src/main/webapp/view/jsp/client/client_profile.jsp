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
    <title><fmt:message key="profile.settings" bundle="${lang}"/></title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/sign-in/">

    <link href="${abs}/view/css/bootstrap.min.css" rel="stylesheet">
    <script>
        function reload() {
            console.log("load new file");
        }
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

        .input-file {
            display: inline-block;

            position: relative;

            overflow: hidden;
            min-width: 100px;
            height: 30px;
            padding: 5px;

            font: 12px sans-serif;
            line-height: 20px;

            text-align: center;

            border: #3b71de 1px solid;
            border-radius: 5px;

            color: #ffffff;
            background-color: #3b71de;
        }

        .input-file input {
            position: absolute;
            top: 0;
            left: -999em;
        }
    </style>
    <script>
        window.history.forward();
    </script>
    <link href="${abs}/view/css/profile.css" rel="stylesheet">
</head>
<body class="text-center">
<form method="post" action="${abs}/controller" enctype="multipart/form-data">
    <div class="container rounded bg-white mt-5 mb-5">
        <div class="row">
            <div class="col-md-3 border-right">
                <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                    <img class="rounded-circle mt-5" width="150px" src="data:image/png;base64,${photo}">
                    <br/>
                    <label class="input-file">
                        <h1 class="h6"><fmt:message key="choose.file"
                                                    bundle="${lang}"/></h1>
                        <input type="file" name="photo" value="photo" onchange="reload()"/>
                    </label>
                </div>
            </div>
            <div class="col-md-5 border-right">
                <div class="p-3 py-5">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h4 class="text-right"><fmt:message key="profile.settings"
                                                            bundle="${lang}"/></h4>
                    </div>
                    <div class="row mt-2">
                        <div class="col-md-6"><label class="labels"><fmt:message key="profile.name"
                                                                                 bundle="${lang}"/></label><input
                                type="text" class="form-control"
                                placeholder="first name"
                                name="name"
                                value="${user.getName()}"></div>
                        <div class="col-md-6"><label class="labels"><fmt:message key="profile.surname"
                                                                                 bundle="${lang}"/></label><input
                                type="text"
                                class="form-control"
                                name="surname"
                                value="${user.getSurname()}"
                                placeholder="surname"></div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-md-12"><label class="labels"><fmt:message key="profile.phone.number"
                                                                                  bundle="${lang}"/></label><input
                                type="text"
                                name="phone_number"
                                class="form-control"
                                placeholder="enter phone number"
                                value="${user.getPhoneNumber()}">
                        </div>
                        <div class="col-md-12"><label class="labels"><fmt:message key="profile.login"
                                                                                  bundle="${lang}"/></label><input
                                type="text"
                                class="form-control"
                                name="login"
                                placeholder="enter address line 1"
                                value="${user.getLogin()}">
                        </div>

                    </div>
                    <div class="mt-5 text-center">
                        <button class="w-100 btn btn-lg btn-primary" name="command" value="update_client"><fmt:message
                                key="profile.save"
                                bundle="${lang}"/>
                        </button>
                    </div>
                    <p class="mt-5 mb-3 text-muted">${msg}</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="p-3 py-5">
                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><h1 class="h3 mb-3 fw-normal"><fmt:message key="client.loyalty_points"
                                                                       bundle="${lang}"/>: ${user.getLoyaltyPoints()}</h1>
                        </li>
                        <li><h1 class="h3 mb-3 fw-normal"><fmt:message key="client.account"
                                                                       bundle="${lang}"/>: ${user.getClientAccount()}</h1>
                        </li>
                        <li><h1 class="h3 mb-3 fw-normal"><fmt:message key="profile.email"
                                                                       bundle="${lang}"/>: ${user.getEmail()}</h1></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</form>
</body>
</html>
