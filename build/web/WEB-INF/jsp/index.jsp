<%-- 
    Document   : CustomerAuthorization
    Created on : Apr 7, 2019, 5:15:23 AM
    Author     : boxm1
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <p><h1>PANE PICCOLO ΠΑΡΑΓΓΕΛΙΑ</h1></p>
    <br>
    <hr>


    <form autocomplete="on" class="login-form" action="${pageContext.request.contextPath}/loginFormHandling.htm" method="POST">

        <div class="col-md-4 login-sec">
            <h2 class="text-center">Login Now</h2>
            <div class="form-group">
                <label for="exampleInputEmail1" class="text-uppercase">Username</label>
                <input class="form-control" id="username" name="username" required="required" type="text" placeholder="myusername"/>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1" class="text-uppercase">Password</label>
                <input class="form-control" id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO"/> 
            </div>
            <div class="error-message">${message}</div>
            <div class="form-check">

                <!--                                     <p class="login button"> 
                                                   <input type="submit" id="submit" value="Login" /> 
                                                </p>-->
                <button type="submit" id="submit" class="btn btn-login float-right" style="background-color: #0c00ff">Submit</button>
            </div>


        </div>   
    </form>
</body>
</html>
