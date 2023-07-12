<%@ page import="de.ina.ina_p_platen.articles.ArticleBean" %>
<%@ page import="java.util.ArrayList" %>
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

    <jsp:include page="/snippets/Toolbar.jsp" />

    <%
        ArrayList<ArticleBean> shoppingCard = (ArrayList<ArticleBean>) session.getAttribute("shopping-card");
    %>

    <% if (shoppingCard != null && !shoppingCard.isEmpty()) { %>
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

    <% } else { %>

    <div style="display: flex;justify-content: center;justify-items: center">
        <h3>Der Warenkorb ist leer.</h3>
    </div>

    <% } %>

</body>
</html>