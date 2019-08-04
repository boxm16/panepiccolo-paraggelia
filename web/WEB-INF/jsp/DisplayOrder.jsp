<%-- 
    Document   : ShowActiveOrder
    Created on : Apr 28, 2019, 4:54:10 PM
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
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    </head>
    <body>
        <h1>Order Display</h1>
        <a href="${pageContext.request.contextPath}/AdminMainPage.htm">Go Main Page</a><br>
        <hr>
        Order_ID:${order.order_id}<br>
        Orderer: ${order.orderer}<br>
        Order Status: ${order.status}<br>
        Order Creation Time: ${order.creation_time}<br>
        Order Due Day: ${order.due_day}<br>



        <input id="datepicker" type="date"  name="due_day" value="${order.due_day}" readonly="readonly" />
        <table id="table" border="1">
            <tr class="row">
                <th >ID</th>
                <th>Product Name</th>
                <th>Quantity</th>
            </tr>
            <c:forEach items="${order.orderItems}" var="current" varStatus="loop">
                <tr class="row">
                    <td><input type="text" value="${current.product.product_id}" readonly="readonly" name="orderItems[${loop.index}].product.product_id"  style="width: 2em"  /></td>
                    <td><c:out value="${current.product.selling_name}" /></td>
                    <td><input type="number" name="orderItems[${loop.index}].quantity"  value="${current.quantity}" onchange="colorChange(this)" class="quantity" style="width: 4em"></td>

                    <!-- <td><button onclick="addHtmlTableRow();">Add To Order</button></td> -->

                </tr>
            </c:forEach>

        </table>




        <br>



        <hr>

        <script>
            function zero() {

                var x = document.getElementsByClassName('quantity');
                for (i = 0; i < x.length; i++) {
                    x[i].value = 0;
                }
                var x = document.getEl
                ementsByClassName('row');
                for (i = 0; i < x.length; i++) {
                    x[i].style.backgroundColor
                            = "red";
                }
            }

            function colorChange(a) {
                var parentRow = a.parentNode.parentNode;
                if (a.value != 0) {
                    parentRow.style.backgroundColor = "green";
                } else {
                    parentRow.style.backgroundColor = "red";
                }
            }

        </script>
    </body>
</html>
