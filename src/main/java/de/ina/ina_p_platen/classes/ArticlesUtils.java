package de.ina.ina_p_platen.classes;

import de.ina.ina_p_platen.articles.ArticleBean;
import jakarta.servlet.ServletContext;

import java.util.ArrayList;

public class ArticlesUtils {

    public static void addStandardArticles(ServletContext servletContext) {

        synchronized (servletContext) {

            if (servletContext.getAttribute("articles") == null) {

                ArrayList<ArticleBean> articles = new ArrayList<>();

                ArticleBean articleBean1 = new ArticleBean();
                articleBean1.setName("T-Shirt");
                articleBean1.setAmount(5);
                articles.add(articleBean1);

                ArticleBean articleBean2 = new ArticleBean();
                articleBean2.setName("Pullover");
                articleBean2.setAmount(7);
                articles.add(articleBean2);

                ArticleBean articleBean3 = new ArticleBean();
                articleBean3.setName("Jeans");
                articleBean3.setAmount(11);
                articles.add(articleBean3);

                ArticleBean articleBean4 = new ArticleBean();
                articleBean4.setName("Socken");
                articleBean4.setAmount(4);
                articles.add(articleBean4);

                ArticleBean articleBean5 = new ArticleBean();
                articleBean5.setName("Schmuck");
                articleBean5.setAmount(6);
                articles.add(articleBean5);

                servletContext.setAttribute("articles", articles);
            }
        }
    }

    public static boolean isArticleExists(ArrayList<ArticleBean> articleList, String articleName) {
        for (ArticleBean article : articleList) {
            if (article.getName().equals(articleName)) {
                return true; // Artikel bereits vorhanden
            }
        }
        return false; // Artikel nicht gefunden
    }

    public static void updateArticleAmount(ArrayList<ArticleBean> articleList, String articleName, int newAmount) {
        for (ArticleBean article : articleList) {
            if (article.getName().equals(articleName)) {
                article.setAmount(newAmount); // Neue Anzahl setzen
                break;
            }
        }
    }
}
