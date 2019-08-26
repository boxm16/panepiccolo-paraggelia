<%-- 
    Document   : AddNewFavoriteProduct
    Created on : Apr 14, 2019, 8:52:18 AM
    Author     : boxm1
--%>
<%@page contentType="text/html"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


        <title>ADD NEW FAVORITE PRODUCT TO CUSTOMER</title>
    </head>
    <body>
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

                    <p>SELECT A PRODUCT FROM EXISTING LIST OR <a href="${pageContext.request.contextPath}/createNewProduct.htm">Create New PRODUCT</a></p>
                    <br>

                    <select id="product" name="product" onChange="selectProduct()" >
                        <option value="0">SELECT PRODUCT</option>
                        <c:forEach items="${productList}" var="productList">
                            <option  value="${productList.product_id}">${productList.baking_name}</option>
                        </c:forEach>
                    </select>
                    <br>
                    <br>
                    <hr>
                    <p>SELECT LABEL FOR THE PRODUCT</p>

                    <br>
                    <select id="label" name="label" onChange="selectLabel()" >

                        <c:forEach items="${labelList}" var="labelList">

                            <option  value="${labelList.label_id}">${labelList.label_description}</option>
                        </c:forEach>
                    </select>
                    <br>
                    <hr>
                    <spring:form modelAttribute="favoriteProduct"  action="${pageContext.request.contextPath}/AddNewFavoriteProductHandling.htm" method="POST">
                        <spring:input  path="user.user_id" value= "${favoriteProduct.user.user_id}" hidden="hidden"/>
                        <spring:input id="product_id" path="product.product_id" hidden="hidden" />
                        <spring:input id="label_id" path="label.label_id" hidden="hidden" value="1"/>
                        <label>product_X</label>
                        <spring:input type="number" id="product_X" path="product_X" value="1" style="width: 3em"/>
                        <label>:</label>
                        <spring:input type="number" id="X_label" path="X_label"  value="1" style="width: 3em"/>
                        <label>X_label</label>
                        <br><br>
                        <button type="submit" id="submit" class="btn btn-primary btn-lg" style="background-color: #0c00ff">Submit</button>

                    </spring:form>
                    <script>
                        function selectProduct() {
                            var productId = document.getElementById("product").value;
                            var product_id = document.getElementById("product_id");
                            product_id.setAttribute("value", productId);
                        }
                        function selectLabel() {
                            var labelId = document.getElementById("label").value;
                            var label_id = document.getElementById("label_id");
                            label_id.setAttribute("value", labelId);
                        }
                    </script>
                    </body>
                    </html>
