<%@ page import="com.himamanth.pastebin.model.Paste"%>

<%
Paste paste = (Paste) request.getAttribute("paste");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Paste</title>
</head>

<body>

<h2>Edit Paste</h2>

<form action="editPaste" method="post">

<input type="hidden"
       name="pasteId"
       value="<%= paste.getPasteId() %>">

Title

<br>

<input type="text"
       name="title"
       value="<%= paste.getTitle() %>"
       required>

<br><br>

Content

<br>

<textarea name="content"
rows="12"
cols="70"><%= paste.getContent() %></textarea>

<br><br>

Visibility

<select name="visibility">

<option value="PUBLIC"
<%= paste.getVisibility().equals("PUBLIC")
? "selected" : "" %>>
Public
</option>

<option value="PRIVATE"
<%= paste.getVisibility().equals("PRIVATE")
? "selected" : "" %>>
Private
</option>

<option value="UNLISTED"
<%= paste.getVisibility().equals("UNLISTED")
? "selected" : "" %>>
Unlisted
</option>

</select>

<br><br>

Category ID

<input type="number"
name="categoryId"
value="<%= paste.getCategory().getCategoryId() %>">

<br><br>

<button type="submit">

Update Paste

</button>

</form>

</body>
</html>