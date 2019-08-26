<%-- 
    Document   : OrderPage2
    Created on : Apr 28, 2019, 12:28:08 AM
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
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">

        <title>NEW ORDER</title>

        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
        <script type='text/javascript' src='//code.jquery.com/jquery-1.8.3.js'></script>
        <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
        <script type='text/javascript'>
            $(function () {
                $('.input-group.date').datepicker({
                    format: "yyyy-mm-dd",
                    daysOfWeekHighlighted: '0',
                    daysOfWeekDisabled: '0',
                    todayHighlight: true,
                    autoclose: true
                });
            });

        </script>
    </head>
    <body onload="zero()">
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <!--https://www.codeply.com/go/qhaBrcWp3v-->

                    <nav class="navbar navbar-light navbar-expand-md bg-primary justify-content-center">
                        <a href="${pageContext.request.contextPath}/AdminMainPage.htm" class="navbar-brand d-flex w-50 mr-auto">ΠΑΡΑΓΓΕΛΙΕΣ</a>
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

                    <div class="table-responsive">
                        <h3>ΚΑΤΑΧΩΡΗΣΕ ΚΑΙΝΟΥΡΓΙΑ ΠΑΡΑΓΓΕΛΙΑ ΓΙΑ <p>${customer.username}<p/></h3> 
                        <hr>
                        <button  class="btn btn-info btn-sm"  onclick="location.href = '${pageContext.request.contextPath}/LoadMyLastOrder.htm?user_id=${customer.user_id}'">LOAD MY LAST ORDER</button>
                        &#160;  &#160;
                        <button onclick="zero()">ΜΗΔΕΝΙΣΜΟΣ</button>
                        <hr>

                        <div class="form-group">
                            <form:form action="${pageContext.request.contextPath}/saveOrder.htm" method="POST" modelAttribute="order"  >
                                <input type="number" name="orderer" value="${customer.user_id}" style="width: 1em" readonly="readonly" hidden="hidden">



                                <div class="input-group date">
                                    <input type="text" value="${due_day1}" name="due_day" class="form-control"><span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                                </div>

                                <br>

                                <table id="table" border="5" class="table">
                                    <tr>
                                        <th>ID</th>
                                        <th>Product Name</th>
                                        <th>Quantity</th>
                                    </tr>
                                    <c:forEach items="${favoriteProductsList}" var="current" varStatus="loop">
                                        <tr class="row1">
                                            <td class="td"><input type="text" value="${current.product.product_id}" readonly="readonly" name="orderItems[${loop.index}].product.product_id"  style="width: 2em"  /></td>
                                            <td ><c:out value="${current.product.selling_name}" /></td>
                                            <td><input type="number" name="orderItems[${loop.index}].quantity" tabindex="${loop.index}" onkeyup="colorChange(this)" onclick="colorChange(this)"  class="quantity" style="width: 4em"></td>

                                            <!-- <td><button onclick="addHtmlTableRow();">Add To Order</button></td> -->

                                        </tr>
                                    </c:forEach>

                                </table>




                                <br>
                                <button type="submit" class="btn btn-primary">ΑΠΟΘΗΚΕΥΣΗ</button>

                            </form:form>
                        </div>
                    </div>
                </div>
            </div>




            <script>
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
                    var parentRow = a.parentNode.parentNode;
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


                //code for moving focus by pressin enter. you need to set tab index in corresponding input "number" html element
                var inputs = document.querySelectorAll("input");
                for (var i = 0; i < inputs.length; i++) {
                    inputs[i].addEventListener("keypress", function (e) {
                        if (e.which == 13) {
                            e.preventDefault();
                            var nextInput = document.querySelectorAll('[tabIndex="' + (this.tabIndex + 1) + '"]');
                            if (nextInput.length === 0) {
                                nextInput = document.querySelectorAll('[tabIndex="1"]');
                            }
                            nextInput[0].focus();
                        }
                    })
                }

            </script>

    </body>
</html>
