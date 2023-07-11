package de.ina.ina_p_platen.classes;

import de.ina.ina_p_platen.articles.ArticleBean;
import jakarta.servlet.ServletContext;

import java.util.ArrayList;
import java.util.Objects;

public class ArticlesUtils {

    public static void addStandardArticles(ServletContext servletContext) {

        synchronized (servletContext) {

            if (servletContext.getAttribute("articles") == null) {

                ArrayList<ArticleBean> articles = new ArrayList<>();

                ArticleBean articleBean1 = new ArticleBean();
                articleBean1.setID(0);
                articleBean1.setName("T-Shirt");
                articleBean1.setAmount(5);
                articles.add(articleBean1);

                ArticleBean articleBean2 = new ArticleBean();
                articleBean2.setID(1);
                articleBean2.setName("Pullover");
                articleBean2.setAmount(7);
                articles.add(articleBean2);

                ArticleBean articleBean3 = new ArticleBean();
                articleBean3.setID(2);
                articleBean3.setName("Jeans");
                articleBean3.setAmount(11);
                articles.add(articleBean3);

                ArticleBean articleBean4 = new ArticleBean();
                articleBean4.setID(3);
                articleBean4.setName("Socken");
                articleBean4.setAmount(4);
                articles.add(articleBean4);

                ArticleBean articleBean5 = new ArticleBean();
                articleBean5.setID(4);
                articleBean5.setName("Schmuck");
                articleBean5.setAmount(6);
                articles.add(articleBean5);

                servletContext.setAttribute("articles", articles);
            }
        }
    }

    public static boolean isArticleNameExists(ArrayList<ArticleBean> inputList, String articleName) {
        for (ArticleBean article : inputList) {
            if (article.getName().equals(articleName)) {
                return true; // Artikel bereits vorhanden
            }
        }
        return false; // Artikel nicht gefunden
    }

    public static void updateArticleAmount(ArrayList<ArticleBean> inputList, int articleID, int newAmount) {
        for (ArticleBean article : inputList) {
            if (article.getID() == articleID) {
                article.setAmount(newAmount); // Neue Anzahl setzen
                break;
            }
        }
    }

    public static int getArticleIDByName(ArrayList<ArticleBean> articleList, String articleName) {

        int articleID = -1;

        for (ArticleBean article: articleList) {

            if (Objects.equals(article.getName(), articleName)) {
                articleID = article.getID();
                break;
            }
        }
        return articleID;
    }

    public static int getNewArticleID(ArrayList<ArticleBean> articleList) {

        int generatedID = 0;

        do {
            generatedID += 1;
        } while(!isIDAvailable(articleList, generatedID));

        return generatedID;
    }

    public static boolean isIDAvailable(ArrayList<ArticleBean> articleList, int id) {

        boolean isAvailable = false;
        for (ArticleBean article: articleList) {

            if (article.getID() == id) {
                isAvailable = true;
                break;
            }
        }

        return isAvailable;
    }

    public static ArticleBean getArticleByID(ArrayList<ArticleBean> articles, int id) {

        ArticleBean output = null;

        for (ArticleBean article : articles) {

            if (article.getID() == id) {
                output = article;
            }
        }

        return output;
    }
}
