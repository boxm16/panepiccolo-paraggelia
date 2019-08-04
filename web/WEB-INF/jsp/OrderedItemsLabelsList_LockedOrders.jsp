<%-- 
    Document   : OrdererdItemsList
    Created on : May 5, 2019, 8:40:47 AM
    Author     : boxm1
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ORDERED ITEMS LABELS SUMMARY</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    </head>
    <body>
        <div class="container">

            <div class="col-sm">
                <div class="table-responsive">
                    <h1>ORDERED ITEMS LABELS SUMMARY</h1>
                    <h1>LOCKED ORDERS</h1>
                    <a href="${pageContext.request.contextPath}/AdminMainPage.htm">Go Home</a><br>
                    <hr>
                    <table id="table" class="table-hover " border="5">
                        <thead>
                            <tr >

                                <th>Label Description</th>
                                <th>QUANTITY</th>
                            </tr>
                        </thead>
                        <c:forEach items="${orderedItemsLabelsList_LockedOrders}" var="current" varStatus="loop">
                            <tr >
                                <td><b><c:out value="${current.label.label_description}" /></b></td>
                                <td><b><c:out value="${current.quantity}" /></b></td>
                            </tr>
                        </c:forEach>

                    </table>
                    <hr>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
