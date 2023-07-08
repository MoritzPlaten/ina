package de.ina.ina_p_platen.login;

import de.ina.ina_p_platen.classes.UserUtils;
import de.ina.ina_p_platen.interfaces.HelperBase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

public class LoginHelper extends HelperBase {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        if (session.getAttribute("login-helper") == null)
            session.setAttribute("login-helper", this);

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserBean user = new UserBean();
        user.setUsername(username);
        user.setPassword(password);



        ArrayList<UserBean> users = (ArrayList<UserBean>) session.getAttribute("userList");

        boolean error = false;
        if (session.getAttribute("user") != null) {

            error = true;
            response.sendRedirect(request.getContextPath() + "/register?message=alreadylogin");
        }

        if (!UserUtils.isUserExists(users, user.getUsername()) & !error) {
            error = true;
            response.sendRedirect(request.getContextPath() + "/register?message=notexists");
        }

        String getPasswordFromUser = UserUtils.getPasswordFromUserList(users, user.getUsername());

        if (!UserUtils.IsPasswordRight(user, getPasswordFromUser) && !error) {
            error = true;
            response.sendRedirect(request.getContextPath() + "/register?message=passnotright");
        }

        if (!error) {
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/articles");
        }
    }
}
