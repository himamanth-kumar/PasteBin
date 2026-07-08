<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.himamanth.pastebin.model.User" %>
<%@ page import="com.himamanth.pastebin.model.Paste" %>
<%@ page import="com.himamanth.pastebin.util.TimeAgoUtil" %>
<%
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);

User user = (User) session.getAttribute("user");
if (user == null) {
    response.sendRedirect("login.jsp");
    return;
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Dashboard · Pastebin</title>
<style>
  :root {
    --color-bg: #f5f6fa;
    --color-surface: #ffffff;
    --color-border: #e3e5eb;
    --color-text: #1c1e26;
    --color-text-muted: #6b7080;
    --color-primary: #4f5bd5;
    --color-primary-hover: #3f4ac0;
    --color-danger: #e0455f;
    --color-danger-hover: #c8324a;
    --color-badge-public-bg: #e6f7ec;
    --color-badge-public-text: #1e8a4c;
    --color-badge-private-bg: #fdeaea;
    --color-badge-private-text: #c0392b;
    --radius-md: 10px;
    --radius-lg: 16px;
    --shadow-sm: 0 1px 2px rgba(20, 20, 43, 0.06);
    --shadow-md: 0 4px 16px rgba(20, 20, 43, 0.08);
    --font-sans: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
  }

  * { box-sizing: border-box; }

  body {
    margin: 0;
    font-family: var(--font-sans);
    background: var(--color-bg);
    color: var(--color-text);
  }

  .topbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 18px 32px;
    background: var(--color-surface);
    border-bottom: 1px solid var(--color-border);
  }

  .topbar h1 {
    font-size: 18px;
    font-weight: 600;
    margin: 0;
  }

  .topbar .welcome {
    color: var(--color-text-muted);
    font-weight: 400;
  }

  .topbar-actions {
    display: flex;
    gap: 10px;
    align-items: center;
  }

  .btn {
    display: inline-flex;
    align-items: center;
    gap: 6px;
    padding: 9px 16px;
    border-radius: var(--radius-md);
    font-size: 14px;
    font-weight: 500;
    text-decoration: none;
    border: 1px solid transparent;
    cursor: pointer;
    transition: background 0.15s ease, border-color 0.15s ease;
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

  .btn-danger-ghost {
    background: transparent;
    color: var(--color-danger);
    border: none;
    padding: 4px 8px;
    font-size: 13px;
  }
  .btn-danger-ghost:hover {
    background: var(--color-badge-private-bg);
    border-radius: var(--radius-md);
  }

  .container {
    max-width: 1000px;
    margin: 0 auto;
    padding: 32px;
  }

  .section-heading {
    display: flex;
    align-items: baseline;
    justify-content: space-between;
    margin-bottom: 16px;
  }

  .section-heading h2 {
    font-size: 16px;
    margin: 0;
    color: var(--color-text-muted);
    font-weight: 500;
  }

  .paste-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
    gap: 16px;
  }

  .paste-card {
    background: var(--color-surface);
    border: 1px solid var(--color-border);
    border-radius: var(--radius-lg);
    padding: 18px;
    box-shadow: var(--shadow-sm);
    display: flex;
    flex-direction: column;
    gap: 10px;
    transition: box-shadow 0.15s ease, transform 0.15s ease;
  }

  .paste-card:hover {
    box-shadow: var(--shadow-md);
    transform: translateY(-2px);
  }

  .paste-card-header {
    display: flex;
    justify-content: space-between;
    align-items: flex-start;
    gap: 8px;
  }

  .paste-title {
    font-size: 15px;
    font-weight: 600;
    word-break: break-word;
    margin: 0;
  }

  .badge {
    font-size: 11px;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.03em;
    padding: 3px 9px;
    border-radius: 999px;
    white-space: nowrap;
  }

  .badge-public {
    background: var(--color-badge-public-bg);
    color: var(--color-badge-public-text);
  }

  .badge-private {
    background: var(--color-badge-private-bg);
    color: var(--color-badge-private-text);
  }

  .paste-meta {
    font-size: 12.5px;
    color: var(--color-text-muted);
  }

  .paste-card-actions {
    display: flex;
    align-items: center;
    gap: 4px;
    margin-top: auto;
    padding-top: 8px;
    border-top: 1px solid var(--color-border);
  }

  .paste-card-actions a,
  .paste-card-actions form {
    margin: 0;
  }

  .paste-card-actions a {
    font-size: 13px;
    color: var(--color-primary);
    text-decoration: none;
    padding: 4px 8px;
    border-radius: var(--radius-md);
  }
  .paste-card-actions a:hover { background: var(--color-bg); }

  .delete-form {
    margin-left: auto;
  }

  .empty-state {
    text-align: center;
    padding: 64px 24px;
    color: var(--color-text-muted);
    background: var(--color-surface);
    border: 1px dashed var(--color-border);
    border-radius: var(--radius-lg);
  }

  .empty-state p {
    margin: 0 0 16px;
  }
</style>
</head>
<body>

<div class="topbar">
  <h1>Pastebin &nbsp;<span class="welcome"> Welcome  <c:out value="${sessionScope.user.username}"/></span></h1>
  <div class="topbar-actions">
    <a class="btn btn-primary" href="createPaste.jsp">+ New Paste</a>
    <a class="btn btn-secondary" href="logout">Logout</a>
  </div>
</div>

<div class="container">
  <div class="section-heading">
    <h2>Your Pastes</h2>
  </div>

  <c:choose>
    <c:when test="${empty pastes}">
      <div class="empty-state">
        <p>You haven't created any pastes yet.</p>
        <a class="btn btn-primary" href="createPaste.jsp">Create your first paste</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="paste-grid">
        <c:forEach var="paste" items="${pastes}">
          <div class="paste-card">
            <div class="paste-card-header">
              <p class="paste-title"><c:out value="${paste.title}"/></p>
              <c:choose>
                <c:when test="${paste.visibility == 'PUBLIC'}">
                  <span class="badge badge-public">Public</span>
                </c:when>
                <c:otherwise>
                  <span class="badge badge-private">Private</span>
                </c:otherwise>
              </c:choose>
            </div>

            <div class="paste-meta">
              Created
              <%
                // c:forEach exposes "paste" as a page-scoped attribute, so we can
                // safely retrieve it here. This is not user input, so no XSS concern —
                // it's only used to call a trusted static utility method that EL can't invoke directly.
                Paste currentPaste = (Paste) pageContext.getAttribute("paste");
              %>
              <%= TimeAgoUtil.timeAgo(currentPaste.getCreatedAt()) %>
            </div>

            <div class="paste-card-actions">
              <a href="${pageContext.request.contextPath}/p/${paste.publicId}">View</a>
              <a href="${pageContext.request.contextPath}/editPaste/${paste.publicId}">Edit
				</a>

              <form class="delete-form" action="deletePaste" method="post"
                    onsubmit="return confirm('Delete this paste? This cannot be undone.');">
                <input type="hidden" name="id" value="${paste.pasteId}">
                <button type="submit" class="btn-danger-ghost">Delete</button>
              </form>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</div>

</body>
</html>