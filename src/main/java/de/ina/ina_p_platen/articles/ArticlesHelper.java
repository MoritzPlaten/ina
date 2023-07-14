package de.ina.ina_p_platen.articles;

import de.ina.ina_p_platen.classes.article.Articles;
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

            Articles articles = (Articles) servletContext.getAttribute("articles");

            Enumeration<String> parameterNames = request.getParameterNames();
            parameterNames.nextElement();

            String getArticleName = request.getParameter(parameterNames.nextElement());
            String getArticleAmount = request.getParameter(parameterNames.nextElement());

            int articleAmount = TypUtils.toInt(getArticleAmount);

            if (ArticlesUtils.isArticleNameExists(articles, getArticleName)) {

                int getArticleID = ArticlesUtils.getArticleIDByName(articles, getArticleName);
                ArticleBean article = ArticlesUtils.getArticleByID(articles, getArticleID);

                if (article != null) {
                    int newAmount = article.getAmount() + articleAmount;

                    ArticlesUtils.updateArticleAmount(articles, getArticleID, newAmount);
                    servletContext.setAttribute("articles", articles);
                }

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

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("articles-helper") == null)
            session.setAttribute("articles-helper", this);

        synchronized (servletContext) {

            Articles articles = (Articles) servletContext.getAttribute("articles");
            Articles shoppingCard = (Articles) session.getAttribute("shopping-card");

            Enumeration<String> parameterNames = request.getParameterNames();
            parameterNames.nextElement();

            String getArticleID = request.getParameter(parameterNames.nextElement());

            //get article
            int articleID = TypUtils.toInt(getArticleID);

            //update article list
            ArticleBean articleInArticles = ArticlesUtils.getArticleByID(articles, articleID);
            if (articleInArticles != null) {
                articles.remove(articleInArticles);
                servletContext.setAttribute("articles", articles);
            }

            //update shopping card list
            ArticleBean articleInShoppingCard = ArticlesUtils.getArticleByID(shoppingCard, articleID);
            if (articleInShoppingCard != null) {
                shoppingCard.remove(articleInShoppingCard);
                session.setAttribute("shopping-card", shoppingCard);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
            dispatcher.forward(request, response);
        }
    }

    public void doChange(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("articles-helper") == null)
            session.setAttribute("articles-helper", this);

        synchronized (servletContext) {

            Articles articles = (Articles) servletContext.getAttribute("articles");
            Articles shoppingCard = (Articles) session.getAttribute("shopping-card");

            Enumeration<String> parameterNames = request.getParameterNames();
            parameterNames.nextElement();

            String getArticleID = request.getParameter(parameterNames.nextElement());
            String getNewArticleAmount = request.getParameter(parameterNames.nextElement());

            //get article and amount which should be change
            int articleID = TypUtils.toInt(getArticleID);
            int newArticleAmount = TypUtils.toInt(getNewArticleAmount);

            //update article list
            ArticleBean articleInShoppingCard = ArticlesUtils.getArticleByID(shoppingCard, articleID);
            //article is in shopping card
            if (articleInShoppingCard != null) {

                //new article amount is not less than the article amount in shopping card
                //Dies müsste ansich nicht implementiert werden, sollte aber als logische Bedingung enthalten sein
                if (0 > (articleInShoppingCard.getAmount() - newArticleAmount)) {
                    ArticlesUtils.updateArticleAmount(articles, articleID, newArticleAmount);
                    servletContext.setAttribute("articles", articles);
                } else {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/articles?message=less");
                    dispatcher.forward(request, response);
                }
            } else {
                // article is not in shopping card
                ArticlesUtils.updateArticleAmount(articles, articleID, newArticleAmount);
                servletContext.setAttribute("articles", articles);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
            dispatcher.forward(request, response);
        }
    }
}
