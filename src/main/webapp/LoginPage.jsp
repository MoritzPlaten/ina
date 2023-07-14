<%@ page import="de.ina.ina_p_platen.classes.message.MessageUtils" %>
<%@ page import="de.ina.ina_p_platen.classes.message.Message" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shop: Login</title>

    <style>
        body {
            background-color: #f2f2f2;
            font-family: Arial, sans-serif;
        }

        .container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            padding-left: 40px;
            padding-right: 40px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 20px;
        }

        h1 {
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .form-group button {
            width: 100%;
            padding: 10px;
            background-color: #4caf50;
            border: none;
            color: #fff;
            font-weight: bold;
            border-radius: 5px;
            cursor: pointer;
        }

        .form-group button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <%
        //receive messages from login
        Message message = MessageUtils.receiveMessage(request);
    %>

    <%-- Import toolbar --%>
    <jsp:include page="/snippets/Toolbar.jsp" />

    <div style="height: 40px"></div>
    <div style="width: 100%;display: flex;align-content: center;justify-content: center">
        <h2>Willkommen zum Login-Bereich:</h2>
    </div>

    <div style="height: 10px"></div>

    <%-- shows messages from login --%>
    <div style="display: flex;justify-items: center;justify-content: center">
        <h4 style="color: indianred"><%=message != null && message.isError() ? message.getMessage() : ""%></h4>
        <h4 style="color: seagreen"><%=message != null && !message.isError() ? message.getMessage() : ""%></h4>
    </div>

    <%-- login form --%>
    <div style="width: 100%;display: flex;justify-items: center;justify-content: center">

        <div class="container">

            <h1>Login</h1>

            <form action="${pageContext.request.contextPath}/login-servlet" method="POST">
                <input type="hidden" name="_method" value="LOGIN">
                <div class="form-group">
                    <label for="username">Benutzername</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Passwort</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <div class="form-group">
                    <button type="submit">Einloggen</button>
                </div>
            </form>
        </div>
</div>

<div style="height: 40px"></div>

</body>
</html>
