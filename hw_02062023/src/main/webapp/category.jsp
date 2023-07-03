<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Категории</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<h2>${categoryName}</h2>

<div class="row">
    <c:forEach items="${categoryProducts}" var="product">
        <div class="col d-flex justify-content-center">
            <div class="card" style="width: 14rem; margin: 20px">
                <img class="card-img-top"
                     src="${contextPath}/images/${product.getImageName()}" alt="Card image">
                <div class="card-body" style="text-align: center">
                    <h5 class="card-title"> ${product.getName()}</h5>
                    <p class="card-text">${product.getDescription()}</p>
                    <p class="card-text">Цена: <fmt:formatNumber value="${product.getPrice()}"
                                                                 type="currency"/><br>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

</body>
</html>
