package de.ina.ina_p_platen.classes.shoppingCard;

import de.ina.ina_p_platen.articles.ArticleBean;
import de.ina.ina_p_platen.classes.article.Articles;
import de.ina.ina_p_platen.classes.article.ArticlesUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

public class ShoppingCardUtils {

    /**
     * @param session Get Session
     */
    public static void initializeShoppingCard(HttpSession session) {

        Articles shoppingCard = (Articles) session.getAttribute("shopping-card");

        if (shoppingCard == null) {

            Articles newShoppingCard = new Articles();
            session.setAttribute("shopping-card", newShoppingCard);
        }
    }

    /**
     * @param articles Die Liste in der gesucht werden soll
     * @param articleId Die Artikel ID von dem Artikel der überprüft werden soll
     * @param desiredAmount Die gewünschte Anzahl, die mit der vorhandenen Anzahl geprüft wird
     * @return Ein boolean, der angibt ob die gewünschte Anzahl vorhanden ist
     */
    public static boolean isArticleAvailable(ArrayList<ArticleBean> articles, int articleId, int desiredAmount) {

        ArticleBean article = ArticlesUtils.getArticleByID(articles, articleId);
        return -1 < (article.getAmount() - desiredAmount);
    }

    /**
     * @param shoppingCard Die Liste in der gesucht wird
     * @param articleId Die Artikel ID vom Artikel der gesucht werden soll
     * @return Ein boolean, der angibt ob der Artikel in der Liste vorhanden ist
     */
    public static boolean isArticleInShoppingCard(ArrayList<ArticleBean> shoppingCard, int articleId) {

        ArticleBean article = ArticlesUtils.getArticleByID(shoppingCard, articleId);
        return article != null;
    }

    /**
     * @param inputList Die Liste in, der der Artikel gelöscht werden soll
     * @param articleId Die Artikel ID vom Artikel der gelöscht werden soll
     */
    public static void deleteArticleByID(ArrayList<ArticleBean> inputList, int articleId) {

        ArticleBean article = ArticlesUtils.getArticleByID(inputList, articleId);
        inputList.remove(article);
    }
}
