package de.ina.ina_p_platen.classes;

import de.ina.ina_p_platen.articles.ArticleBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

public class ShoppingCardUtils {

    /**
     * @param request HttpServletRequest vom Servlet
     */
    public static void initializeShoppingCard(HttpServletRequest request) {

        HttpSession session = request.getSession();
        ArrayList<ArticleBean> shoppingCard = (ArrayList<ArticleBean>) session.getAttribute("shopping-card");

        if (shoppingCard == null) {

            ArrayList<ArticleBean> newShoppingCard = new ArrayList<>();
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
