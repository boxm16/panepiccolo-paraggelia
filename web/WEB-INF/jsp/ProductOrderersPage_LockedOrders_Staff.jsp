<%-- 
    Document   : MAIN
    Created on : Apr 7, 2019, 4:44:32 AM
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

        <title>Product Orderes` List</title>


        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">



    </head>
    <body>

        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <!--https://www.codeply.com/go/qhaBrcWp3v-->

                    <nav class="navbar navbar-light navbar-expand-md bg-primary justify-content-center">
                        <a href="${pageContext.request.contextPath}/ObserverPage.htm" class="navbar-brand d-flex w-50 mr-auto"><b>ΠΑΡΑΓΓΕΛΙΕΣ</b></a>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsingNavbar3">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="navbar-collapse collapse w-100" id="collapsingNavbar3">


                            <ul class="nav navbar-nav ml-auto w-100 justify-content-end">
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Logout.htm">ΑΠΟΣΥΝΔΕΣΗ</a>
                                </li>
                            </ul>
                        </div>
                    </nav>


                    <nav class="nav flex-column nav-pills" style="background:lightskyblue;" >
                        <a class="nav-link " href="${pageContext.request.contextPath}/LockedOrdersPage.htm"">ΔΕΣ ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ</a>
                        <a class="nav-link " href="${pageContext.request.contextPath}/OrderedItemsList_ActiveOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΤΕΜΑΧΙΩΝ ΑΝΑΛΥΤΙΚΑ(ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/OrderedItemsList_LockedOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΤΕΜΑΧΙΩΝ ΑΝΑΛΥΤΙΚΑ(ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> 
                    </nav>
                    <hr>
                    <h3>Product Orderers` List</h3>
                    <hr>
                    <div class="table-responsive">


                        <table id="table" class="table-hover " border="5">
                            <tr >
                                <th >OrderID</th>
                                <th>Orderer</th>
                                <th>Quantity</th>
                                <th></th>
                            </tr>
                            <c:forEach items="${productOrderersList}" var="current" varStatus="loop">
                                <tr>
                                    <td><c:out value="${current.order.order_id}" /></td>
                                    <td><c:out value="${current.user.second_name}" /></td>
                                    <td><c:out value="${current.quantity}" /></td>
                                    <td><button onclick="location.href = '${pageContext.request.contextPath}/displayOrder.htm?order_id=${current.order.order_id}'">ΔΕΣ ΠΑΡΑΓΓΕΛΙΑ ΑΝΑΛΥΤΗΚΑ</button></td>


                                </tr>
                            </c:forEach>

                        </table>

                    </div>
                    <hr>
                </div>
            </div>
        </div>
    </body>
</html>
