<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="localization.pagecontent" var="lang"/>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.88.1">
    <title>Menu</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.1/examples/headers/">
    <link href="${abs}/view/css/bootstrap.min.css" rel="stylesheet">
    <link href="${abs}/view/css/headers.css" rel="stylesheet">
    <link href="${abs}/view/css/client.css" rel="stylesheet">
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


</head>
<body>
<c:import url="${abs}/view/jsp/header/user_header.jsp"/>
<div class="container">
    <article>
        <h1 class="h3 mb-3 fw-normal"><fmt:message key="client.recent_orders" bundle="${lang}"/></h1>
        <div class="container text-center">
            <aside>
                <div class="list-group">
                    <c:forEach var="order" items="${orders}">
                        <a href="${abs}/controller?command=go_order_page&id_order=${order.idOrder}"
                           class="list-group-item list-group-item-action flex-column align-items-start btn btn-outline-primary">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1"><c:out value="${order.getName()}"/></h5>
                                <small><c:out value="${order.getDate()}"/>
                                    <br/>
                                    <h5><c:out value="${order.price}"/> руб.</h5>
                                </small>
                            </div>
                            <p class="mb-1">
                                <c:forEach var="product" items="${order.getProducts()}">
                                    <c:out value="${product.getName()}"/>
                                    <br/>
                                </c:forEach>
                            </p>
                            <small>
                                    ${order.getPaymentType()}
                            </small>
                        </a>
                        <br/>
                    </c:forEach>
                </div>
            </aside>
        </div>
        <form action="${abs}/controller">
            <button class="w-100 btn btn-lg btn-primary" name="command" value="go_create_order_page">
                <fmt:message key="client.create_order" bundle="${lang}"/>
            </button>
        </form>
    </article>

    <script src="${abs}/view/js/bootstrap.bundle.min.js"></script>
</div>
<c:import url="${abs}/view/jsp/footer/footer.jsp"/>
</body>
</html>
