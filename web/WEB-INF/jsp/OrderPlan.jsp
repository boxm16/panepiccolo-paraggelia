<%-- 
    Document   : AutogeneratiorPage
    Created on : May 25, 2019, 3:11:17 PM
    Author     : Michail Sitmalidis
--%>


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Plan For Week</title>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.1/css/font-awesome.min.css">


    </head>
    <body onLoad="onLoadFunction()" >
        <div class="container">

            <div class="col-sm">
                <div class="table-responsive">

                    <h1>${customer.second_name}:Order Plan For Week</h1>
                    <a href="${pageContext.request.contextPath}/AdminMainPage.htm">GO HOME</a> <br>

                    <br>
                    <hr>
                    <div id="ActivationLink" >
                        <p id="message"> ${message} </p>
                        <a href="${pageContext.request.contextPath}/autogeneratorActivation.htm?user_id=${customer.user_id}">Activate  Autogeneration</a> <br>
                    </div>
                    <hr>
                    <div id="DeactivationLink">
                        <a href="${pageContext.request.contextPath}/autogeneratorDeactivation.htm?user_id=${customer.user_id}">Deactivate Autogeneration</a> <br>
                    </div>




                    <hr>
                   



                    <hr>
                    <button onclick="zero()">Click to 0</button>

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

                                        <!-- <td><button onclick="addHtmlTableRow();">Add To Order</button></td> -->

                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>




                        <br>
                        <input type="hidden" name="user_id" id="customer_id" value="${customer.user_id}" readonly="readonly">
                        <hr>
                        <p>
                            Select day(s) to make this form as standard Order Form
                        </p>

                        Day_1<input type="checkbox" name ="day_1" checked="checked" >
                        Day_2<input type="checkbox" name ="day_2" checked="checked" >
                        Day_3<input type="checkbox" name ="day_3" checked="checked" >
                        Day_4<input type="checkbox" name ="day_4" checked="checked" >
                        Day_5<input type="checkbox" name ="day_5" checked="checked" >
                        Day_6<input type="checkbox" name ="day_6" checked="checked" >
                        Day_7 <input type="checkbox" name ="day_7"  >
                        <br>
                        <hr>
                        <input type="submit" value="Save" />

                    </form:form>


                    <hr>


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
