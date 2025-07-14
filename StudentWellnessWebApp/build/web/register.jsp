<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <div class="container">
        <header class="header">
            <h2>
                Register for Wellness System
            </h2>
        </header>
        <main class="content">
            <form action="Register" method="post" class="registerForm">
                <div class="formGroup">
                    <label>Student Number:</label><br>
                    <input type="text" name="student_number" required class="formInput">
                </div>
                <div class="form-group">
                    <label>Name:</label><br>
                    <input type="text" name="name" required class="formInput">
                </div>
                <div class="form-group">
                    <label>Surname:</label><br>
                    <input type="text" name="surname" required class="formInput">
                </div>
                <div class="form-group">
                    <label>Email:</label><br>
                    <input type="email" name="email" required class="formInput">
                </div>
                <div class="form-group">
                    <label>Phone:</label><br>
                    <input type="text" name="phone" required class="formInput">
                </div>
                <div class="form-group">
                    <label>Password:</label><br>
                    <input type="password" name="password" required class="formInput">
                </div>
                <input type="submit" value="Register" class="btn">
            </form>
        </main>
    </div>
    <%
        String message = request.getParameter("msg");
        String error = request.getParameter("error");
        if (message != null) {
    %>
        <div class="statusMessage success">
            <p><%= message %></p>
        </div>
    <%
        } else if (error != null) {
    %>
        <div class="statusMessage error">
            <p><%= error %></p>
        </div>
    <%
                }
     %>
</body>
</html>
