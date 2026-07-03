<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Arial, sans-serif;
        }

        body {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            padding: 20px;
        }

        .card {
            background: #ffffff;
            width: 100%;
            max-width: 400px;
            border-radius: 14px;
            padding: 40px 35px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.25);
            animation: fadeIn 0.4s ease-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(15px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 6px;
            font-size: 26px;
        }

        .subtitle {
            text-align: center;
            color: #888;
            font-size: 14px;
            margin-bottom: 25px;
        }

        .error-message {
            background: #fdecea;
            color: #c0392b;
            border: 1px solid #f5c6cb;
            padding: 10px 14px;
            border-radius: 8px;
            font-size: 14px;
            margin-bottom: 20px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            font-size: 13px;
            font-weight: 600;
            color: #555;
            margin-bottom: 6px;
            letter-spacing: 0.3px;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px 14px;
            border: 1.5px solid #e0e0e0;
            border-radius: 8px;
            font-size: 14px;
            outline: none;
            transition: border-color 0.2s ease, box-shadow 0.2s ease;
        }

        input:focus {
            border-color: #764ba2;
            box-shadow: 0 0 0 3px rgba(118, 75, 162, 0.15);
        }

        button {
            width: 100%;
            padding: 13px;
            margin-top: 8px;
            border: none;
            border-radius: 8px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: #fff;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: opacity 0.2s ease, transform 0.1s ease;
        }

        button:hover {
            opacity: 0.92;
        }

        button:active {
            transform: scale(0.98);
        }

        .footer-text {
            text-align: center;
            margin-top: 22px;
            font-size: 14px;
            color: #666;
        }

        .footer-text a {
            color: #764ba2;
            font-weight: 600;
            text-decoration: none;
        }

        .footer-text a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="card">

    <h2>Create Account</h2>
    <p class="subtitle">Sign up to get started</p>

    <%
    String error = (String) request.getAttribute("error");

    if(error != null){
    %>

    <div class="error-message"><%= error %></div>

    <%
    }
    %>

    <form action="register" method="post">

        <div class="form-group">
            <label>Username</label>
            <input type="text" name="username" required>
        </div>

        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" required>
        </div>

        <div class="form-group">
            <label>Password</label>
            <input type="password" name="password" required>
        </div>

        <button type="submit">Register</button>

    </form>

    <p class="footer-text">
        Already have an account? <a href="login.jsp">Login</a>
    </p>

</div>

</body>
</html>