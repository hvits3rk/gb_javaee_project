<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Catalog</title>
    <link rel="stylesheet" href="../public/css/main.css">
</head>

<body>

<jsp:include page="component/header.jsp" />

<div class="container">
    <div class="section">
        <h1>Каталог</h1>

        <div class="card-list">
            <c:forEach var="product" items="${products}">
                <div class="card pointer">
                    <p>ID: <span>${product.id}</span></p>
                    <p>NAME: <span>${product.name}</span></p>
                    <p>DESC: <span>${product.description}</span></p>
                </div>
                <br>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>
