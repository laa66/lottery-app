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
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?version=40">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/panel-style.css?version=41">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/signup-style.css?version=32">

            </head>

            <body class="main">

                <!-- header -->
                <header class="header">

                    <div class="header-content header-logo">
                        <h1 onclick="window.location.href='${pageContext.request.contextPath}/';return false;">Lottery Web App</h1>
                    </div>

                    <div class="header-content header-hello">
                        <p></p>
                    </div>


                    <div class="header-content header-login">
                        <security:authorize access="isAuthenticated()">
                            <div class="dropdown header-button-logout">
                                <button class="header-button dropdown-toggle" type="button" id="dropdownMenu2"
                                    data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    User -
                                    <security:authentication property="principal.username" />
                                </button>
                                <div class="dropdown-menu" aria-labelledby="dropdownMenu2">

                                    <form:form action="${pageContext.request.contextPath}/" method="GET">
                                        <button class="dropdown-item" type="submit">Home Page</button>
                                    </form:form>

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
                                onclick="window.location.href='${pageContext.request.contextPath}/join/signup';return false;">Sign
                                Up</button>
                        </security:authorize>
                    </div>

                </header>

                <!-- Body layout -->
                <div id="body-layout">
                    
                    <div class="column-left">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item" onclick="showSectionWithId('home')">Home</li>

                            <security:authorize access="hasRole('ROLE_ADMIN')">
                                <li class="list-group-item" onclick="showSectionWithId('user-management')">User management</li>
                                <li class="list-group-item" onclick="showSectionWithId('lottery-management')">Lottery management</li>

                            </security:authorize>

                            <li class="list-group-item" onclick="showSectionWithId('numbers-history')">Ticket history</li>
                            <li class="list-group-item" onclick="showSectionWithId('form-save')">User informations</li>
                            <li class="list-group-item" onclick="window.location.href='${pageContext.request.contextPath}/';return false;">Back to Home Page</li>

                          </ul>
                    </div>

                    <div class="column-right">
                        <div id="temp-div">
                          
                           
                        </div>
                    </div>
                </div>

                <!-- left bar options -->

                <!-- home summary -->
                <div class="hide" id="home">
                    <p class="section-title">Welcome back, ${userLogged.firstName}!</p>
                    <p>This is your summary.</p>
                    <p>In this panel you can track your statistics and access your account settings.</p>
                    <hr>
                    <div class="container container-stats">
                        <div class="stats-block">
                            <span>
                                <p style="font-size: 30px;">20</p>
                                <p style="font-size: 20px;">This is your stats block.</p>
                            </span>  
                          </div>
                      <div class="stats-block">
                        <span>
                            <p style="font-size: 30px;">20</p>
                            <p style="font-size: 20px;">This is your stats block.</p>
                        </span>  
                      </div>
                      <div class="stats-block">
                        <span>
                            <p style="font-size: 30px;">20</p>
                            <p style="font-size: 20px;">This is your stats block.</p>
                        </span>  
                      </div>
                      <div class="stats-block">
                        <span>
                            <p style="font-size: 30px;">20</p>
                            <p style="font-size: 20px;">This is your stats block.</p>
                        </span>  
                      </div>
                </div>
            </div>

                <!-- user management -->
                <div class="hide" id="user-management">
                    <p class="section-title">User management</p>
                    <p>Admin user management panel - here you can add new users, enable and delete their account</p>
                    <button class="header-button" type="submit">Add new user</button>
                    <hr>
                    <br>

                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">First name</th>
                                <th scope="col">Last name</th>
                                <th scope="col">Username</th>
                                <th scope="col">Birthdate</th>
                                <th scope="col">Email</th>
                                <th scope="col">Enabled</th>
                                <th scope="col">Action</th>
                                

                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" items="${users}">
                                <tr>
                                    <td>
                                        <c:out value="${user.id}"/>
                                    </td>
                                    <td>
                                        <c:out value="${user.firstName}"/>
                                    </td>
                                    <td>
                                       <c:out value="${user.lastName}"/>
                                    </td>
                                    <td>
                                        <c:out value="${user.username}"/>
                                     </td>
                                     <td>
                                        <c:out value="${user.birthDate}"/>
                                     </td>
                                     <td>
                                        <c:out value="${user.email}"/>
                                     </td>
                                     <td>
                                        <select name="userEnabled" id="enabled">
                                            <option value="" disabled hidden selected>${user.enabled}</option>
                                            <option value="True">True</option>
                                            <option value="False">False</option>
                                        </select>
                                    </td>
                                     <td>
                                        Delete ${user.id}
                                     </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- lottery management -->
                <div class="hide" id="lottery-management">
                    <p class="section-title">Lottery management</p>
                    <p>Admin lottery management panel</p>
                    <button class="header-button" type="submit">Draw</button>
                    <br>
                    <i style="color: rgb(97, 97, 97);">Note - use this option only when necessary!</i>
                    <hr>
                    <br>


                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Date</th>
                                <th scope="col" colspan="6">Numbers</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="drawResult" items="${drawResults}">
                                <tr>
                                    <td>
                                        <c:out value="${drawResult.id}"/>
                                    </td>
                                    <td>
                                        <c:out value="${drawResult.date}"/>
                                    </td>
                                    <c:forEach var="number" items="${drawResult.numbers}">
                                            <td> ${number}</td>
                                    </c:forEach>                                   
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>


                <!-- ticket history -->
                <div class="hide" id="numbers-history">
                    <p class="section-title">Ticket history</p>
                    <p>Your lottery ticket history</p>
                    <hr>

                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Purchase Date</th>
                                <th scope="col">Lottery Date</th>
                                <th scope="col" colspan="6">Numbers</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ticket" items="${userHistory}">
                                <tr>
                                    <td>
                                        <c:out value="${ticket.date}" />
                                    </td>
                                    <td>
                                        <c:out value="${ticket.drawDate}" />

                                    </td>
                                    
                                        <c:forEach var="number" items="${ticket.numbers}">
                                            <td>${number}</td>
                                        </c:forEach>
                                   
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- user update form -->
                <div class="hide" id="form-save">
                   <div id="form-save-layout">
                    <p class="section-title">User details</p>
                    <p>Change your account details</p>
                <hr>
                    <form:form action="${pageContext.request.contextPath}/user/save" modelAttribute="userForm" method="POST">
              
                    <form:errors path = "email" class="error" />
                    <div class="form-floating">
                      <form:input path="email" type="text" class="form-control" id="floatingInput" placeholder="name@example.com"/>
                      <label for="floatingInput">Email address</label>
                    </div>
                    <br>
                    <div class="row gx-2 w-100 ms-0">
                      <div class="col-6">
                          <form:errors path = "firstName" class="error"/>
                          <div class="form-floating">
                            <form:input path="firstName" type="text" class="form-control" id="floatingPassword" placeholder="First name"/>
                            <label for="floatingPassword">First name</label>
                          </div>
                      </div>
                      <div class="col-6">
                          <form:errors path = "lastName" class="error"/>
                          <div class="form-floating">
                            <form:input path="lastName" type="text" class="form-control" id="floatingInput" placeholder="Last name"/>
                            <label for="floatingInput">Last name</label>
                          </div>
                      </div>
                    </div>
                    <br>
                    <form:errors path = "birthDate" class="error"/>
                    <div class="form-floating">
                      <form:input path="birthDate" type="date" class="form-control" id="floatingInput" placeholder="Birth date"/>
                      <label for="floatingInput">Birth date</label>
                    </div>
                    <br>
                    <form:errors path = "username" class="error"/>
                    <div class="form-floating">
                      <form:input path="username" type="text" class="form-control" id="floatingInput" placeholder="username"/>
                      <label for="floatingInput">Username</label>
                    </div>
                    <br>

                    <div class="row gx-2 w-100 ms-0">
                      <div class="col-6">
                          <form:errors path = "" class="error"/>
                          <div class="form-floating">
                            <form:input path="password" type="password" class="form-control" id="floatingInput" placeholder="password"/>
                            <label for="floatingInput">Password</label>
                          </div>
                      </div>
                      <div class="col-6">
                          <form:errors path = "confirmPassword" class="error"/>
                          <div class="form-floating">
                            <form:input path="confirmPassword" type="password" class="form-control" id="floatingInput" placeholder="password"/>
                            <label for="floatingInput">Confirm password</label>
                          </div>
                      </div>
                    </div>
                    <br>
                    <button class="header-button" id="sign-button" type="submit">Save</button>
               

                  </form:form>
                   </div>
                </div>

                <!-- JS Additional scripts -->
                <script>
                    document.getElementById('temp-div').innerHTML = document.getElementById('home').innerHTML;


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
            </body>

            </html>