package de.ina.ina_p_platen.articles;

import de.ina.ina_p_platen.classes.ArticlesUtils;
import de.ina.ina_p_platen.classes.UserUtils;
import de.ina.ina_p_platen.register.RegisterHelper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/articles-servlet")
public class ArticlesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        ArticlesHelper articlesHelper;
        if (session.getAttribute("articles-helper") == null)
            articlesHelper = new ArticlesHelper();
        else
            articlesHelper = (ArticlesHelper)session.getAttribute("articles-helper");

        articlesHelper.doPost(request, response);
    }
}
