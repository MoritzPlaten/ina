<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Shop - Made by Moritz</title>
</head>
<body>

<%--
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
--%>

<div style="display: flex;align-content: space-between;justify-content: space-between;border-bottom: black;border-bottom-width: thin;width: 100%">

    <div style="display: flex;justify-content: center;justify-items: center">
        <div style="width: 50px;"></div>
        <h2 style="font-family: Bahnschrift,sans-serif">Der Shop</h2>
    </div>
    <div style="display: flex;justify-content: center;align-content: center">
        <a style="font-family: Bahnschrift,sans-serif;margin: auto;color: black;text-decoration: none;padding: 10px;padding-right: 15px;padding-left: 15px;background-color: white;border: black 2px solid;border-radius: 20px" href="${pageContext.request.contextPath}/shopping-card">Warenkorb</a>
        <div style="width: 15px"></div>
        <%
            if (session.getAttribute("user") == null) {
        %>
            <a style="font-family: Bahnschrift,sans-serif;margin: auto;color: black;text-decoration: none;padding: 10px;padding-right: 15px;padding-left: 15px;background-color: white;border: black 2px solid;border-radius: 20px" href="${pageContext.request.contextPath}/login">Login</a>
            <div style="width: 15px"></div>
            <a style="font-family: Bahnschrift,sans-serif;margin: auto;color: black;text-decoration: none;padding: 10px;padding-right: 15px;padding-left: 15px;background-color: white;border: black 2px solid;border-radius: 20px" href="${pageContext.request.contextPath}/register">Registrieren</a>
            <div style="width: 20px"></div>
        <% } else {%>
            <form style="display: flex;justify-content: center;justify-items: center" method="get" action="<% session.setAttribute("user", null); %>">
                <input style="font-family: Bahnschrift,sans-serif;margin: auto;color: black;text-decoration: none;padding: 10px;padding-right: 15px;padding-left: 15px;background-color: white;border: black 2px solid;border-radius: 20px" value="Logout" type="submit">
            </form>
            <div style="width: 15px"></div>
        <% } %>
    </div>
</div>

<%--
<table>
    <tr>
        <th>Bezeichnung</th>
        <th>Lagerbestand</th>
        <th></th>
    </tr>
    <c:forEach items="${articles}" var="article">
        <tr>
            <td>${article.name}</td>
            <td>${article.stock}</td>
            <td>
                <form action="AddToCart" method="post">
                    <input type="hidden" name="articleId" value="${article.id}" />
                    <input type="submit" value="In Warenkorb legen" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
--%>

</body>
</html>