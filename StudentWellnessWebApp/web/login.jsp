<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Login</title>
</head>
<body>
    <h2>Login to Wellness System</h2>

    <form action="Login" method="post">
        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <input type="submit" value="Login">
    </form>

    <br>

    <%
        // ✅ Show success message from session (e.g., Login successful!)
        HttpSession sessionLogin = request.getSession(false);
        String status = (sessionLogin != null) ? (String) sessionLogin.getAttribute("loginStatus") : null;
        if (status != null) {
    %>
        <textarea readonly rows="2" cols="50" style="color: green;"><%= status %></textarea>
    <%
            sessionLogin.removeAttribute("loginStatus"); // clear after displaying
        }

        // ❌ Show error messages if login failed or DB error occurred
        String error = request.getParameter("error");
        if ("true".equals(error)) {
            String code = request.getParameter("code");
            String message = "Invalid email or password.";
            if ("db".equals(code)) {
                String dbMessage = request.getParameter("msg");
                message = (dbMessage != null) ? dbMessage : "A database error occurred.";
            }
    %>
        <textarea readonly rows="4" cols="50" style="color: red;"><%= message %></textarea>
    <%
        }
    %>
</body>
</html>
