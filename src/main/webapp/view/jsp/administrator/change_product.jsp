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
    <title><fmt:message key="change.product" bundle="${lang}"/></title>

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


</head>
<body>
<c:import url="${abs}/view/jsp/header/user_header.jsp"/>
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

<main>
    <br/>
    <form method="post" action="${abs}/controller" enctype="multipart/form-data">
        <div style="margin-left: 1.5%; margin-right: 1.5%">
            <div class="row row-cols-2">
                <div>
                    <article>
                        <h1 class="h3 mb-3 fw-normal"><fmt:message key="product"
                                                                   bundle="${lang}"/>: ${product.name}</h1>
                        <div class="container text-center">
                            <aside>
                                <div class="list-group">
                                    <c:forEach var="ingredient" items="${product.ingredients}">
                                        <a class="list-group-item list-group-item-action flex-column align-items-start border border-primary">
                                            <div class="d-flex w-100 justify-content-between">
                                                <h5 class="mb-1"><c:out value="${ingredient.getName()}"/></h5>
                                                <small>
                                                    <c:out value="${ingredient.shelfLife}"/>
                                                    <br/>
                                                    <c:out value="${ingredient.grams}"/>
                                                </small>
                                            </div>
                                            <small>
                                                <a class="w-25 btn btn-lg btn-primary m-1"
                                                   href="${abs}/controller?command=delete_ingredient_from_product&id_ingredient=${ingredient.idIngredient}&id_product=${product.idProduct}">
                                                    <fmt:message key="product.delete" bundle="${lang}"/>
                                                </a>
                                                <a class="w-25 btn btn-lg btn-primary m-1" id="link"
                                                   href="${abs}/controller?command=go_change_grams_page&id_ingredient=${ingredient.idIngredient}&id_product=${product.idProduct}">
                                                    <fmt:message key="order.change" bundle="${lang}"/>
                                                </a>
                                            </small>
                                        </a>
                                        <br/>
                                    </c:forEach>
                                </div>
                            </aside>
                        </div>
                    </article>
                </div>
                <div>
                    <br/><br/>
                    <input type="hidden" name="id_product" value="${product.idProduct}">
                    <a class="list-group-item list-group-item-action flex-column align-items-start border border-primary">
                        <div class="d-flex flex-column align-items-center text-center p-3 py-5">
                            <img class="rounded-circle mt-5" width="150px"
                                 src="data:image/png;base64,${product.stringPhoto}">
                            <br/>
                            <label class="input-file">
                                <h1 class="h6"><fmt:message key="choose.file" bundle="${lang}"/></h1>
                                <input type="file" name="photo" value="photo"/>
                            </label>
                        </div>
                        <div class="form-floating">
                            <input type="text" class="form-control" id="floatingName" placeholder="name@example.com"
                                   name="name"
                                   value="${product.name}">
                            <label for="floatingName"><fmt:message key="create_order.name" bundle="${lang}"/></label>
                        </div>
                        <label id="labelName"></label>
                        <div class="form-floating">
                            <input type="text" class="form-control" id="floatingPrice" placeholder="name@example.com"
                                   name="price"
                                   value="${product.price}">
                            <label for="floatingName"><fmt:message key="product.price" bundle="${lang}"/></label>
                        </div>
                        <label id="labelPrice"></label>
                        <div class="form-floating">
                            <input type="date" class="form-control" id="date" name="date" max="2002.01.01"
                                   value="${product.date}" required>
                            <label for="date"><fmt:message key="create_order.date" bundle="${lang}"/></label>
                        </div>
                        <button class="w-25 btn btn-lg btn-primary m-1" name="command"
                                value="add_ingredients_from_administrator_basket">
                            <fmt:message key="add.from.basket" bundle="${lang}"/>
                        </button>
                        <button class="w-25 btn btn-lg btn-primary m-1" name="command"
                                value="change_product">
                            <fmt:message key="order.change" bundle="${lang}"/>
                        </button>
                        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                            <li><h1 class="h3 mb-3 fw-normal">${msg}</h1>
                            </li>
                        </ul>
                    </a>
                </div>
            </div>
        </div>
    </form>
</main>

<c:import url="${abs}/view/jsp/footer/footer.jsp"/>
<script src="${abs}/view/js/bootstrap.bundle.min.js"></script>
<script>
</script>
</body>
</html>
