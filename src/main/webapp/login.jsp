<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="card">

    <h2>Welcome back</h2>
    <p class="subtitle">Log in to your account</p>

    <c:if test="${not empty requestScope.error}">
        <div class="error-banner">
            <c:out value="${requestScope.error}" />
        </div>
    </c:if>

    <form action="login" method="post" novalidate>

        <div class="field">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="you@example.com" required autofocus>
        </div>

        <div class="field">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" placeholder="?????????" required>
        </div>

        <button type="submit" class="btn">Login</button>

    </form>

    <div class="footer-link">
        Don't have an account? <a href="register.jsp">Create one</a>
    </div>

</div>

</body>
</html>