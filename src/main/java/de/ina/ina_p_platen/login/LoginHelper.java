package de.ina.ina_p_platen.login;

import de.ina.ina_p_platen.articles.ArticleBean;
import de.ina.ina_p_platen.classes.article.Articles;
import de.ina.ina_p_platen.classes.article.ArticlesUtils;
import de.ina.ina_p_platen.classes.user.UserUtils;
import de.ina.ina_p_platen.classes.user.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

public class LoginHelper {

    public void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("login-helper") == null)
            session.setAttribute("login-helper", this);

        Enumeration<String> parameterNames = request.getParameterNames();
        parameterNames.nextElement();

        String username = request.getParameter(parameterNames.nextElement());
        String password = request.getParameter(parameterNames.nextElement());

        UserBean user = new UserBean();
        user.setUsername(username);
        user.setPassword(password);

        synchronized (servletContext) {

            Users users = (Users) servletContext.getAttribute("userList");

            boolean error = false;
            if (session.getAttribute("user") != null) {

                error = true;
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login?message=alreadylogin");
                dispatcher.forward(request, response);
            }

            if (!UserUtils.isUserExists(users, user.getUsername()) & !error) {
                error = true;
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login?message=notexists");
                dispatcher.forward(request, response);
            }

            String getPasswordFromUser = UserUtils.getPasswordFromUserList(users, user.getUsername());

            if (!UserUtils.IsPasswordRight(user, getPasswordFromUser) && !error) {
                error = true;
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login?message=passnotright");
                dispatcher.forward(request, response);
            }

            if (!error) {
                session.setAttribute("user", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
                dispatcher.forward(request, response);
            }
        }
    }

    //TODO: Article ist auf 0 und nicht gelöscht
    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("login-helper") == null)
            session.setAttribute("login-helper", this);

        UserBean user = (UserBean)session.getAttribute("user");

        if (user != null) {

            Articles shoppingCard = (Articles) session.getAttribute("shopping-card");
            Articles articles = (Articles) servletContext.getAttribute("articles");

            Articles removeArticles = new Articles();

            for (ArticleBean articleInShoppingCard : shoppingCard) {

                ArticleBean articleInArticles = ArticlesUtils.getArticleByID(articles, articleInShoppingCard.getID());
                if (articleInArticles != null) {

                    int newAmountInArticleList = articleInShoppingCard.getAmount() + articleInArticles.getAmount();

                    //change the amount of the shopping card
                    removeArticles.add(articleInShoppingCard);

                    //change the amount of the articles
                    ArticlesUtils.updateArticleAmount(articles, articleInShoppingCard.getID(), newAmountInArticleList);
                }
            }

            //Remove the articles from the shopping card
            shoppingCard.removeAll(removeArticles);

            //save lists
            session.setAttribute("shopping-card", shoppingCard);
            servletContext.setAttribute("articles", articles);

            session.removeAttribute("user");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
            dispatcher.forward(request, response);
        } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/articles?message=alreadylogin");
            dispatcher.forward(request, response);
        }
    }
}
