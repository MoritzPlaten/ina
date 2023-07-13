package de.ina.ina_p_platen.register;

import de.ina.ina_p_platen.classes.PasswordValidator;
import de.ina.ina_p_platen.classes.UserUtils;
import de.ina.ina_p_platen.login.UserBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RegisterHelper {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();

        if (session.getAttribute("register-helper") == null)
            session.setAttribute("register-helper", this);

        synchronized (servletContext) {

            ArrayList<UserBean> users = (ArrayList<UserBean>) servletContext.getAttribute("userList");

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String passwordConfirm = request.getParameter("confirm-password");

            boolean error = false;
            if (!Objects.equals(password, passwordConfirm)) {
                error = true;
                RequestDispatcher dispatcher = request.getRequestDispatcher("/register?message=notconfirm");
                dispatcher.forward(request, response);
            }

            UserBean user = new UserBean();
            user.setUsername(username);
            user.setPassword(password);

            if (UserUtils.isUserExists(users, user.getUsername()) && !error) {

                //Wenn User existiert, dann sag dem User bescheid
                error = true;
                RequestDispatcher dispatcher = request.getRequestDispatcher("/register?message=exists");
                dispatcher.forward(request, response);
            }

            //Password validation
            if (!PasswordValidator.validate(user.getPassword()) && !error) {

                //Wenn Password schwach ist, dann sag dem User bescheid
                //Wurde implementiert für ein weiteres Feature
                error = true;
                RequestDispatcher dispatcher = request.getRequestDispatcher("/register?message=pass");
                dispatcher.forward(request, response);
            }

            //Wenn kein Fehler aufgetreten ist => User adden + User zum Login schicken
            if (!error) {
                users.add(user);
                session.setAttribute("userList", users);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/login?message=successRegister");
                dispatcher.forward(request, response);
            }
        }
    }
}