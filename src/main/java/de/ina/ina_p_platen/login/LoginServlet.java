package de.ina.ina_p_platen.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login-servlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        LoginHelper loginHelper;
        if (session.getAttribute("login-helper") == null)
            loginHelper = new LoginHelper();
        else
            loginHelper = (LoginHelper)session.getAttribute("login-helper");

        String method = request.getParameter("_method");

        switch (method) {

            case "LOGIN":
                loginHelper.doLogin(request, response);
                break;

            case "LOGOUT":
                loginHelper.doLogout(request, response);
                break;

        }
    }

}
