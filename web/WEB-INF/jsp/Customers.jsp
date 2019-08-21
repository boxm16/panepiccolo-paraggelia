
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
                                <li class="nav-item active">
                                    <a class="nav-link" href="${pageContext.request.contextPath}/Customers.htm">ΠΕΛΑΤΕΣ</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="//codeply.com">ΠΡΟΪΟΝΤΑ</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Link</a>
                                </li>
                            </ul>
                            <ul class="nav navbar-nav ml-auto w-100 justify-content-end">
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Right</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Right</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">Right</a>
                                </li>
                            </ul>
                        </div>
                    </nav>







                    <div class="form-group">
                        <form:form action="${pageContext.request.contextPath}/saveCustomerRating.htm" method="POST" modelAttribute="customersRatingTable"  >
                            <table class="table table-hover" id="myTable">
                                <thead>
                                    <tr>
                                        <th scope="col" class="text-center">RATING</th>
                                        <th scope="col">OFFICIAL NAME</th>
                                        <th scope="col">SECOND NAME</th>
                                        <th scope="col">USERNAME</th>

                                    </tr>
                                </thead>
                                <tbody>

                                    <!-- <tr id="1" >
                                         <td class="index">1</td>
                                         <td class="indexInput"><input type="text" name="abc" id="index" value="1"></td>
                                         <td>Mark</td>
                                         <td>Otto</td>
                                         <td>@mdo</td>
                                     </tr>
                                     <tr id="2">
                                         <td class="index">2</td>
                                         <td class="indexInput"><input type="text" name="abc" id="index" value="2"></td>
                                         <td>Jacob</td>
                                         <td>Thornton</td>
                                         <td>@fat</td>
                                     </tr>
                                     <tr id="3">
                                         <td class="index">3</td>
                                         <td class="indexInput"><input type="text" name="abc" id="index" value="3"></td>
                                         <td>Larry</td>
                                         <td>the Bird</td>
                                         <td>@twitter</td>
                                     </tr>
                                    -->
                                    <c:forEach items="${customersMap.values()}" var="cur" varStatus="loop">

                                        <tr>
                                            <td class="indexInput"> <input type="number"   value="${cur.user.rating}" name="customersRatingTable[${loop.index}].rating" readonly="readonly" style="width: 4em"></td>
                                            <td><b><c:out value="${cur.user.official_name}" /></b></td>
                                            <td><b><c:out value="${cur.user.second_name}" /></b></td>
                                            <td > <input type="text" value="${cur.user.username}" name="customersRatingTable[${loop.index}].username" readonly="readonly" style="border:none"></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <button type="submit" class="btn btn-primary">ΑΠΟΘΗΚΕΥΣΗ ΑΛΛΑΓΗΣ</button>
                        </form:form>
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

    </body>
</html>