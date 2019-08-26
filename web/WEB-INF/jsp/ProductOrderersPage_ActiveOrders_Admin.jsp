<%-- 
    Document   : ProductOrderers
    Created on : May 5, 2019, 3:10:53 PM
    Author     : Michail Sitmalidis
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello ProductOrderers</h1>
        ProductOrderersPage_ActiveOrders_Admin
        <hr>


        <table id="table" border="1">
            <tr class="row">
                <th >OrderID</th>
                <th>Orderer</th>
                <th>Quantity</th>
            </tr>
            <c:forEach items="${productOrderersList}" var="current" varStatus="loop">
                <tr class="row">
                    <td><c:out value="${current.order.order_id}" /></td>
                    <td><c:out value="${current.user.second_name}" /></td>
                    <td><c:out value="${current.quantity}" /></td>
                    <td><button onclick="location.href = '${pageContext.request.contextPath}/displayOrder.htm?order_id=${current.order.order_id}'">ΔΕΣ ΠΑΡΑΓΓΕΛΙΑ ΑΝΑΛΥΤΗΚΑ</button></td>


                </tr>
            </c:forEach>

        </table>
    </body>
</html>
