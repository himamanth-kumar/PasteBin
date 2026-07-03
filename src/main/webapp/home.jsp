<%@ page import="com.himamanth.pastebin.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    // ---- Auth guard: must run before any HTML is written ----
    User user = (User) session.getAttribute("user");

    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Expose to JSTL/EL so we can use c:out (auto-escapes HTML -> prevents stored/reflected XSS)
    request.setAttribute("currentUser", user);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Home</title>
<link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="card">

    <div class="welcome-header">
        <div>
            <h2 style="margin-bottom:2px;">
                Welcome back,
                <c:out value="${currentUser.username}" />
            </h2>
            <p class="subtitle" style="margin:0;">Here's your account</p>
        </div>
        <div class="avatar">
            <%-- First letter of username as a simple avatar --%>
            <c:out value="${fn:substring(currentUser.username, 0, 1)}" />
        </div>
    </div>

    <div class="info-row">
        <span>Username</span>
        <strong><c:out value="${currentUser.username}" /></strong>
    </div>

    <div class="info-row">
        <span>Email</span>
        <strong><c:out value="${currentUser.email}" /></strong>
    </div>

    <a class="logout-btn" href="logout">Logout</a>

</div>

</body>
</html>