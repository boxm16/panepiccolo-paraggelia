<%-- 
    Document   : ErroPage
    Created on : Jun 2, 2019, 4:43:48 AM
    Author     : boxm1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <h1>Error Page</h1>
        <a href="${pageContext.request.contextPath}/index.htm">GO HOME</a> <br>
        <hr>    
        ${message}
    </body>
</html>
