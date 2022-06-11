<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="abs">${pageContext.request.contextPath}</c:set>
<html>
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
<main>
    <br/>
    <div style="margin-left: 1.5%; margin-right: 1.5%">
        <div class="row row-cols-6">
            <c:forEach items="${products}" var="product">
                <div class="col pb-4">
                    <div class="card" style="width: 18rem;">
                        <div class="card-body">
                            <h5 class="card-title">Name: ${product.name}</h5>
                            <p class="card-text">Date: ${product.date}</p>
                            <h5 class="card-title">Price: ${product.price}</h5>
                            <a href="${abs}/controller?command=add_product_in_basket&return_page=view/jsp/client/all_products.jsp&currentPage=${currentPage}&id_product=${product.idProduct}&name=${product.name}&date=${product.date}&price=${product.price}"
                               class="btn btn-primary">Add to basket</a>
                            <a href="${abs}/controller?command=go_product_page&id_product=${product.idProduct}"
                               class="btn btn-primary">Show more</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div></div>
</main>
<center>
    <nav aria-label="Ad navigation">
        <ul class="pagination mt-2">
            <li class="page-item ${firstPage ? 'disabled' : ''}">
                <a class="page-link"
                   href="${abs}/controller?command=go_client_all_products&currentPage=${currentPage - 1}"
                   aria-label="Previous">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <c:forEach var="i" begin="1" end="${pageCount}">
                <li class="page-item ${currentPage == i ? 'active': ''}">
                    <a class="page-link"
                       href="${abs}/controller?command=go_client_all_products&currentPage=${i}">${i}</a>
                </li>
            </c:forEach>

            <li class="page-item ${lastPage ? 'disabled' : ''}">
                <a class="page-link"
                   href="${abs}/controller?command=go_client_all_products&currentPage=${currentPage + 1}"
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
