<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Login</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <header class="header">
            
            <h2>Login to Wellness System</h2>
        </header>
        <main class="content">
            <form action="Login" method="post" class="loginForm">
                <div class="formGroup">
                    <label>Email:</label><br>
                    <input type="email" name="email" required class="formInput">
                </div>
                <div class="formGroup">
                    <label>Password:</label><br>
                    <input type="password" name="password" required class="formInput">
                </div>
                <input type="submit" value="Login" class="btn">
            </form>
        </main>
    </div>
    
<%
                HttpSession sessionLogin = request.getSession(false);
                String status = (sessionLogin != null) ? (String) sessionLogin.getAttribute("loginStatus") : null;
                if (status != null) {
            %>
                <div class="status-message success">
                    <textarea readonly rows="2" cols="50"><%= status %></textarea>
                </div>
            <%
                    sessionLogin.removeAttribute("loginStatus");
                }
                String error = request.getParameter("error");
                if ("true".equals(error)) {
                    String code = request.getParameter("code");
                    String message = "Invalid email or password.";
                    if ("db".equals(code)) {
                        String dbMessage = request.getParameter("msg");
                        message = (dbMessage != null) ? dbMessage : "A database error occurred.";
                    }
            %>
                <div class="status-message error">
                    <textarea readonly rows="4" cols="50"><%= message %></textarea>
                </div>
            <%
                }
            %>

    <br>
  
</body>
</html>
