package de.ina.ina_p_platen.articles;

import de.ina.ina_p_platen.classes.ArticlesUtils;
import de.ina.ina_p_platen.classes.TypUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

public class ArticlesHelper {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("articles-helper") == null)
            session.setAttribute("articles-helper", this);

        synchronized (servletContext) {

            ArrayList<ArticleBean> articles = (ArrayList<ArticleBean>) servletContext.getAttribute("articles");

            String getArticleName = request.getParameter("articleName");
            String getArticleAmount = request.getParameter("articleAmount");

            int articleAmount = TypUtils.toInt(getArticleAmount);

            if (ArticlesUtils.isArticleNameExists(articles, getArticleName)) {

                int getArticleID = ArticlesUtils.getArticleIDByName(articles, getArticleName);

                ArticlesUtils.updateArticleAmount(articles, getArticleID, articleAmount);
                servletContext.setAttribute("articles", articles);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
                dispatcher.forward(request, response);
            } else {

                ArticleBean articleBean = new ArticleBean();
                articleBean.setID(ArticlesUtils.getNewArticleID(articles));
                articleBean.setName(getArticleName);
                articleBean.setAmount(articleAmount);

                articles.add(articleBean);
                servletContext.setAttribute("articles", articles);

                RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
                dispatcher.forward(request, response);
            }
        }
    }
}
