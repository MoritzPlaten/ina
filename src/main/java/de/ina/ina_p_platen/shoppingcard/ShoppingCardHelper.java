package de.ina.ina_p_platen.shoppingcard;

import de.ina.ina_p_platen.articles.ArticleBean;
import de.ina.ina_p_platen.classes.ArticlesUtils;
import de.ina.ina_p_platen.classes.ShoppingCardUtils;
import de.ina.ina_p_platen.classes.TypUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;

public class ShoppingCardHelper {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("shopping-card-helper") == null)
            session.setAttribute("shopping-card-helper", this);

        Enumeration<String> parameterNames = request.getParameterNames();
        parameterNames.nextElement();

        String getArticleID = request.getParameter(parameterNames.nextElement());
        String getDesiredAmount = request.getParameter(parameterNames.nextElement());

        int articleID = TypUtils.toInt(getArticleID);
        int desiredAmount = TypUtils.toInt(getDesiredAmount);

        ArrayList<ArticleBean> shoppingCard = (ArrayList<ArticleBean>) session.getAttribute("shopping-card");
        ArrayList<ArticleBean> articles = (ArrayList<ArticleBean>) servletContext.getAttribute("articles");

        synchronized (servletContext) {

            boolean error = false;

            ArticleBean article = ArticlesUtils.getArticleByID(articles, articleID);

            //Fehler, wenn die gewünschte Artikelanzahl nicht mit der vorhandenen Artikelanzahl übereinstimmt
            if (!ShoppingCardUtils.isArticleAvailable(articles, articleID, desiredAmount)) {

                error = true;
                response.sendRedirect(request.getContextPath() + "/articles?message=notavailable");
            }

            //Wenn der Artikel in dem Warenkorb ist + kein Fehler aufgetreten ist
            if (ShoppingCardUtils.isArticleInShoppingCard(shoppingCard, articleID) && !error) {

                //Die Anzahl der Artikelstücke wird hier in der Artikelliste geupdated
                int newAmountInArticleList = article.getAmount() - desiredAmount;
                ArticlesUtils.updateArticleAmount(articles, articleID, newAmountInArticleList);
                servletContext.setAttribute("articles", articles);

                //Die Anzahl der Artikelstücke wird hier in dem Warenkorb geupdated
                ArticleBean shoppingCardArticle = ArticlesUtils.getArticleByID(shoppingCard, articleID);
                int newAmountInShoppingCard = shoppingCardArticle.getAmount() + desiredAmount;
                ArticlesUtils.updateArticleAmount(shoppingCard, articleID, newAmountInShoppingCard);
                session.setAttribute("shopping-card", shoppingCard);

                response.sendRedirect(request.getContextPath() + "/articles");
            }
            // Wenn der Artikel nicht im Warenkorb vorhanden ist + kein Fehler aufgetreten ist
            else if (!ShoppingCardUtils.isArticleInShoppingCard(shoppingCard, articleID) && !error) {

                //Die Anzahl der Artikelstücke wird hier in der Artikelliste geupdated
                int newAmountInArticleList = article.getAmount() - desiredAmount;
                ArticlesUtils.updateArticleAmount(articles, articleID, newAmountInArticleList);
                servletContext.setAttribute("articles", articles);

                //Artikel wird hier in dem Warenkorb hinzugefügt
                ArticleBean addArticle = new ArticleBean();
                addArticle.setID(article.getID());
                addArticle.setName(article.getName());
                addArticle.setAmount(desiredAmount);
                shoppingCard.add(addArticle);

                session.setAttribute("shopping-card", shoppingCard);
                response.sendRedirect(request.getContextPath() + "/articles");
            }
        }
    }

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("shopping-card-helper") == null)
            session.setAttribute("shopping-card-helper", this);

        Enumeration<String> parameterNames = request.getParameterNames();
        parameterNames.nextElement();

        String getArticleID = request.getParameter(parameterNames.nextElement());

        int articleID = TypUtils.toInt(getArticleID);

        synchronized (servletContext) {

            ArrayList<ArticleBean> shoppingCard = (ArrayList<ArticleBean>) session.getAttribute("shopping-card");
            ArrayList<ArticleBean> articles = (ArrayList<ArticleBean>) servletContext.getAttribute("articles");

            ArticleBean articleInShoppingCard = ArticlesUtils.getArticleByID(shoppingCard, articleID);
            ArticleBean articleInArticle = ArticlesUtils.getArticleByID(articles, articleID);

            ShoppingCardUtils.deleteArticleByID(shoppingCard, articleID);
            session.setAttribute("shopping-card", shoppingCard);

            ArticlesUtils.updateArticleAmount(articles, articleID, articleInArticle.getAmount() + articleInShoppingCard.getAmount());
            servletContext.setAttribute("articles", articles);
            response.sendRedirect(request.getContextPath() + "/shopping-card");
        }
    }
}