package de.ina.ina_p_platen.articles;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/articles-servlet")
public class ArticlesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        ArticlesHelper articlesHelper;
        if (session.getAttribute("articles-helper") == null)
            articlesHelper = new ArticlesHelper();
        else
            articlesHelper = (ArticlesHelper)session.getAttribute("articles-helper");

        String method = request.getParameter("_method");

        switch (method) {

            case "ADD":
                articlesHelper.doPost(request, response);
                break;

            case "DELETE":
                articlesHelper.doDelete(request, response);
                break;

            case "CHANGE":
                articlesHelper.doChange(request, response);
                break;
        }
    }
}
