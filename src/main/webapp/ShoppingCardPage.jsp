<%@ page import="de.ina.ina_p_platen.articles.ArticleBean" %>
<%@ page import="de.ina.ina_p_platen.login.UserBean" %>
<%@ page import="de.ina.ina_p_platen.classes.article.Articles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Shop: Warenkorb</title>

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

    <%-- Shows toolbar --%>

    <jsp:include page="/snippets/Toolbar.jsp" />

    <%
        //receive shopping card list and the user
        Articles shoppingCard = (Articles) session.getAttribute("shopping-card");

        HttpSession getSession = request.getSession();
        UserBean user = (UserBean) getSession.getAttribute("user");
    %>

    <%-- show shopping card --%>
    <% if (shoppingCard != null && !shoppingCard.isEmpty()) { %>

        <%-- show buy-button, only when user is logged in --%>
        <% if (user != null) { %>

        <div style="height: 30px"></div>

        <div style="display: flex;justify-items: flex-end;justify-content: flex-end">

            <div>
                <h4>Kaufe jetzt den Warenkorb!</h4>
                <form style="display: grid;" action="${pageContext.request.contextPath}/shopping-card-servlet" method="post">
                    <input type="hidden" name="_method" value="BUY">
                    <input type="submit" value="Kaufen">
                </form>
            </div>
            <div style="width: 100px"></div>
        </div>

        <%-- Shows when user is not logged in --%>
        <% } else { %>

        <div style="height: 10px"></div>

        <div style="display: flex;justify-items: flex-end;justify-content: flex-end">

            <div>
                <a style="border: 2px black solid;border-radius: 20px;background-color: darkslategrey;text-decoration: none;color: white;padding: 10px;padding-left: 20px;padding-right: 20px;font-family: Bahnschrift, sans-serif" href="${pageContext.request.contextPath}/login">Zum Login</a>
            </div>
            <div style="width: 30px;"></div>
        </div>

        <div style="display: flex;justify-content: center;justify-items: center">
            <h3>Sie müssen sich anmelden, um den Warenkorb zu kaufen!</h3>
        </div>
        <% } %>

        <div style="height: 20px"></div>

        <%-- articles --%>
        <table>
            <thead>
            <tr>
                <th>Artikel-Name</th>
                <th>Verfügbare Anzahl</th>
                <th>Ausgewählte Anzahl</th>
            </tr>
            </thead>
            <tbody>
            <% for (ArticleBean article : shoppingCard) { %>
            <tr>
                <td><%= article.getName() %></td>
                <td><%= article.getAmount() %></td>
                <td>

                    <%-- delete articles from shopping card --%>
                    <form style="display: grid;" action="${pageContext.request.contextPath}/shopping-card-servlet" method="post">
                        <div style="display: flex; align-items: center;">
                            <input type="hidden" name="_method" value="DELETE">
                            <input type="hidden" name="articleId<%=article.getID()%>" value="<%=article.getID()%>">
                            <input type="submit" value="Delete">
                        </div>
                    </form>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>

    <%-- shows when the shopping card list is empty --%>
    <% } else { %>

    <div style="display: flex;justify-content: center;justify-items: center">
        <h3>Der Warenkorb ist leer.</h3>
    </div>

    <% } %>

</body>
</html>