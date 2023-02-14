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
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/signup-style.css?version=34">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?version=34">
            </head>

            <body class="main">
                <main class="form-signin">
                    <form:form action="${pageContext.request.contextPath}/user/save/${loggedUserId}" modelAttribute="user" method="POST">
                      <p class="sign-title">Admin Panel</p>
                      
                      <div class="form-floating">
                        <form:input path="email" type="text" class="form-control" id="floatingInput" placeholder="name@example.com"/>
                        <label for="floatingInput">Email address</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <form:input path="firstName" type="text" class="form-control" id="floatingPassword" placeholder="First name"/>
                        <label for="floatingPassword">First name</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <form:input path="lastName" type="text" class="form-control" id="floatingInput" placeholder="Last name"/>
                        <label for="floatingInput">Last name</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <form:input path="username" type="text" class="form-control" id="floatingInput" placeholder="username"/>
                        <label for="floatingInput">Username</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <form:input path="password" type="hidden" class="form-control" id="floatingInput" placeholder="password"/>
                      </div>
                      <div class="form-floating">
                        <form:input path="enabled" type="text" class="form-control" id="floatingInput" placeholder="password"/>
                        <label for="floatingInput">Enabled</label>
                      </div>
                      <form:input path="id" type="hidden"/>
                      <form:input path="birthDate" type="hidden"/>
                      <br>
                      <button class="header-button" id="sign-button" type="submit">Update</button>
                    
                    </form:form>
                    <c:url value="/user/delete/${user.id}" var="url">
                      <c:param name="loggedUserId" value="${loggedUserId}" />
                    </c:url>
                    <button class="header-button mt-3" id="sign-button" type="button" 
                            style="background-color: rgba(255, 0, 0, 0.548);" 
                            onclick="window.location.href='${url}'">Delete</button>
                    <div class="mt-4">
                       <a href="${pageContext.request.contextPath}/user/panel/${loggedUserId}" class="text-secondary"><p class="text-center">Back to the User panel</p></a>
                    </div>

                  </main>
            </body>

            </html>