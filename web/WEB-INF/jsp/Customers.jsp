
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>

        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Order Page</title>


        <style>
            td:hover{
                cursor:move;
            }

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
                                    <a class="nav-link active" href="${pageContext.request.contextPath}/Customers.htm"><b>ΠΕΛΑΤΕΣ</b></a>
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

                    <nav class="navbar navbar-light bg-light">

                        <button type="button" class="btn btn-outline-success"  onclick="location.href = '${pageContext.request.contextPath}/createNewCustomer.htm'" >ΔΗΜΙΟΥΡΓΙΑ ΚΑΙΝΟΥΡΓΙΟΥ ΠΕΛΑΤΗ</button>

                    </nav>

                    <div class="container">

                        <div class="form-group">
                            <form:form action="${pageContext.request.contextPath}/saveCustomerRating.htm" method="POST" modelAttribute="customersRatingTable"  >
                                <table class="table table-hover" id="myTable">
                                    <thead>
                                        <tr>
                                            <th scope="col" class="text-center">RATING</th>
                                            <th scope="col">OFFICIAL NAME</th>
                                            <th scope="col">SECOND NAME</th>
                                            <th scope="col">USERNAME</th>
                                            <th scope="col">  <button type="submit" class="btn btn-sm btn-primary">ΑΠΟΘΗΚΕΥΣΗ ΑΛΛΑΓΗΣ<br> ΣΤΟ RATING</button>
                                            </th>
                                            <th scope="col"></th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${activeCustomers}" var="cur" varStatus="loop">

                                            <tr >
                                                <td class="indexInput"> <input type="number"   value="${cur.user.rating}" name="customersRatingTable[${loop.index}].rating" readonly="readonly" style="width: 3em; border:none;"></td>
                                                <td><b><c:out value="${cur.user.official_name}" /></b></td>
                                                <td><b><c:out value="${cur.user.second_name}" /></b></td>
                                                <td > <input type="text" value="${cur.user.username}" name="customersRatingTable[${loop.index}].username" readonly="readonly" style="border:none"></td>

                                                <td>
                                                    <button type="button" class="btn btn-sm btn-warning"  onclick="location.href = '${pageContext.request.contextPath}/editCustomer.htm?user_id=${cur.user.user_id}'" >ΑΛΛΑΓΗ ΣΤΟΙΧΕΙΩΝ ΠΕΛΑΤΗ</button>
                                                    <hr>
                                                    <button type="button" class="btn btn-sm btn-danger"  onclick="location.href = '${pageContext.request.contextPath}/deactivateUser.htm?user_id=${cur.user.user_id}'" >ΑΠΕΝΕΡΓΟΠΟΙΗΣΗ ΠΕΛΑΤΗ</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </form:form>

                        </div>
                        <hr>
                        <h3>ΑΠΕΝΕΡΓΟΠΟΙΗΜΕΝΟΙ ΠΕΛΑΤΕΣ</h3>
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th scope="col" >RATING</th>
                                    <th scope="col">OFFICIAL NAME</th>
                                    <th scope="col">SECOND NAME</th>
                                    <th scope="col">USERNAME</th>
                                    <th scope="col"> </th>
                                    <th scope="col"></th>

                                </tr>
                            </thead>

                            <c:forEach items="${deactivatedCustomers}" var="cur" varStatus="loop">
                                <tbody>
                                    <tr>
                                        <td class="indexInput"> <input type="number"   value="0"  readonly="readonly" style="width: 3em; border:none;"></td>
                                        <td><b><c:out value="${cur.user.official_name}" /></b></td>
                                        <td><b><c:out value="${cur.user.second_name}" /></b></td>
                                        <td > <input type="text" value="${cur.user.username}"  readonly="readonly" style="border:none"></td>

                                        <td>
                                            <button type="button" class="btn btn-sm btn-success"  onclick="location.href = '${pageContext.request.contextPath}/reactivateUser.htm?user_id=${cur.user.user_id}'" >ΕΠΑΝΕΝΕΡΓΟΠΟΙΗΣΗ ΠΕΛΑΤΗ</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </c:forEach>
                        </table>
                        <hr>
                    </div>
                </div>
            </div>
        </div>

        <script>
            var fixHelperModified = function (e, tr) {
                var $originals = tr.children();
                var $helper = tr.clone();
                $helper.children().each(function (index) {
                    $(this).width($originals.eq(index).width())
                });
                return $helper;
            },
                    updateIndex = function (e, ui) {
                        $('td.index', ui.item.parent()).each(function (i) {
                            $(this).html(i + 1);
                        });
                        $('input[type=number]', ui.item.parent()).each(function (i) {
                            $(this).val(i + 1);
                        });
                    };

            $("#myTable tbody").sortable({
                helper: fixHelperModified,
                stop: updateIndex
            }).disableSelection();

            $("tbody").sortable({
                distance: 5,
                delay: 100,
                opacity: 0.6,
                cursor: 'move',
                update: function () {}
            });

        </script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


    </body>
</html>