<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>${user.getLogin()}</title>

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
<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
    <symbol id="bootstrap" viewBox="0 0 328.9 439.71">
        <title>Bootstrap</title>
        <path fill-rule="evenodd" clip-rule="evenodd"
              d="M320.4,94.61H307.46l-8.74-48.56a8.51,8.51,0,0,0-8.37-7h-8.1l-7.66-32.5A8.51,8.51,0,0,0,263.71.41L206.1,19a8.5,8.5,0,0,0-5.89,8.09v12H38.55a8.5,8.5,0,0,0-8.36,7L21.45,94.61H8.5a8.51,8.51,0,0,0-8.5,8.5v46.68a8.51,8.51,0,0,0,8.5,8.5H19.94l34.28,274a8.5,8.5,0,0,0,8.43,7.45h203.6a8.51,8.51,0,0,0,8.44-7.45l34.28-274H320.4a8.5,8.5,0,0,0,8.5-8.5V103.11A8.5,8.5,0,0,0,320.4,94.61ZM217.21,33.27l43-13.85,4.63,19.63H217.21ZM45.66,56.05H283.24l6.94,38.56H38.72ZM267.2,355.19H61.71L48.46,249.28h232Zm-8.45,67.52H70.16l-7.58-60.52H266.32Zm22.57-180.43H47.58l-10.51-84H291.83Zm30.58-101H17V111.61H311.9Z"/>
        <path class="cls-1"
              d="M178.58,274.16a4,4,0,0,1-1.08,1.47c-5.46,4.7-6.08,12.85-6.79,22.28-.83,10.92-1.76,23.2-10.28,33.93,10.2,0,21-8.17,26.29-20.88C193,295.88,189.35,279.83,178.58,274.16Z"/>
        <path class="cls-1"
              d="M170.89,271.15c-10,.35-20.49,8.41-25.71,20.87-6.27,15-2.68,30.92,7.94,36.69a3.75,3.75,0,0,1,.73-1.11c8.39-9.14,9.24-20.39,10.07-31.27C164.61,287.29,165.31,278,170.89,271.15Z"></path>
    </symbol>
</svg>
<header class="p-3 mb-3 border-bottom">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <a class="d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none">
                <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                    <use xlink:href="#bootstrap"/>
                </svg>
            </a>

            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><a href="${abs}/controller?command=go_all_orders_page" class="nav-link px-2 link-dark"><fmt:message
                        key="client.all_orders" bundle="${lang}"/></a>
                </li>
                <li><a href="${abs}/controller?command=go_all_products&currentPage=1"
                       class="nav-link px-2 link-dark"><fmt:message
                        key="client.menu" bundle="${lang}"/></a></li>
                <li>
                    <a href="${abs}/controller?command=go_language_page&main_page=view/jsp/client/client.jsp"
                       class="nav-link px-2 link-dark"><fmt:message key="language" bundle="${lang}"/></a>
                </li>
                <li>
                    <a href="${abs}/controller?command=go_basket_page"
                       class="nav-link px-2 link-dark"><fmt:message key="client.basket" bundle="${lang}"/>
                        <c:choose>
                            <c:when test="${basket_products.size() > 0}">
                                +${basket_products.size()}
                            </c:when>
                        </c:choose>
                    </a>
                </li>
            </ul>
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><h1 class="h3 mb-3 fw-normal"><fmt:message key="client.loyalty_points"
                                                               bundle="${lang}"/>: ${user.getLoyaltyPoints()}</h1>
                </li>
            </ul>
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><h1 class="h3 mb-3 fw-normal"><fmt:message key="client.account"
                                                               bundle="${lang}"/>: ${user.clientAccount}</h1>
                </li>
            </ul>
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <a href="${abs}/controller?command=go_refill_page"
                   class="btn btn-primary"><fmt:message key="client.add.client.account"
                                                        bundle="${lang}"/></a>
            </ul>
            <div class="dropdown text-end">
                <a href="#" class="d-block link-dark text-decoration-none dropdown-toggle" id="dropdownUser1"
                   data-bs-toggle="dropdown" aria-expanded="false">
                    <img src="data:image/png;base64,${photo}" alt="${user.getName()}" width="32"
                         height="32" class="rounded-circle">
                </a>
                <ul class="dropdown-menu text-small" aria-labelledby="dropdownUser1">
                    <li><a class="dropdown-item"
                           href="${abs}/controller?command=go_client_profile_page"><fmt:message
                            key="client.profile" bundle="${lang}"/></a></li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li><a class="dropdown-item"
                           href="${abs}/controller?command=go_exit_page"><fmt:message
                            key="client.sign_out" bundle="${lang}"/></a></li>
                </ul>
            </div>
        </div>
    </div>
</header>
<main>
    <div class="b-example-divider"></div>
    <div class="main">
        <div style="margin-left: 1.5%; margin-right: 1.5%">
            <div class="row row-cols-2">
                <div>
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
                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><h1 class="h3 mb-3 fw-normal">${msg}</h1>
                        </li>
                    </ul>
                </div>

                <div>
                    <h1 class="h3 mb-3 fw-normal"><fmt:message key="client.whats_new" bundle="${lang}"/></h1>
                    <div class="row row-cols-4">
                        <c:forEach items="${products}" var="product">
                            <div class="card" style="width: 25rem;">
                                <div class="card-body">
                                    <h5 class="card-title"><fmt:message key="product.name"
                                                                        bundle="${lang}"/>: ${product.name}</h5>
                                    <p class="card-text"><fmt:message key="product.date"
                                                                      bundle="${lang}"/>: ${product.date}</p>
                                    <h5 class="card-title"><fmt:message key="product.price"
                                                                        bundle="${lang}"/>: ${product.price}</h5>
                                    <a href="${abs}/controller?command=add_product_in_basket&return_page=view/jsp/client/client.jsp&id_product=${product.idProduct}&name=${product.name}&date=${product.date}&price=${product.price}"
                                       class="btn btn-primary"><fmt:message key="product.add.basket"
                                                                            bundle="${lang}"/></a>
                                    <a href="${abs}/controller?command=go_product_page&id_product=${product.idProduct}"
                                       class="btn btn-primary"><fmt:message key="product.show.more"
                                                                            bundle="${lang}"/></a>
                                </div>
                            </div>
                            <br/>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <p class="mt-5 mb-3 text-muted">${msg}</p>
</main>

<c:import url="${abs}/view/jsp/footer/footer.jsp"/>
<script src="${abs}/view/js/bootstrap.bundle.min.js"></script>
</body>
</html>
