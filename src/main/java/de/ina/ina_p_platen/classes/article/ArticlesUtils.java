package de.ina.ina_p_platen.classes.article;

import de.ina.ina_p_platen.articles.ArticleBean;
import jakarta.servlet.ServletContext;

import java.util.ArrayList;
import java.util.Objects;

public class ArticlesUtils {

    /**
     * @param servletContext Den ServletContext
     */
    public static void addStandardArticles(ServletContext servletContext) {

        synchronized (servletContext) {

            if (servletContext.getAttribute("articles") == null) {

                Articles articles = new Articles();

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

    /**
     * @param inputList Die Liste in welcher gesucht wird
     * @param articleName Der Artikel der geprüft werden soll
     * @return
     */
    public static boolean isArticleNameExists(ArrayList<ArticleBean> inputList, String articleName) {
        for (ArticleBean article : inputList) {
            if (article.getName().equals(articleName)) {
                return true; // Artikel bereits vorhanden
            }
        }
        return false; // Artikel nicht gefunden
    }

    /**
     * @param inputList Die Liste in der gesucht werden soll
     * @param articleID Die Artikel ID des Artikel, wo die Anzahl geändert werden soll
     * @param newAmount Die neue Anzahl des Artikels setzen
     */
    public static ArrayList<ArticleBean> updateArticleAmount(ArrayList<ArticleBean> inputList, int articleID, int newAmount) {
        for (ArticleBean article : inputList) {
            if (article.getID() == articleID) {
                article.setAmount(newAmount); // Neue Anzahl setzen
                break;
            }
        }
        return inputList;
    }

    /**
     * @param articleList Die Liste in welcher gesucht werden soll
     * @param articleName Der Artikel Name, von der die ID herausgesucht werden soll
     * @return Die ID des Artikel
     */
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

    /**
     * @param articleList Die Liste die benutzt werden soll
     * @return Eine Artikel ID, welche in dieser Liste noch nicht existiert.
     */
    public static int getNewArticleID(ArrayList<ArticleBean> articleList) {

        int generatedID = 0;

        do {
            generatedID += 1;
        } while(!isIDAvailable(articleList, generatedID));

        return generatedID;
    }

    /**
     * @param articleList Die Liste in der gesucht werden soll
     * @param id Die Artikel ID die gesucht werden soll
     * @return Ein boolean, der zurück gibt, ob die ID vorhanden ist
     */
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

    /**
     * @param articles Die Liste die benutzt werden soll
     * @param id Die Artikel ID, von dem Artikel, welcher sich geholt werden soll.
     * @return Der Artikel, der zur Artikel ID passt oder null;
     */
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
