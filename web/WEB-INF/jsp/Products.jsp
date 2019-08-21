<%-- 
    Document   : Products
    Created on : Aug 21, 2019, 4:00:59 PM
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

        <title>MAIN PAGE</title>


        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">



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
                                    <a class="nav-link active" href="${pageContext.request.contextPath}/Products.htm">ΠΡΟΪΟΝΤΑ</a>
                                </li>
                            </ul>
                            <ul class="nav navbar-nav ml-auto w-100 justify-content-end">
                                <li class="nav-item">

                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Logout.htm">ΑΠΟΣΥΝΔΕΣΗ</a>
                                </li>
                            </ul>
                        </div>
                    </nav>


                </div>
            </div>
        </div>
    </body>
</html>
