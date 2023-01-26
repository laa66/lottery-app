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
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/login-style.css?">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?">
            </head>

            <body class="main">
                <main class="form-signin">
                    <form:form action="${pageContext.request.contextPath}/authenticateUser" method="POST">
                      <div class="dice-container"><img src="${pageContext.request.contextPath}/resources/image/square-dice.png" alt="Dice" style="width:128px;height:128px;"/></div>
                      <br>
                      <p class="sign-title">Please sign in</p>
                  
                      <div class="form-floating">
                        <input name="username" type="username" class="form-control" id="floatingInput" placeholder="Username">
                        <label for="floatingInput">Email address</label>
                      </div>
                      <br>
                      <div class="form-floating">
                        <input name="password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
                        <label for="floatingPassword">Password</label>
                      </div>
                      <br>
        
                      </div>
                      <button class="header-button" id="sign-button" type="submit">Sign in</button>
                    </form:form>
                  </main>
            </body>

            </html>