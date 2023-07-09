package de.ina.ina_p_platen.login;

import de.ina.ina_p_platen.classes.UserUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

public class LoginHelper {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("login-helper") == null)
            session.setAttribute("login-helper", this);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserBean user = new UserBean();
        user.setUsername(username);
        user.setPassword(password);

        synchronized (servletContext) {

            ArrayList<UserBean> users = (ArrayList<UserBean>) servletContext.getAttribute("userList");

            boolean error = false;
            if (session.getAttribute("user") != null) {

                error = true;
                response.sendRedirect(request.getContextPath() + "/login?message=alreadylogin");
            }

            if (!UserUtils.isUserExists(users, user.getUsername()) & !error) {
                error = true;
                response.sendRedirect(request.getContextPath() + "/login?message=notexists");
            }

            String getPasswordFromUser = UserUtils.getPasswordFromUserList(users, user.getUsername());

            if (!UserUtils.IsPasswordRight(user, getPasswordFromUser) && !error) {
                error = true;
                response.sendRedirect(request.getContextPath() + "/login?message=passnotright");
            }

            if (!error) {
                session.setAttribute("user", user);
                response.sendRedirect(request.getContextPath() + "/articles");
            }
        }
    }
}
