package de.ina.ina_p_platen.register;

import de.ina.ina_p_platen.classes.UserUtils;
import de.ina.ina_p_platen.login.LoginHelper;
import de.ina.ina_p_platen.login.UserBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/register-servlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        UserUtils.addUserListAndStandardUsers(session);

        RegisterHelper registerHelper;
        if (session.getAttribute("register-helper") == null)
            registerHelper = new RegisterHelper();
        else
            registerHelper = (RegisterHelper)session.getAttribute("register-helper");

        registerHelper.doPost(request, response);
    }

}
