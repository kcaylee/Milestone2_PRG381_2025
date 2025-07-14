<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
    <h2>Register for Wellness System</h2>

    <form action="Register" method="post">
        <label>Student Number:</label><br>
        <input type="text" name="student_number" required><br><br>

        <label>Name:</label><br>
        <input type="text" name="name" required><br><br>

        <label>Surname:</label><br>
        <input type="text" name="surname" required><br><br>

        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label>Phone:</label><br>
        <input type="text" name="phone" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <input type="submit" value="Register">
    </form>

    <%
        String message = request.getParameter("msg");
        String error = request.getParameter("error");
        if (message != null) {
    %>
        <p style="color:green;"><%= message %></p>
    <%
        } else if (error != null) {
    %>
        <p style="color:red;"><%= error %></p>
    <%
        }
    %>
</body>
</html>
