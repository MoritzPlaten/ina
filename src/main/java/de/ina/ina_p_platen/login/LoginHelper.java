package de.ina.ina_p_platen.login;

import de.ina.ina_p_platen.classes.UserUtils;
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

            ArrayList<UserBean> users = (ArrayList<UserBean>) servletContext.getAttribute("userList");

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

    public void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("login-helper") == null)
            session.setAttribute("login-helper", this);

        if (session.getAttribute("user") != null) {

            session.removeAttribute("user");

            RequestDispatcher dispatcher = request.getRequestDispatcher("/articles");
            dispatcher.forward(request, response);
        } else {

            RequestDispatcher dispatcher = request.getRequestDispatcher("/articles?message=alreadylogin");
            dispatcher.forward(request, response);
        }
    }
}
