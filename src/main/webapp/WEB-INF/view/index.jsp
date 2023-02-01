<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
        <%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Lottery Web App</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
                    rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD"
                    crossorigin="anonymous">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?version=43">
            </head>

            <body class="main">

                <div class="header">

                    <div class="header-content header-logo">
                        <h1>Lottery Web App</h1>
                    </div>

                    <div class="header-content header-hello">
                        <p>
                            <security:authorize access="hasAnyRole('USER', 'ADMIN')">
                                Hello,
                                <security:authentication property="principal.username" />
                            </security:authorize>
                        </p>

                    </div>

                   
                    <div class="header-content header-login">
                        <security:authorize access="isAuthenticated()">
                            <div class="dropdown header-button-logout">
                                <button class="header-button dropdown-toggle" type="button" id="dropdownMenu2" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                  User - <security:authentication property="principal.username"/>
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">
                                  <button class="dropdown-item" type="button">Settings</button>
                                  <form:form action="${pageContext.request.contextPath}/logout" method="POST">
                                        <button class="dropdown-item" type="submit">Logout</button>
                                </form:form>
                                </div>
                              </div>
                           
                        </security:authorize>

                        <security:authorize access="!isAuthenticated()">
                            <button class="header-button header-button-login"
                                onclick="window.location.href='${pageContext.request.contextPath}/join/login';return false;">Login</button>
                            <button class="header-button header-button-sign"
                                onclick="window.location.href='${pageContext.request.contextPath}/join/signup';return false;">Sign Up</button>
                        </security:authorize>
                    </div>

                </div>

                <div class="body">

                    <div class="body-title">
                        <p>Today Generated numbers</p>
                    </div>

                    <div class="numbers">

                        <ul class="list">
                            <c:forEach var="number" items="${lastNumbers.numbers}">
                                <li class="list-item">
                                    <c:out value="${number}" />
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                    <!-- Show different section based on user action -->
                    <!-- Block 'YOUR NUMBERS' section and 'PLAY THE LOTTERY' section if user is not logged in -->

                    <div class="numbers-history-title">

                        <div>

                            <security:authorize access="isAuthenticated()">
                                <div class="section section-2">
                                    <p onclick="showSectionWithId('play-lottery')">Play the lottery</p>
                                </div>
                            </security:authorize>

                            <div class="section section-3">
                                <p onclick="showSectionWithId('historical-numbers')">Historical numbers</p>
                            </div>

                            <security:authorize access="isAuthenticated()">
                                <div class="section section-1">
                                    <p onclick="showSectionWithId('your-numbers')">Your numbers</p>
                                </div>
                            </security:authorize>

                        </div>

                        <div id="temp-div" class="numbers-history">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">Lottery Date</th>
                                        <th scope="col">Numbers</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="drawResult" items="${allNumbers}">
                                        <tr>
                                            <td>
                                                <c:out value="${drawResult.dateString}" />
                                            </td>
                                            <td>
                                                <c:forEach var="historicalNumber" items="${drawResult.numbers}">
                                                    ${historicalNumber}
                                                </c:forEach>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>


                        <div id="historical-numbers" class="numbers-history" style="display: none;">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">Lottery Date</th>
                                        <th scope="col">Numbers</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="drawResult" items="${allNumbers}">
                                        <tr>
                                            <td>
                                                <c:out value="${drawResult.dateString}" />
                                            </td>
                                            <td>
                                                <c:forEach var="historicalNumber" items="${drawResult.numbers}">
                                                    ${historicalNumber}
                                                </c:forEach>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                        </div>

                        <security:authorize access="isAuthenticated()">
                        <div id="your-numbers" class="numbers-history" style="display: none">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">Purchase Date</th>
                                        <th scope="col">Lottery Date</th>
                                        <th scope="col">Numbers</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ticket" items="${userLastTickets}">
                                        <tr>
                                            <td>
                                                <c:out value="${ticket.date}" />
                                            </td>
                                            <td>
                                                <c:out value="${ticket.drawDate}" />

                                            </td>
                                            <td>
                                                <c:forEach var="number" items="${ticket.numbers}">
                                                    ${number}
                                                </c:forEach>
                                            </td>
                                        
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        </security:authorize>

                        <security:authorize access="isAuthenticated()">
                            <div id="play-lottery" class="numbers-history" style="display: none">

                                <form:form action="${pageContext.request.contextPath}/user/saveTicket/${loggedUserId}" modelAttribute="ticketNumbers" method="POST">
                                    <p>Enter <i style="color: goldenrod;">6</i> numbers and submit them to participate in
                                        the draw</p>
                                    <div class="row g-3">
                                        <div class="col-sm">
                                            <form:input path="field1" type="number" min="0" max="100" class="form-control"/>
                                        </div>
                                        <div class="col-sm">
                                            <form:input path="field2" type="number" min="0" max="100" class="form-control"/>
                                        </div>
                                        <div class="col-sm">
                                            <form:input path="field3" type="number" min="0" max="100" class="form-control"/>
                                        </div>
                                        <div class="col-sm">
                                            <form:input path="field4" type="number" min="0" max="100" class="form-control"/>
                                        </div>
                                        <div class="col-sm">
                                            <form:input path="field5" type="number" min="0" max="100" class="form-control"/>
                                        </div>
                                        <div class="col-sm">
                                            <form:input path="field6" type="number" min="0" max="100" class="form-control"/>
                                        </div>
                                    </div>
                                    <br>
                                    <button class="header-button" type="submit">Submit</button>
                                </form:form>
                            </div>
                        </security:authorize>

                        <script>

                            function showSectionWithId(divId) {
                                document.getElementById('temp-div').innerHTML = document.getElementById(divId).innerHTML;
                            }

                        </script>

                        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
                            integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
                            crossorigin="anonymous"></script>
                        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"
                            integrity="sha384-mQ93GR66B00ZXjt0YO5KlohRA5SY2XofN4zfuZxLkoj1gXtW8ANNCe9d5Y3eG5eD"
                            crossorigin="anonymous"></script>
                    </div>
                </div>
            </body>

            </html>