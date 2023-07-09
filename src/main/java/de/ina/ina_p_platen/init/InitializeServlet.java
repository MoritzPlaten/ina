package de.ina.ina_p_platen.init;

import de.ina.ina_p_platen.classes.ArticlesUtils;
import de.ina.ina_p_platen.classes.UserUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import java.net.http.HttpRequest;

public class InitializeServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();

        ServletContext servletContext = getServletContext();

        UserUtils.addUserListAndStandardUsers(servletContext);
        ArticlesUtils.addStandardArticles(servletContext);
    }
}