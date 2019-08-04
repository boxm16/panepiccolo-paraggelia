<%-- 
    Document   : FavoriteProducts
    Created on : Apr 10, 2019, 9:36:12 PM
    Author     : boxm1
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

        <title>Favorite Products Page</title>
    </head>
    <body>
        <h1>  ${customer.second_name} Favorite Products</h1>

        <a href="${pageContext.request.contextPath}/AdminMainPage.htm">GO MAIN PAGE</a> <br>

      <!--  <a href="${pageContext.request.contextPath}/autogenerator.htm?user_id=${customer.user_id}&day=NoDay">Autogenerator</a>  -->
        <hr>
        <a href="${pageContext.request.contextPath}/OrderPlan.htm?user_id=${customer.user_id}">Order Plan For Week</a>

        <hr>
        <a href="${pageContext.request.contextPath}/AddNewFavoriteProduct.htm?user_id=${customer.user_id}">Add New Favorite Product</a>

        <table id="table" border="1">
            <tr class="row">
                <th >ID</th>
                <th>Product Name</th>
                <th>Label Type</th>
                <th></th>
            </tr>
            <c:forEach items="${favoriteProductsList}" var="current" varStatus="loop">
                <tr class="row">
                    <td><input type="text" value="${current.product.product_id}" readonly="readonly" name="orderItems[${loop.index}].product.product_id"  style="width: 2em"  /></td>
                    <td><c:out value="${current.product.selling_name}" /></td>
                    <td><c:out value="${current.label.label_description}" /></td>
                    <td><a href="${pageContext.request.contextPath}/deleteFavoriteProduct.htm?user_id=${customer.user_id}&product_id=${current.product.product_id}">DELETE Favorite Product</a></td> 

                </tr>

            </c:forEach>

        </table>
        <hr>

    </body>
</html>
