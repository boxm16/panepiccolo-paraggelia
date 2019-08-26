<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>ΚΑΝΟΥΡΓΙΟ ΠΡΟΪΟΝ</title>


        <style>


        </style>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js" integrity="sha256-T0Vest3yCU7pafRw9r+settMBX6JkKN06dqBnpQ8d30=" crossorigin="anonymous"></script>

    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-sm">


                    <nav class="navbar navbar-light navbar-expand-md bg-primary justify-content-center">
                        <a href="${pageContext.request.contextPath}/AdminMainPage.htm" class="navbar-brand d-flex w-50 mr-auto">ΠΑΡΑΓΓΕΛΙΕΣ</a>
                        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsingNavbar3">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="navbar-collapse collapse w-100" id="collapsingNavbar3">
                            <ul class="navbar-nav w-100 justify-content-center">
                                <li class="nav-item">
                                    <a class="nav-link active" href="${pageContext.request.contextPath}/Customers.htm">ΠΕΛΑΤΕΣ</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Products.htm"><b>ΠΡΟΪΟΝΤΑ</b></a>
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
                    <hr>
                    <h3>ΚΑΤΑΧΩΡΗΣΕ ΚΑΙΝΟΥΡΓΙΟ ΠΡΟΪΟΝΤ</h3>
                    <hr>
                    <form action="${pageContext.request.contextPath}/createNewProductHandling.htm" method="POST" modelAttribute="product" >

                        <label>SELLING NAME</label><br/> 
                        <input type="text" name="selling_name" required style="width:30em"/><br/>
                        <label>BAKING NAME</label><br/> 
                        <input type="text" name="baking_name" required style="width:30em"/><br/>
                        <label>SELLING UNIT</label> <br/>
                        <input type="number" name="selling_unit" required style="width:3em"/><br/>
                        <label>BAKING UNIT</label> <br/>
                        <input type="number" name="baking_unit" required style="width:3em"/><br/>
                        <br/>
                        <input type="submit" value="ΑΠΟΘΗΚΕΥΣΗ" />
                    </form>

                    <div class="container">
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
