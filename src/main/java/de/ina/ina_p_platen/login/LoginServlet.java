package de.ina.ina_p_platen.login;

import de.ina.ina_p_platen.classes.UserUtils;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        LoginHelper loginHelper;
        if (session.getAttribute("login-helper") == null)
            loginHelper = new LoginHelper();
        else
            loginHelper = (LoginHelper)session.getAttribute("login-helper");

        loginHelper.doPost(request, response);
    }

}
