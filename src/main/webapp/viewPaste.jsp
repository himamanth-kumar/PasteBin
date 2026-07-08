<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.himamanth.pastebin.model.Paste" %>
<%
Paste paste = (Paste) request.getAttribute("paste");
if (paste == null) {
    response.sendRedirect(request.getContextPath() + "/dashboard");
    return;
}
request.setAttribute("p", paste);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title><c:out value="${p.title}"/> · Pastebin</title>
<style>
  :root {
    --color-bg: #f5f6fa;
    --color-surface: #ffffff;
    --color-border: #e3e5eb;
    --color-text: #1c1e26;
    --color-text-muted: #6b7080;
    --color-primary: #4f5bd5;
    --color-primary-hover: #3f4ac0;
    --color-badge-public-bg: #e6f7ec;
    --color-badge-public-text: #1e8a4c;
    --color-badge-private-bg: #fdeaea;
    --color-badge-private-text: #c0392b;
    --color-code-bg: #1c1e26;
    --color-code-text: #e6e7eb;
    --radius-md: 10px;
    --radius-lg: 16px;
    --shadow-sm: 0 1px 2px rgba(20, 20, 43, 0.06);
    --shadow-md: 0 4px 16px rgba(20, 20, 43, 0.08);
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
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 18px 32px;
    background: var(--color-surface);
    border-bottom: 1px solid var(--color-border);
  }

  .topbar a.back {
    font-size: 14px;
    color: var(--color-text-muted);
    text-decoration: none;
    display: flex;
    align-items: center;
    gap: 6px;
  }
  .topbar a.back:hover { color: var(--color-primary); }

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
    transition: background 0.15s ease;
  }

  .btn-primary { background: var(--color-primary); color: #fff; }
  .btn-primary:hover { background: var(--color-primary-hover); }

  .btn-secondary {
    background: var(--color-surface);
    color: var(--color-text);
    border-color: var(--color-border);
  }
  .btn-secondary:hover { background: var(--color-bg); }

  .container {
    max-width: 860px;
    margin: 0 auto;
    padding: 32px;
  }

  .paste-card {
    background: var(--color-surface);
    border: 1px solid var(--color-border);
    border-radius: var(--radius-lg);
    box-shadow: var(--shadow-sm);
    overflow: hidden;
  }

  .paste-header {
    padding: 24px 28px 18px;
    border-bottom: 1px solid var(--color-border);
  }

  .paste-header-top {
    display: flex;
    align-items: flex-start;
    justify-content: space-between;
    gap: 12px;
  }

  .paste-title {
    font-size: 20px;
    font-weight: 700;
    margin: 0;
    word-break: break-word;
  }

  .badge {
    font-size: 11px;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.03em;
    padding: 3px 9px;
    border-radius: 999px;
    white-space: nowrap;
    flex-shrink: 0;
  }

  .badge-public { background: var(--color-badge-public-bg); color: var(--color-badge-public-text); }
  .badge-private { background: var(--color-badge-private-bg); color: var(--color-badge-private-text); }

  .paste-meta {
    display: flex;
    gap: 20px;
    flex-wrap: wrap;
    margin-top: 12px;
    font-size: 13px;
    color: var(--color-text-muted);
  }

  .paste-meta strong {
    color: var(--color-text);
    font-weight: 600;
  }

  .paste-body {
    position: relative;
  }

  .paste-body-actions {
    display: flex;
    justify-content: flex-end;
    gap: 8px;
    padding: 12px 16px 0;
  }

  .copy-btn {
    font-size: 12.5px;
    padding: 6px 12px;
    border-radius: var(--radius-md);
    border: 1px solid var(--color-border);
    background: var(--color-surface);
    color: var(--color-text-muted);
    cursor: pointer;
  }
  .copy-btn:hover { background: var(--color-bg); color: var(--color-text); }

  pre.paste-content {
    margin: 12px 16px 16px;
    padding: 18px;
    background: var(--color-code-bg);
    color: var(--color-code-text);
    border-radius: var(--radius-md);
    font-family: var(--font-mono);
    font-size: 13.5px;
    line-height: 1.55;
    overflow-x: auto;
    white-space: pre-wrap;
    word-break: break-word;
  }

  .footer-actions {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    margin-top: 20px;
  }
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
  <div class="paste-card">
    <div class="paste-header">
      <div class="paste-header-top">
        <h1 class="paste-title"><c:out value="${p.title}"/></h1>
        <c:choose>
          <c:when test="${p.visibility == 'PUBLIC'}">
            <span class="badge badge-public">Public</span>
          </c:when>
          <c:otherwise>
            <span class="badge badge-private">Private</span>
          </c:otherwise>
        </c:choose>
      </div>

      <div class="paste-meta">
        <span><strong>Created:</strong>
          <fmt:formatDate value="${p.createdAt}" pattern="dd MMM yyyy, hh:mm a"/>
        </span>
        <c:if test="${p.updatedAt != null}">
          <span><strong>Last updated:</strong>
            <fmt:formatDate value="${p.updatedAt}" pattern="dd MMM yyyy, hh:mm a"/>
          </span>
        </c:if>
      </div>
    </div>

    <div class="paste-body">
      <div class="paste-body-actions">
        <button type="button" class="copy-btn" onclick="copyContent()">Copy</button>
      </div>
      <pre class="paste-content" id="pasteContent"><c:out value="${p.content}"/></pre>
    </div>
  </div>

  <div class="footer-actions">
    <a class="btn btn-secondary" href="editPaste?id=${p.pasteId}">Edit</a>
    <a class="btn btn-primary"
   href="${pageContext.request.contextPath}/dashboard">
    Done
</a>
  </div>
</div>

<script>
  function copyContent() {
    var text = document.getElementById('pasteContent').innerText;
    navigator.clipboard.writeText(text).then(function () {
      var btn = document.querySelector('.copy-btn');
      var original = btn.textContent;
      btn.textContent = 'Copied!';
      setTimeout(function () { btn.textContent = original; }, 1500);
    });
  }
</script>

</body>
</html>