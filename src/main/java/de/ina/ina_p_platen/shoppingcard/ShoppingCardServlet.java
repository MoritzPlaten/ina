package de.ina.ina_p_platen.shoppingcard;

import de.ina.ina_p_platen.classes.shoppingCard.ShoppingCardUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/shopping-card-servlet")
public class ShoppingCardServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();

        //initialize the shopping card
        ShoppingCardUtils.initializeShoppingCard(session);

        ShoppingCardHelper articlesHelper;
        if (session.getAttribute("shopping-card-helper") == null)
            articlesHelper = new ShoppingCardHelper();
        else
            articlesHelper = (ShoppingCardHelper)session.getAttribute("shopping-card-helper");

        String method = request.getParameter("_method");

        switch (method) {

            case "ADD":
                articlesHelper.doPost(request, response);
                break;

            case "DELETE":
                articlesHelper.doDelete(request, response);
                break;

            case "BUY":
                articlesHelper.doBuy(request, response);
                break;

        }
    }
}
