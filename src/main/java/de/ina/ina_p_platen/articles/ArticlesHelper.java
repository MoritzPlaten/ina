package de.ina.ina_p_platen.articles;

import de.ina.ina_p_platen.classes.article.ArticlesUtils;
import de.ina.ina_p_platen.classes.TypUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ArticlesHelper {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("articles-helper") == null)
            session.setAttribute("articles-helper", this);

        synchronized (servletContext) {

            ArrayList<ArticleBean> articles = (ArrayList<ArticleBean>) servletContext.getAttribute("articles");

            Enumeration<String> parameterNames = request.getParameterNames();
            parameterNames.nextElement();

            String getArticleName = request.getParameter(parameterNames.nextElement());
            String getArticleAmount = request.getParameter(parameterNames.nextElement());

            int articleAmount = TypUtils.toInt(getArticleAmount);

            if (ArticlesUtils.isArticleNameExists(articles, getArticleName)) {

                int getArticleID = ArticlesUtils.getArticleIDByName(articles, getArticleName);
                ArticleBean article = ArticlesUtils.getArticleByID(articles, getArticleID);

                int newAmount = article.getAmount() + articleAmount;

                ArrayList<ArticleBean> updateArticle = ArticlesUtils.updateArticleAmount(articles, getArticleID, newAmount);

                servletContext.setAttribute("articles", updateArticle);

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

    //TODO: Testen
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("articles-helper") == null)
            session.setAttribute("articles-helper", this);

        synchronized (servletContext) {

            ArrayList<ArticleBean> articles = (ArrayList<ArticleBean>) servletContext.getAttribute("articles");
            ArrayList<ArticleBean> shoppingCard = (ArrayList<ArticleBean>) session.getAttribute("shopping-card");

            Enumeration<String> parameterNames = request.getParameterNames();
            parameterNames.nextElement();

            String getArticleName = request.getParameter(parameterNames.nextElement());

            //get article
            int articleID = ArticlesUtils.getArticleIDByName(articles, getArticleName);
            ArticleBean article = ArticlesUtils.getArticleByID(articles, articleID);

            //update article list
            articles.remove(article);
            servletContext.setAttribute("articles", articles);

            //update shopping card list
            shoppingCard.remove(article);
            session.setAttribute("shopping-card", shoppingCard);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
            dispatcher.forward(request, response);
        }
    }

    ///TODO: Hier
    public void doChange(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}
