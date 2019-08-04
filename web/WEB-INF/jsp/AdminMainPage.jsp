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

        <title>MAIN PAGE</title>


        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">



    </head>
    <body>

        <div class="container">

            <div class="col-sm">
                <div class="table-responsive">
                    <h1>MAIN PAGE!</h1>
                    <a  href="${pageContext.request.contextPath}/OrderedItemsList_ActiveOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΤΕΜΑΧΙΩΝ ΑΝΑΛΥΤΙΚΑ(ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> <br>
                    <hr>
                    <a  href="${pageContext.request.contextPath}/LockedOrdersPage.htm">ΔΕΣ ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ</a> <br>
                    23:00, ΠΑΡΑΓΓΕΛΙΕΣ ΠΟΥ ΕΙΝΑΙ ΝΑ ΠΑΡΑΔΟΘΟΥΝ ΤΗΝ ΕΠΟΜΕΝΗ ΜΕΡΑ, ΚΛΕΙΔΩΝΟΥΝ. ΔΕΝ ΥΠΑΡΧΕΙ ΔΥΝΑΤΟΤΗΤΑ ΑΛΛΑΓΗΣ (Η ΑΚΥΡΩΣΗΣ) ΠΑΡΑΓΓΕΛΙΑΣ ΜΕΣΩ ΣΥΣΤΗΜΑΤΟΣ. ΜΟΝΟ ΤΗΛΕΦΩΝΙΚΑ<br>
                    <a  href="${pageContext.request.contextPath}/OrderedItemsList_LockedOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΤΕΜΑΧΙΩΝ ΑΝΑΛΥΤΙΚΑ(ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> <br>

                    <hr>
                    <a  href="${pageContext.request.contextPath}/LabelsList_ActiveOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΗΜΕΡΟΜΗΝΙΩΝ ΑΝΑΛΥΤΙΚΑ (ΕΝΕΡΓΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> <br>
                    <a  href="${pageContext.request.contextPath}/LabelsList_LockedOrders.htm">ΔΕΣ ΣΥΝΟΛΟ ΗΜΕΡΟΜΗΝΙΩΝ ΑΝΑΛΥΤΙΚΑ (ΚΛΕΙΔΩΜΕΝΕΣ ΠΑΡΑΓΓΕΛΙΕΣ)</a> <br>
                    <hr>
                    <ul class="list-group">

                        <c:forEach items="${filledCustomersList}" var="current" varStatus="loop">
                            <li class="list-group-item">
                                <div>
                                    <table id="customerTable" class="table-hover " border="5" width="100%">
                                        <thead>
                                            <tr>
                                                <td width="80%"><h2 style="color:red;"><b><c:out value="${current.value.user.rating})${current.value.user.official_name}" /></b></h2></td>

                                                <td width="20%"><h2><b><c:out value="${current.value.user.second_name}" /></b></h2>  </td>
                                            </tr>
                                        </thead>
                                        <c:forEach items="${current.value.ordersList}" var="cur" varStatus="loop">

                                            <tr bgcolor="fuchsia" >

                                           <!-- <c:out value="${cur.creation_time}" />/ -->
                                                <td>Due-Day <b><c:out value="${cur.due_day}" /></b></td>
                                                <td> Order Number:<c:out value="${cur.order_id}" /></td>
                                            </tr>
                                            <tr>
                                                <td >
                                                    <table id="orderTable"    class="table-bordered table-hover ">

                                                        <c:forEach items="${cur.orderItems}" var="curr" varStatus="loop">
                                                            <tr class = "active" bgcolor="lime" >
                                                               <!-- <td><b><c:out value="${curr.product.product_id}" /></b></td>-->
                                                                <td><b><c:out value="${curr.product.selling_name}" /></b></td>
                                                                <td><b><c:out value="${curr.quantity}" /></b></td>

                                                            </tr>
                                                        </c:forEach>
                                                    </table>
                                                </td>
                                                <td> <button id="modifyButton"  class="btn btn-warning btn-sm btn-block"   onclick="location.href = '${pageContext.request.contextPath}/ModifyOrderPage.htm?order_id=${cur.order_id}'">ΔΙΟΡΘΩΣΗ ΠΑΡΑΓΓΕΛΙΑΣ</button></td>

                                            </tr>
                                        </c:forEach>
                                        <tfoot>
                                            <tr>
                                                <td><button   class="btn btn-primary btn-lg" onclick="location.href = '${pageContext.request.contextPath}/AdminOrderPage.htm?user_id=${current.key}'">ΝΕΑ ΠΑΡΑΓΓΕΛΙΑ</button></td>
                                                <td><button  class="btn btn-secondary btn-sm"  onclick="location.href = '${pageContext.request.contextPath}/FavoritProductsList.htm?user_id=${current.key}'">ΡΥΘΜΙΣΕΙΣ ΠΕΛΑΤΗ</button></td>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>


                            </li>
                        </c:forEach>

                    </ul>
                    <hr>
                </div>
            </div>
        </div>
        <script>


        </script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
