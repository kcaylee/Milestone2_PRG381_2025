<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
    <title>Student Dashboard</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">

</head>
<body>
    <div class="container">
            <header>
                <h2>
                    Student Wellness Dashboard
                </h2>
            </header>


        <main class="content">
            <div id="welcomeMessage">
                <h3>
                    Welcome, nameOfPerson where the email entered is used to identify the name and surname.
                </h3>
            </div>
            <form action="Logout" method="post" class="logoutForm">
                <input type="submit" value="Logout" class="btn">
            </form>

            <%
                    String error = request.getParameter("error");
                    if ("true".equals(error)) {
                %>
                    <div class="errMessage">
                        <p>Invalid email or password. Please try again.</p>
                    </div>
        </main>
    </div>
</body>
</html>
