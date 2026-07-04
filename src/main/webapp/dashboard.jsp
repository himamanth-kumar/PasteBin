<%@ page import="java.util.List"%>
<%@ page import="com.himamanth.pastebin.model.Paste"%>
<%@ page import="com.himamanth.pastebin.model.User"%>

<%
User user = (User) session.getAttribute("user");

if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}

List<Paste> pastes =
        (List<Paste>) request.getAttribute("pastes");
%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Dashboard</title>

</head>

<body>

<h2>

Welcome,

<%= user.getUsername() %>

</h2>

<a href="createPaste.jsp">

Create New Paste

</a>

|

<a href="logout">

Logout

</a>

<hr>

<table border="1" cellpadding="10">

<tr>

<th>Title</th>

<th>Visibility</th>

<th>Created</th>

<th>Actions</th>

</tr>

<%
for(Paste paste : pastes){
%>

<tr>

<td>

<%= paste.getTitle() %>

</td>

<td>

<%= paste.getVisibility() %>

</td>

<td>

<%= paste.getCreatedAt() %>

</td>

<td>

<a href="paste?id=<%= paste.getPasteId() %>">

View

</a>

|

<a href="editPaste?id=<%= paste.getPasteId() %>">

Edit

</a>

|

<a href="deletePaste?id=<%= paste.getPasteId() %>"
   onclick="return confirm('Delete this paste?');">
Delete
</a>

</td>

</tr>

<%
}
%>

</table>

</body>

</html>