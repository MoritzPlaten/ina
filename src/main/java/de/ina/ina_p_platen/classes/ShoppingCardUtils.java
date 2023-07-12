package de.ina.ina_p_platen.classes;

import de.ina.ina_p_platen.articles.ArticleBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;

public class ShoppingCardUtils {

    public static void initializeShoppingCard(HttpServletRequest request) {

        HttpSession session = request.getSession();
        ArrayList<ArticleBean> shoppingCard = (ArrayList<ArticleBean>) session.getAttribute("shopping-card");

        if (shoppingCard == null) {

            ArrayList<ArticleBean> newShoppingCard = new ArrayList<>();
            session.setAttribute("shopping-card", newShoppingCard);
        }
    }

    public static boolean isArticleAvailable(ArrayList<ArticleBean> articles, int articleId, int desiredAmount) {

        ArticleBean article = ArticlesUtils.getArticleByID(articles, articleId);
        return -1 < (article.getAmount() - desiredAmount);
    }

    public static boolean isArticleInShoppingCard(ArrayList<ArticleBean> shoppingCard, int articleId) {

        ArticleBean article = ArticlesUtils.getArticleByID(shoppingCard, articleId);
        return article != null;
    }

    public static void deleteArticleByID(ArrayList<ArticleBean> inputList, int articleId) {

        ArticleBean article = ArticlesUtils.getArticleByID(inputList, articleId);
        inputList.remove(article);
    }
}
