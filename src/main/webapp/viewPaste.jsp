<%@ page import="com.himamanth.pastebin.model.Paste"%>

<%
Paste paste =
(Paste)request.getAttribute("paste");
%>

<!DOCTYPE html>

<html>

<head>

<title>

<%= paste.getTitle() %>

</title>

</head>

<body>

<h2>

<%= paste.getTitle() %>

</h2>

<hr>

<pre>

<%= paste.getContent() %>

</pre>

<hr>

Visibility :

<%= paste.getVisibility() %>

<br>

Created :

<%= paste.getCreatedAt() %>

</body>

</html>