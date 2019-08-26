
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

        <title>DISPLAY ORDER</title>


        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">



    </head>
    <body>

        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <!--https://www.codeply.com/go/qhaBrcWp3v-->

                    <nav class="navbar navbar-light navbar-expand-md bg-primary justify-content-center">
                        <a href="${pageContext.request.contextPath}/AdminMainPage.htm" class="navbar-brand d-flex w-50 mr-auto"><b>ΠΑΡΑΓΓΕΛΙΕΣ</b></a>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsingNavbar3">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="navbar-collapse collapse w-100" id="collapsingNavbar3">
                            <ul class="navbar-nav w-100 justify-content-center">
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Customers.htm">ΠΕΛΑΤΕΣ</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Products.htm">ΠΡΟΪΟΝΤΑ</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Labels.htm">ΤΑΜΠΕΛΕΣ</a>
                                </li>
                            </ul>
                            <ul class="nav navbar-nav ml-auto w-100 justify-content-end">
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Observers.htm">STAFF</a>
                                </li>
                            </ul>

                            <ul class="nav navbar-nav ml-auto w-100 justify-content-end">
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Logout.htm">ΑΠΟΣΥΝΔΕΣΗ</a>
                                </li>
                            </ul>
                        </div>
                    </nav>


                    <nav class="nav flex-column nav-pills" style="background:lightskyblue;" >
                        <a class="nav-link " href="${pageContext.request.contextPath}/LockedOrdersPage.htm"">ΔΕΣ ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ</a>
                        <a class="nav-link"  href="${pageContext.request.contextPath}/OrderedItemsList_ActiveOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΤΕΜΑΧΙΩΝ ΑΝΑΛΥΤΙΚΑ(ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/OrderedItemsList_LockedOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΤΕΜΑΧΙΩΝ ΑΝΑΛΥΤΙΚΑ(ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> 
                        <a class="nav-link" href="${pageContext.request.contextPath}/LabelsList_ActiveOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΗΜΕΡΟΜΗΝΙΩΝ ΑΝΑΛΥΤΙΚΑ (ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/LabelsList_LockedOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΗΜΕΡΟΜΗΝΙΩΝ ΑΝΑΛΥΤΙΚΑ (ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> <br>
                    </nav>

                    <div class="table-responsive">
                        Order_ID:${order.order_id}<br>
                        Orderer: ${order.orderer}<br>
                        Order Status: ${order.status}<br>
                        Order Creation Time: ${order.creation_time}<br>
                        Order Due Day: ${order.due_day}<br>



                       <table id="table" class=" table-hover " border="5">
                            <tr>
                                <th >ID</th>
                                <th>Product Name</th>
                                <th>Quantity</th>
                            </tr>
                            <c:forEach items="${order.orderItems}" var="current" varStatus="loop">
                                <tr>
                                    <td><c:out value="${current.product.product_id}" /></td>
                                    <td><c:out value="${current.product.selling_name}" /></td>
                                    <td><c:out  value="${current.quantity}"/></td>

                                        <!-- <td><button onclick="addHtmlTableRow();">Add To Order</button></td> -->

                                    </tr>
                            </c:forEach>

                        </table>




                        <br>

                    </div>
                </div>
            </div>
        </div>

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
