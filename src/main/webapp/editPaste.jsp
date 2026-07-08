<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.himamanth.pastebin.model.User" %>
<%@ page import="com.himamanth.pastebin.model.Paste" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

User user = (User) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}

Paste paste = (Paste) request.getAttribute("paste");
if (paste == null) {
    response.sendRedirect("dashboard.jsp");
    return;
}
request.setAttribute("p", paste);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Paste · Pastebin</title>
<style>
  :root {
    --color-bg: #f5f6fa;
    --color-surface: #ffffff;
    --color-border: #e3e5eb;
    --color-text: #1c1e26;
    --color-text-muted: #6b7080;
    --color-primary: #4f5bd5;
    --color-primary-hover: #3f4ac0;
    --radius-md: 10px;
    --radius-lg: 16px;
    --shadow-sm: 0 1px 2px rgba(20, 20, 43, 0.06);
    --font-sans: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
    --font-mono: "SF Mono", Consolas, "Courier New", monospace;
  }

  * { box-sizing: border-box; }

  body {
    margin: 0;
    font-family: var(--font-sans);
    background: var(--color-bg);
    color: var(--color-text);
  }

  .topbar {
    padding: 18px 32px;
    background: var(--color-surface);
    border-bottom: 1px solid var(--color-border);
  }

  .topbar a.back {
    font-size: 14px;
    color: var(--color-text-muted);
    text-decoration: none;
  }
  .topbar a.back:hover { color: var(--color-primary); }

  .container {
    max-width: 640px;
    margin: 0 auto;
    padding: 32px;
  }

  h1 {
    font-size: 20px;
    margin: 0 0 20px;
  }

  .form-card {
    background: var(--color-surface);
    border: 1px solid var(--color-border);
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-sm);
    padding: 28px;
  }

  .field {
    margin-bottom: 20px;
  }

  .field:last-of-type {
    margin-bottom: 0;
  }

  label {
    display: block;
    font-size: 13.5px;
    font-weight: 600;
    margin-bottom: 6px;
    color: var(--color-text);
  }

  input[type="text"],
  input[type="number"],
  textarea,
  select {
    width: 100%;
    padding: 10px 12px;
    border: 1px solid var(--color-border);
    border-radius: var(--radius-md);
    font-size: 14px;
    font-family: var(--font-sans);
    background: var(--color-surface);
    color: var(--color-text);
    transition: border-color 0.15s ease;
  }

  textarea {
    font-family: var(--font-mono);
    font-size: 13.5px;
    resize: vertical;
    min-height: 220px;
    line-height: 1.5;
  }

  input:focus,
  textarea:focus,
  select:focus {
    outline: none;
    border-color: var(--color-primary);
  }

  .field-footer {
    display: flex;
    justify-content: flex-end;
    margin-top: 4px;
  }

  .char-count {
    font-size: 12px;
    color: var(--color-text-muted);
  }

  .row-split {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 16px;
  }

  .form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 24px;
  }

  .btn {
    display: inline-flex;
    align-items: center;
    padding: 10px 18px;
    border-radius: var(--radius-md);
    font-size: 14px;
    font-weight: 500;
    text-decoration: none;
    border: 1px solid transparent;
    cursor: pointer;
    transition: background 0.15s ease;
  }

  .btn-primary {
    background: var(--color-primary);
    color: #fff;
  }
  .btn-primary:hover { background: var(--color-primary-hover); }

  .btn-secondary {
    background: var(--color-surface);
    color: var(--color-text);
    border-color: var(--color-border);
  }
  .btn-secondary:hover { background: var(--color-bg); }
</style>
</head>
<body>

<div class="topbar">
  <a class="back"
   href="${pageContext.request.contextPath}/dashboard">
    &larr; Back to Dashboard
</a>
</div>

<div class="container">
  <h1>Edit Paste</h1>

  <div class="form-card">
    <form action="editPaste" method="post">

      <input type="hidden" name="pasteId" value="${p.pasteId}">

      <div class="field">
        <label for="title">Title</label>
        <input type="text" id="title" name="title" value="<c:out value="${p.title}"/>" required>
      </div>

      <div class="field">
        <label for="content">Content</label>
        <textarea id="content" name="content" oninput="updateCharCount()" required><c:out value="${p.content}"/></textarea>
        <div class="field-footer">
          <span class="char-count" id="charCount">0 characters</span>
        </div>
      </div>

      <div class="row-split">
        <div class="field">
          <label for="visibility">Visibility</label>
          <select id="visibility" name="visibility">
            <option value="PUBLIC" ${p.visibility == 'PUBLIC' ? 'selected' : ''}>Public</option>
            <option value="PRIVATE" ${p.visibility == 'PRIVATE' ? 'selected' : ''}>Private</option>
            <option value="UNLISTED" ${p.visibility == 'UNLISTED' ? 'selected' : ''}>Unlisted</option>
          </select>
        </div>

        <div class="field">
          <label for="categoryId">Category ID</label>
          <input type="number" id="categoryId" name="categoryId"
                 value="${p.category != null ? p.category.categoryId : ''}">
        </div>
      </div>

      <div class="form-actions">
        <a class="btn btn-secondary"
   href="${pageContext.request.contextPath}/p/${p.publicId}">
    Cancel
</a>
        <button type="submit" class="btn btn-primary">Update Paste</button>
      </div>

    </form>
  </div>
</div>

<script>
  function updateCharCount() {
    var content = document.getElementById('content').value;
    document.getElementById('charCount').textContent = content.length + ' characters';
  }
  // Initialize count on load since content is pre-filled
  document.addEventListener('DOMContentLoaded', updateCharCount);
</script>

</body>
</html>