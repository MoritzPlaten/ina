package de.ina.ina_p_platen.articles;

import de.ina.ina_p_platen.classes.ArticlesUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

public class ArticlesHelper {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("articles-helper") == null)
            session.setAttribute("articles-helper", this);

        synchronized (servletContext) {

            ArrayList<ArticleBean> articles = (ArrayList<ArticleBean>) servletContext.getAttribute("articles");

            String articleName = request.getParameter("articleName");
            String articleAmount = request.getParameter("articleAmount");

            int articleAmountInt = 0; // Standardwert, falls der Parameter nicht vorhanden oder ungültig ist

            if (articleAmount != null && !articleAmount.isEmpty()) {
                try {
                    articleAmountInt = Integer.parseInt(articleAmount);
                } catch (NumberFormatException e) {
                    // Fehlerbehandlung, wenn der String keine gültige Zahl ist
                }
            }

            if (ArticlesUtils.isArticleExists(articles, articleName)) {

                ArticlesUtils.updateArticleAmount(articles, articleName, articleAmountInt);
                servletContext.setAttribute("articles", articles);
                response.sendRedirect(request.getContextPath() + "/articles");
            } else {

                ArticleBean articleBean = new ArticleBean();
                articleBean.setName(articleName);
                articleBean.setAmount(articleAmountInt);

                articles.add(articleBean);
                servletContext.setAttribute("articles", articles);
                response.sendRedirect(request.getContextPath() + "/articles");
            }
        }
    }
}
