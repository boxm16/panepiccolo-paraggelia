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
<html lang="en">
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modify My Order Page</title>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>

        <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">

        <script type='text/javascript' src='//code.jquery.com/jquery-1.8.3.js'></script>

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker3.min.css">
        <script type='text/javascript' src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>

    </head>
    <body onload="yellowRow()">
        <div class="container">

            <div class='col-sm'>
                <div class="table-responsive">
                    <h1>Modify My Active Order</h1>

                    <a href="${pageContext.request.contextPath}/MyMainPage.htm">MY MAIN PAGE</a> <br>

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

                                        <!-- <td><button onclick="addHtmlTableRow();">Add To Order</button                        ></td> -->

                                    </tr>
                                </c:forEach>

                            </table>




                            <br>

                            <input type="submit" value="ΑΠΟΘΗΚΕΥΣΗ"/>
                        </form:form>
                    </div>
                </div>
            </div>


            <hr>
            <button onclick="location.href = '${pageContext.request.contextPath}/cancelOrder.htm?order_id=${order.order_id}'">ΑΚΥΡΩΣΗ ΠΑΡΑΓΓΕΛΙΑΣ</button>
            <hr>
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
    </body>
</html>