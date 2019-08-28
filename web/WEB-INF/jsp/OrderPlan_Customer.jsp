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

        <title>ORDER PLAN</title>


        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">



    </head>
    <body onload="onLoadFunction()">

        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <nav class="navbar navbar-light navbar-expand-md bg-primary justify-content-center">
                        <a href="${pageContext.request.contextPath}/CustomerMainPage.htm" class="navbar-brand d-flex w-50 mr-auto"><b>ΑΡΧΙΚΗ ΣΕΛΙΔΑ</b></a>
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




                    <div class="table-responsive">

                        <h3>${customer.second_name}:Order Plan For Week</h3>

                        <hr>
                        <div id="ActivationLink" >
                            <p id="message"> ${message} </p>
                            <a href="${pageContext.request.contextPath}/autogeneratorActivation.htm?user_id=${customer.user_id}">ACTIVATE AUTOGENERATION</a> <br>
                        </div>

                        <div id="DeactivationLink">
                            <a href="${pageContext.request.contextPath}/autogeneratorDeactivation.htm?user_id=${customer.user_id}">DEACTIVATE ORDER AUTOGENERATION</a> <br>
                        </div>
                        <hr>
                        <button class="btn btn-outline-primary" onclick="zero()">ΜΗΔΕΝΙΣΜΟΣ</button>

                        <hr>
                        <form:form  class="form-horizontal" action="${pageContext.request.contextPath}/saveTemplate.htm" method="POST" modelAttribute="order"  >


                            <table id="table" border="5" class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Product Name</th>
                                        <th>ΔΕΥΤΕΡΑ</th>
                                        <th>ΤΡΙΤΗ</th>
                                        <th>ΤΕΤΑΡΤΗ</th>
                                        <th>ΠΕΜΤΗ</th>
                                        <th>ΠΑΡΣΚΕΥΗ</th>
                                        <th>ΣΑΒΒΒΑΤΟ</th>
                                        <th>ΚΥΡΙΑΚΗ</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${orderTemplate.templateItems}" var="current" varStatus="loop">
                                        <tr class="row1">
                                            <td><input  type="text" value="${current.product.product_id}" readonly="readonly" name="templateItems[${loop.index}].product.product_id"  style="width: 2em"  /></td>
                                            <td><c:out  value="${current.product.selling_name}" /></td>
                                            <td><input  type="number" value="${current.day_1}"  name="templateItems[${loop.index}].day_1" onkeyup="colorChange(this)" onclick="colorChange(this)" class="quantity" style="width: 4em"></td>
                                            <td><input  type="number" value="${current.day_2}"  name="templateItems[${loop.index}].day_2" onkeyup="colorChange(this)" onclick="colorChange(this)" class="quantity" style="width: 4em"></td>

                                            <td><input  type="number" value="${current.day_3}"  name="templateItems[${loop.index}].day_3" onkeyup="colorChange(this)" onclick="colorChange(this)" class="quantity" style="width: 4em"></td>

                                            <td><input  type="number" value="${current.day_4}"  name="templateItems[${loop.index}].day_4" onkeyup="colorChange(this)" onclick="colorChange(this)" class="quantity" style="width: 4em"></td>

                                            <td><input  type="number" value="${current.day_5}"  name="templateItems[${loop.index}].day_5" onkeyup="colorChange(this)" onclick="colorChange(this)" class="quantity" style="width: 4em"></td>

                                            <td><input  type="number" value="${current.day_6}"  name="templateItems[${loop.index}].day_6" onkeyup="colorChange(this)" onclick="colorChange(this)" class="quantity" style="width: 4em"></td>

                                            <td><input  type="number" value="${current.day_7}"  name="templateItems[${loop.index}].day_7" onkeyup="colorChange(this)" onclick="colorChange(this)" class="quantity" style="width: 4em"></td>

                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <br>
                            <input type="hidden" name="user_id" id="customer_id" value="${customer.user_id}" readonly="readonly">


                            <input class="btn btn-primary" type="submit" value="ΑΠΟΘΗΚΕΥΣΗ" />

                        </form:form>


                        <hr>


                    </div>
                </div>
            </div>

        </div>
        <script>

            function colorRow() {

                var x = document.getElementsByClassName('row1');
                for (i = 0; i < x.length; i++) {
                    x[i].style.backgroundColor = "yellow";
                }

            }
            function zero() {
                var x = document.getElementsByClassName('quantity');
                for (i = 0; i < x.length; i++) {
                    x[i].value = 0;
                }
                var x = document.getElementsByClassName('row1');
                for (i = 0; i < x.length; i++) {
                    x[i].style.backgroundColor = "yellow";
                }
            }

            function colorChange(a) {
                var parentRow = a.parentNode;
                if (!Number.isInteger(a.value)) {
                    parentRow.style.backgroundColor = "red";
                }
                if (a.value > 0 && a.value < 500) {
                    parentRow.style.backgroundColor = "green";
                }
                if (a.value == 0) {
                    parentRow.style.backgroundColor = "yellow";
                }
            }


            function activationLinkStatus() {
                var x = document.getElementById("message").innerHTML;

                console.log(x.length);
                //for some reason that i have no idea of x.lengh=2 even if there is no message at all.
                if (x.length > 3) {
                    console.log(x);
                    document.getElementById("ActivationLink").style.color = "red";
                    document.getElementById("DeactivationLink").style.display = "none";
                } else {
                    console.log(x)
                    console.log(x.length);
                    document.getElementById("ActivationLink").style.display = "none";
                    document.getElementById("DeactivationLink").style.display = "block";

                }
            }
            function onLoadFunction() {

                colorRow();
                activationLinkStatus()
            }

        </script>

    </body>
</html>
