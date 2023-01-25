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
                    <form>
                      <p class="sign-title">Registration</p>
                  
                      <div class="form-floating">
                        <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
                        <label for="floatingInput">Email address</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <input type="text" class="form-control" id="floatingPassword" placeholder="First name">
                        <label for="floatingPassword">First name</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <input type="text" class="form-control" id="floatingInput" placeholder="Last name">
                        <label for="floatingInput">Last name</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <input type="date" class="form-control" id="floatingInput" placeholder="Birth date">
                        <label for="floatingInput">Birth date</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <input type="text" class="form-control" id="floatingInput" placeholder="username">
                        <label for="floatingInput">Username</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <input type="password" class="form-control" id="floatingInput" placeholder="password">
                        <label for="floatingInput">Password</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <input type="password" class="form-control" id="floatingInput" placeholder="password">
                        <label for="floatingInput">Confirm password</label>
                      </div>
                      <br>
                      <button class="header-button" id="sign-button" type="submit">Sign Up</button>
                    </form>
                  </main>
            </body>

            </html>