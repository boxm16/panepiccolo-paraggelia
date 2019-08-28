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

        <title>ΔΙΟΡΘΩΣΗ ΠΑΡΑΓΓΕΛΙΑΣ</title>


        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">



    </head>
    <body onload="yellowRow()">

        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <!--https://www.codeply.com/go/qhaBrcWp3v-->

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
                        <h1>Modify Active Order</h1>
                        <hr>
                        <button onclick="zero()">ΜΗΔΕΝΙΣΜΟΣ</button>
                        <hr>
                        <div class="form-group">
                            <form:form action="${pageContext.request.contextPath}/modifyOrder.htm" method="POST" modelAttribute="order"  >
                                <input type = "number" name = "order_id" value = "${order.order_id}" style = "width:1em" readonly = "readonly" hidden="hidden" >
                                <input type="number" name="orderer" value="${order.orderer}" style="width:1em" readonly="readonly" hidden="hidden">
                                <div class="input-group date">
                                    <input id="datepicker" type="date"  name="due_day" value="${order.due_day}" readonly="readonly" />
                                </div>

                                <table id="table" border="5" class="table">
                                    <tr>
                                        <th >ID</th>
                                        <th>Product Name</th>
                                        <th>Quantity</th>
                                    </tr>
                                    <c:forEach items="${order.orderItems}" var="current" varStatus="loop">
                                        <tr class="row1">
                                            <td class="td"><input type="text" value="${current.product.product_id}" readonly="readonly" name="orderItems[${loop.index}].product.product_id"  style="width: 2em"  /></td>
                                            <td><c:out value="${current.product.selling_name}" /></td>
                                            <td><input type="number" name="orderItems[${loop.index}].quantity" tabindex="${loop.index}" value="${current.quantity}" onkeyup="colorChange(this)" onclick="colorChange(this)"  class="quantity" style="width: 4em"></td>
                                        </tr>
                                    </c:forEach>

                                </table>

                                <br>

                                <input type="submit" value="ΑΠΟΘΗΚΕΥΣΗ"/>
                            </form:form>
                        </div>

                        <hr>
                        <button onclick="location.href = '${pageContext.request.contextPath}/cancelOrder.htm?order_id=${order.order_id}'">ΑΚΥΡΩΣΗ ΠΑΡΑΓΓΕΛΙΑΣ</button>
                        <hr>
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
            }
            function yellowRow() {

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


            //code for moving focus by pressing enter. you need to set tab index in corresponding input "number" html element
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
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    </body>
</html>
