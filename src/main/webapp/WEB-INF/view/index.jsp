<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en-US">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lottery Web App</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css?">
    </head>
    <body class="main">

        <div class="header">
            <h2>Lottery Web App</h2>
        </div>

        <div class="body">

            <div class="body-title">
                <p>Today Generated numbers</p>
            </div>

            <div class="numbers">

            <ul class="list">
                <c:forEach var="number" items="${lastNumbers.numbers}">
                    <li class="list-item"><c:out value="${number}"/></li>
                </c:forEach>
            </ul>
            </div>

            <div class="numbers-history-title">

                <p>Historical numbers</p>

                <div class="numbers-history">

                    <table class="basic-table">
                        <tr>
                            <th>Lottery date</th>
                            <th>Numbers</th>
                        </tr>
                        <c:forEach var="drawResult" items="${allNumbers}">
                            <tr>
                                <div class="lottery-date"><td><c:out value="${drawResult.dateString}"/></td></div>
                                <div class="historical-numbers"><td>
                                    <c:forEach var="historicalNumber" items="${drawResult.numbers}">
                                        ${historicalNumber}
                                    </c:forEach>
                                </td>
                            </tr>
                        </c:forEach>
                        <!-- Create table rows based on historical data fetched from DB -->
                    </table>

                </div>
            </div>
        </div>
    </body>
</html>