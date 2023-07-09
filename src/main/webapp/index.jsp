<%@ page import="de.ina.ina_p_platen.login.UserBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="de.ina.ina_p_platen.articles.ArticleBean" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shop - Made by Moritz</title>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        h1 {
            color: #333;
            text-align: center;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th {
            background-color: darkslategrey;
            color: #fff;
            padding: 10px;
            text-align: left;
        }

        td {
            padding: 10px;
            border-bottom: 1px solid #ccc;
        }

        form {
            display: inline-block;
        }

        input[type="number"] {
            width: 50px;
            padding: 5px;
            border: 1px solid #ccc;
        }

        button {
            background-color: darkslategrey;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
        }

        button:hover {
            background-color: #555;
        }

        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        input[type="submit"] {
            background-color: #333;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #555;
        }
    </style>

</head>
<body>

<%-- Initialisier die Startwerte --%>
<jsp:include page="/initialize" />

<%-- Toolbar for Home Screen --%>

<div style="display: flex;align-content: space-between;justify-content: space-between;border-bottom: black;border-bottom-width: thin;width: 100%">

    <div style="display: flex;justify-content: center;justify-items: center">
        <div style="width: 50px;"></div>
        <h2><a style="font-family: Bahnschrift,sans-serif;text-decoration: none;color: black" href="${pageContext.request.contextPath}/articles">Der Shop</a></h2>
    </div>
    <div style="display: flex;justify-content: center;align-content: center">
        <a style="font-family: Bahnschrift,sans-serif;margin: auto;color: black;text-decoration: none;padding: 10px;padding-right: 15px;padding-left: 15px;background-color: white;border: black 2px solid;border-radius: 20px" href="${pageContext.request.contextPath}/shopping-card">Warenkorb</a>
        <div style="width: 15px"></div>
        <%
            UserBean user = (UserBean) session.getAttribute("user");

            if (user == null) {
        %>
            <a style="font-family: Bahnschrift,sans-serif;margin: auto;color: white;text-decoration: none;padding: 10px;padding-right: 15px;padding-left: 15px;background-color: darkslategrey;border: black 2px solid;border-radius: 20px" href="${pageContext.request.contextPath}/register">Registrieren</a>
            <div style="width: 20px"></div>
        <% } else {%>
            <form style="display: flex;justify-content: center;justify-items: center" method="get" action="<% session.setAttribute("user", null); %>">
                <input style="font-family: Bahnschrift,sans-serif;margin: auto;color: black;text-decoration: none;padding: 10px;padding-right: 15px;padding-left: 15px;background-color: white;border: black 2px solid;border-radius: 20px" value="Logout" type="submit">
            </form>
            <div style="width: 15px"></div>
        <% } %>
    </div>
</div>

<%-- User Begrüßung --%>
<div style="height: 20px"></div>

<%
    if (user != null) {
%>
    <div style="display: flex;justify-items: center;justify-content: center">
        <h1>Willkommen <%=user.getUsername()%> in unserem Shop!</h1>
    </div>
<% } else { %>
    <div style="display: flex;justify-content: center;justify-items: center;">

        <div style="display: flex;flex-direction: row;flex-wrap: wrap;">
            <h1 style="width: 100%;text-align: center">Melde dich jetzt an!</h1>
            <a style="font-family: Bahnschrift,sans-serif;margin: auto;color: black;text-decoration: none;padding: 10px;padding-right: 50px;padding-left: 50px;background-color: white;border: black 2px solid;border-radius: 20px" href="${pageContext.request.contextPath}/login">Login</a>
        </div>

    </div>
<% } %>

<%-- Artikel Anzeige --%>

<div style="height: 60px"></div>

<%
    ServletContext servletContext = request.getServletContext();
    ArrayList<ArticleBean> articles = (ArrayList<ArticleBean>) servletContext.getAttribute("articles");

%>

<% if (articles != null) { %>
<table>
    <thead>
    <tr>
        <th>Artikel-Name</th>
        <th>Verfügbare Anzahl</th>
        <th>Ausgewählte Anzahl</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <% for (ArticleBean article : articles) { %>
    <tr>
        <td><%= article.getName() %></td>
        <td><%= article.getAmount() %></td>
        <td>
            <form action="${pageContext.request.contextPath}/shopping-card" method="post">
                <input type="hidden" name="articleId" value="<%= article.getName() %>">
                <input type="number" name="quantity" value="1" min="1">
            </form>
        </td>
        <td>
            <button type="submit">In den Warenkorb</button>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>

<% } %>

<%-- Artikel hinzufügen, nur für user1 --%>

<div style="height: 40px"></div>

<% if (user != null && user.getUsername() != null && user.getUsername().equals("user1")) { %>

<h2>Artikel hinzufügen</h2>

<form action="${pageContext.request.contextPath}/articles-servlet" method="post">
    <label for="articleName">Artikelname:</label>
    <input type="text" id="articleName" name="articleName" required>

    <div style="height: 10px"></div>

    <label for="articleAmount">Anzahl:</label>
    <input type="number" id="articleAmount" name="articleAmount" min="1" required>

    <div style="height: 10px"></div>

    <input type="submit" value="Artikel hinzufügen">
</form>
<% } %>

<div style="height: 40px"></div>

</body>
</html>