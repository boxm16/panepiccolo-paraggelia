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

        <title>ΗΜΕΡΟΜΗΝΙΕΣ (ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</title>


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
                        <a class="nav-link  " href="${pageContext.request.contextPath}/LockedOrdersPage.htm"">ΔΕΣ ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ</a>
                        <a class="nav-link " href="${pageContext.request.contextPath}/OrderedItemsList_ActiveOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΤΕΜΑΧΙΩΝ ΑΝΑΛΥΤΙΚΑ(ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/OrderedItemsList_LockedOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΤΕΜΑΧΙΩΝ ΑΝΑΛΥΤΙΚΑ(ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> 
                        <a class="nav-link active" href="${pageContext.request.contextPath}/LabelsList_ActiveOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΗΜΕΡΟΜΗΝΙΩΝ ΑΝΑΛΥΤΙΚΑ (ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a>
                        <a class="nav-link" href="${pageContext.request.contextPath}/LabelsList_LockedOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΗΜΕΡΟΜΗΝΙΩΝ ΑΝΑΛΥΤΙΚΑ (ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> <br>
                    </nav>




                    <div class="table-responsive">
                        <h3>ΗΜΕΡΟΜΗΝΙΕΣ (ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</h3>

                        <hr>
                        <table id="table" class="table-hover " border="5">
                            <thead>
                                <tr >

                                    <th>Label Description</th>
                                    <th>QUANTITY</th>
                                </tr>
                            </thead>
                            <c:forEach items="${orderedItemsLabelsList_ActiveOrders}" var="current" varStatus="loop">
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
        </div>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
