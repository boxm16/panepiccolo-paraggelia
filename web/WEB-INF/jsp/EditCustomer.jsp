<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="spring" %>

<!DOCTYPE html>
<html>
    <head>

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>ΑΛΛΑΓΗ ΣΤΟΙΧΕΙΩΝ  ΤΟΥ ΠΕΛΑΤΗ</title>

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


                    <spring:form modelAttribute="userCustomer" cssClass="login-form" action="${pageContext.request.contextPath}/editCustomerHandling.htm" method="POST">  
                        <div class="col-md-4 login-sec">
                            <h4 class="text-center">ΑΛΛΑΞΕ ΣΤΟΙΧΕΙΑ ΤΟΥ ΠΕΛΑΤΗ</h4>

                            <div>
                                <spring:input  hidden="hidden"  path="user_id" /> 
                            </div> 

                            <div class="form-group">
                                <spring:label path="username" cssClass="text-uppercase" >USERNAME</spring:label>
                                <spring:input  cssClass="form-control" path="username" placeholder="ΣΥΜΠΛΗΡΩΣΕ USERNAME"/> 
                                <spring:errors path="username" cssClass="error error-message"/>

                            </div>  
                            <div class="form-group">
                                <spring:label path="official_name" cssClass="text-uppercase" >OFFICIAL NAME</spring:label>
                                <spring:input  cssClass="form-control" path="official_name" /> 
                                <spring:errors path="official_name" cssClass="error error-message"/>

                            </div>  
                            <div class="form-group">
                                <spring:label path="second_name" cssClass="text-uppercase">SECOND NAME</spring:label>
                                <spring:input  cssClass="form-control" path="second_name"/> 
                                <spring:errors path="second_name" cssClass="error error-message"/>

                            </div>  
                            <div class="form-group">
                                <spring:label path="password" cssClass="text-uppercase">PASSWORD</spring:label>
                                <spring:input  cssClass="form-control" path="password" /> 
                                <spring:errors path="password" cssClass="error error-message"/>

                            </div>  


                            <button type="submit" id="submit" class="btn btn-login float-right" style="background-color: #0c00ff">Submit</button>

                        </div>


                    </spring:form> 

                </div>
            </div>           
        </div>               


        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    </body>
</html>
