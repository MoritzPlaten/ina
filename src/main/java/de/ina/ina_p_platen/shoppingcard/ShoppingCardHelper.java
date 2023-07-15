package de.ina.ina_p_platen.shoppingcard;

import de.ina.ina_p_platen.articles.ArticleBean;
import de.ina.ina_p_platen.classes.article.Articles;
import de.ina.ina_p_platen.classes.article.ArticlesUtils;
import de.ina.ina_p_platen.classes.shoppingCard.ShoppingCardUtils;
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

        Articles shoppingCard = (Articles) session.getAttribute("shopping-card");
        Articles articles = (Articles) servletContext.getAttribute("articles");

        synchronized (servletContext) {

            boolean error = false;

            ArticleBean article = ArticlesUtils.getArticleByID(articles, articleID);

            if (article != null) {

                //Fehler, wenn die gewünschte Artikelanzahl nicht mit der vorhandenen Artikelanzahl übereinstimmt
                if (!ShoppingCardUtils.isArticleAvailable(articles, articleID, desiredAmount)) {

                    error = true;
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/articles?message=notavailable");
                    dispatcher.forward(request, response);
                }

                //Wenn der Artikel in dem Warenkorb ist + kein Fehler aufgetreten ist
                if (ShoppingCardUtils.isArticleInShoppingCard(shoppingCard, articleID) && !error) {

                    //Die Anzahl der Artikelstücke wird hier in der Artikelliste geupdated
                    int newAmountInArticleList = article.getAmount() - desiredAmount;
                    ArticlesUtils.updateArticleAmount(articles, articleID, newAmountInArticleList);
                    servletContext.setAttribute("articles", articles);

                    //Die Anzahl der Artikelstücke wird hier in dem Warenkorb geupdated
                    ArticleBean shoppingCardArticle = ArticlesUtils.getArticleByID(shoppingCard, articleID);
                    if (shoppingCardArticle != null) {

                        int newAmountInShoppingCard = shoppingCardArticle.getAmount() + desiredAmount;
                        ArticlesUtils.updateArticleAmount(shoppingCard, articleID, newAmountInShoppingCard);
                        session.setAttribute("shopping-card", shoppingCard);
                    }

                    if (article.getAmount() == 0) {
                        articles.remove(article);
                        servletContext.setAttribute("articles", articles);
                    }

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
                    dispatcher.forward(request, response);
                }
                // Wenn der Artikel nicht im Warenkorb vorhanden ist + kein Fehler aufgetreten ist
                else if (!ShoppingCardUtils.isArticleInShoppingCard(shoppingCard, articleID) && !error) {

                    //Die Anzahl der Artikelstücke wird hier in der Artikelliste geupdated
                    int newAmountInArticleList = article.getAmount() - desiredAmount;
                    ArticlesUtils.updateArticleAmount(articles, articleID, newAmountInArticleList);
                    servletContext.setAttribute("articles", articles);

                    if (article.getAmount() == 0) {
                        articles.remove(article);
                        servletContext.setAttribute("articles", articles);
                    }

                    //Artikel wird hier in dem Warenkorb hinzugefügt
                    ArticleBean addArticle = new ArticleBean();
                    addArticle.setID(article.getID());
                    addArticle.setName(article.getName());
                    addArticle.setAmount(desiredAmount);
                    shoppingCard.add(addArticle);

                    session.setAttribute("shopping-card", shoppingCard);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
                    dispatcher.forward(request, response);
                }
            } else {

                RequestDispatcher dispatcher = request.getRequestDispatcher("/articles?message=idnotavailable");
                dispatcher.forward(request, response);
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

            Articles shoppingCard = (Articles) session.getAttribute("shopping-card");
            Articles articles = (Articles) servletContext.getAttribute("articles");

            ArticleBean articleInShoppingCard = ArticlesUtils.getArticleByID(shoppingCard, articleID);
            ArticleBean articleInArticle = ArticlesUtils.getArticleByID(articles, articleID);

            if (articleInArticle != null && articleInShoppingCard != null) {

                ArticlesUtils.updateArticleAmount(
                        articles,
                        articleID,
                        articleInArticle.getAmount() + articleInShoppingCard.getAmount()
                );
                servletContext.setAttribute("articles", articles);
            } else if (articleInArticle == null && articleInShoppingCard != null) {

                articles.add(articleInShoppingCard);
                servletContext.setAttribute("articles", articles);
            }

            ShoppingCardUtils.deleteArticleByID(shoppingCard, articleID);
            session.setAttribute("shopping-card", shoppingCard);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/shopping-card");
            dispatcher.forward(request, response);
        }
    }

    public void doBuy(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("shopping-card-helper") == null)
            session.setAttribute("shopping-card-helper", this);

        synchronized (servletContext) {

            Articles shoppingCard = (Articles) session.getAttribute("shopping-card");

            ArrayList<ArticleBean> removeArticles = new ArrayList<>();

            for (ArticleBean article : shoppingCard) {

                int articleID = article.getID();

                ArticleBean articleInShoppingCard = ArticlesUtils.getArticleByID(shoppingCard, articleID);
                removeArticles.add(articleInShoppingCard);
            }

            shoppingCard.removeAll(removeArticles);

            session.setAttribute("shopping-card", shoppingCard);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/articles?message=buyed");
            dispatcher.forward(request, response);
        }
    }
}