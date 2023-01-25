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
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?version=34">
            </head>

            <body class="main">

                <div class="header">
                    <div class="header-content header-logo">
                        <h1>Lottery Web App</h1>
                    </div>
                    <div class="header-content header-hello">
                        <p>Hello, .... !</p>
                    </div>
                    <div class="header-content header-login"><button class="header-button header-button-login" onclick="window.location.href='${pageContext.request.contextPath}/join/login';return false;">Login</button>
                        <button class="header-button header-button-sign" onclick="window.location.href='${pageContext.request.contextPath}/join/signup';return false;">Sign Up</button></div>
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
                    <!-- Block YOUR NUMBERS section and Play the lottery section if user is not logged in -->

                    <div class="numbers-history-title">

                        <div>
                            <div class="section section-1">
                                <p onclick="show('your-numbers')">Your numbers</p>
                            </div>
                            <div class="section section-2">
                                <p onclick="show('play-lottery')">Play the lottery</p>
                            </div>
                            <div class="section section-3">
                                <p onclick="show('historical-numbers')">Historical numbers</p>
                            </div>
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

                        <div id="play-lottery" class="numbers-history" style="display: none">
                            <p>Enter <i style="color: goldenrod;">6</i> numbers and submit them to participate in the draw</p>
                            <div class="row g-3">
                                  <div class="col-sm">
                                    <input type="number" min=0 max="100" class="form-control" placeholder="1" aria-label="Zip">
                                  </div>
                                  <div class="col-sm">
                                    <input type="number" min=0 max="100" class="form-control" placeholder="2" aria-label="Zip">
                                  </div>
                                  <div class="col-sm">
                                    <input type="number" min=0 max="100" class="form-control" placeholder="3" aria-label="Zip">
                                  </div>
                                  <div class="col-sm">
                                    <input type="number" min=0 max="100" class="form-control" placeholder="4" aria-label="Zip">
                                  </div>
                                  <div class="col-sm">
                                    <input type="number" min=0 max="100" class="form-control" placeholder="5" aria-label="Zip">
                                  </div>
                                  <div class="col-sm">
                                    <input type="number" min=0 max="100" class="form-control" placeholder="6" aria-label="Zip">
                                  </div>
                              </div>
                              <br>
                              <button class="header-button">Submit</button>
                        </div>

                        <script>
                            function show(divId) {
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