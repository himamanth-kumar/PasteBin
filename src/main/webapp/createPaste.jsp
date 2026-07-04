<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Paste</title>
</head>
<body>

<h2>Create New Paste</h2>

<form action="createPaste" method="post">

Title <br>
<input type="text" name="title" required>

<br><br>

Content <br>
<textarea name="content"
          rows="10"
          cols="60"
          required></textarea>

<br><br>

Visibility

<select name="visibility">

<option value="PUBLIC">Public</option>

<option value="PRIVATE">Private</option>

<option value="UNLISTED">Unlisted</option>

</select>

<br><br>

Category

<select name="categoryId">

<option value="1">Programming</option>
<option value="2">Notes</option>
<option value="3">Personal</option>

</select>

<br><br>

<button type="submit">

Create Paste

</button>

</form>

</body>
</html>