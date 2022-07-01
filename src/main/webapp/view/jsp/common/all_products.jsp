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
    <title><fmt:message key="client.menu" bundle="${lang}"/></title>

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
            <a href="${abs}/controller?command=go_user_page"
               class="d-flex align-items-center mb-2 mb-lg-0 text-dark text-decoration-none">
                <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap">
                    <use xlink:href="#bootstrap"/>
                </svg>
            </a>
            <form action="${abs}/controller" class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                <input type="hidden" name="command" value="search_product">
                <input type="hidden" name="currentPage" value="${currentPage}">
                <input type="search" class="form-control" name="search" value="${search_product.name}"
                       placeholder="Search..." aria-label="Search">
            </form>

        </div>
    </div>
</header>
<div class="b-example-divider"></div>
<main>
    <br/>
    <div style="margin-left: 1.5%; margin-right: 1.5%">
        <div class="row row-cols-6">
            <c:choose>
                <c:when test="${not empty search_product}">
                    <div class="col pb-4">
                        <div class="card" style="width: 18rem;">
                            <div class="card-body">
                                <h5 class="card-title"><fmt:message key="product.name"
                                                                    bundle="${lang}"/>: ${search_product.name}</h5>
                                <p class="card-text"><fmt:message key="product.date"
                                                                  bundle="${lang}"/>: ${search_product.date}</p>
                                <h5 class="card-title"><fmt:message key="product.price"
                                                                    bundle="${lang}"/>: ${search_product.price}</h5>
                                <c:choose>
                                    <c:when test="${role.toString() == 'CLIENT'}">
                                        <a href="${abs}/controller?command=add_product_in_basket&return_page=view/jsp/common/all_products.jsp&currentPage=${currentPage}&id_product=${product.idProduct}&name=${product.name}&date=${product.date}&price=${product.price}"
                                           class="btn btn-primary"><fmt:message key="product.add.basket"
                                                                                bundle="${lang}"/></a>
                                        <a href="${abs}/controller?command=go_product_page&id_product=${search_product.idProduct}"
                                           class="btn btn-primary"><fmt:message key="product.show.more"
                                                                                bundle="${lang}"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${abs}/controller?command=delete_product&id_product=${search_product.idProduct}&currentPage=${currentPage}"
                                           class="btn btn-primary"><fmt:message key="product.delete"
                                                                                bundle="${lang}"/></a>
                                        <a href="${abs}/controller?command=go_change_product_page&id_product=${search_product.idProduct}"
                                           class="btn btn-primary"><fmt:message key="order.change"
                                                                                bundle="${lang}"/></a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${products}" var="product">
                        <div class="col pb-4">
                            <div class="card" style="width: 18rem;">
                                <div class="card-body">
                                    <h5 class="card-title"><fmt:message key="product.name"
                                                                        bundle="${lang}"/>: ${product.name}</h5>
                                    <p class="card-text"><fmt:message key="product.date"
                                                                      bundle="${lang}"/>: ${product.date}</p>
                                    <h5 class="card-title"><fmt:message key="product.price"
                                                                        bundle="${lang}"/>: ${product.price}</h5>
                                    <c:choose>
                                        <c:when test="${role.toString() == 'CLIENT'}">
                                            <a href="${abs}/controller?command=add_product_in_basket&return_page=view/jsp/common/all_products.jsp&currentPage=${currentPage}&id_product=${product.idProduct}&name=${product.name}&date=${product.date}&price=${product.price}"
                                               class="btn btn-primary"><fmt:message key="product.add.basket"
                                                                                    bundle="${lang}"/></a>
                                            <a href="${abs}/controller?command=go_product_page&id_product=${product.idProduct}"
                                               class="btn btn-primary"><fmt:message key="product.show.more"
                                                                                    bundle="${lang}"/></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${abs}/controller?command=delete_product&id_product=${product.idProduct}&currentPage=${currentPage}"
                                               class="btn btn-primary"><fmt:message key="product.delete"
                                                                                    bundle="${lang}"/></a>
                                            <a href="${abs}/controller?command=go_change_product_page&id_product=${product.idProduct}"
                                               class="btn btn-primary"><fmt:message key="order.change"
                                                                                    bundle="${lang}"/></a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</main>
<center>
    <nav aria-label="Ad navigation">
        <ul class="pagination mt-2">
            <li class="page-item ${firstPage ? 'disabled' : ''}">
                <a class="page-link"
                   href="${abs}/controller?command=go_all_products&currentPage=${currentPage - 1}"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <c:forEach var="i" begin="1" end="${pageCount}">
                <li class="page-item ${currentPage == i ? 'active': ''}">
                    <a class="page-link"
                       href="${abs}/controller?command=go_all_products&currentPage=${i}">${i}</a>
                </li>
            </c:forEach>

            <li class="page-item ${lastPage ? 'disabled' : ''}">
                <a class="page-link"
                   href="${abs}/controller?command=go_all_products&currentPage=${currentPage + 1}"
                   aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</center>
<c:import url="${abs}/view/jsp/footer/footer.jsp"/>
<script src="${abs}/view/js/bootstrap.bundle.min.js"></script>
</body>
</html>
