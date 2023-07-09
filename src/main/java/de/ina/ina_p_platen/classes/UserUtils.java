package de.ina.ina_p_platen.classes;

import de.ina.ina_p_platen.login.UserBean;
import jakarta.servlet.ServletContext;

import java.util.ArrayList;
import java.util.Objects;

public class UserUtils {

    public static boolean isUserExists(ArrayList<UserBean> userList, String username) {
        for (UserBean user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static boolean IsPasswordRight(UserBean user, String dbPassword) {

        return Objects.equals(user.getPassword(), dbPassword);
    }

    public static String getPasswordFromUserList(ArrayList<UserBean> userList, String username) {
        if (userList != null) {
            for (UserBean user : userList) {
                if (user.getUsername().equals(username)) {
                    return user.getPassword();
                }
            }
        }
        return null;
    }

    public static void addUserListAndStandardUsers(ServletContext servletContext) {

        synchronized (servletContext) {
            if (servletContext.getAttribute("userList") == null) {

                ArrayList<UserBean> users = new ArrayList<UserBean>();

                UserBean userBean1 = new UserBean();
                userBean1.setUsername("user1");
                userBean1.setPassword("pw1");
                users.add(userBean1);

                UserBean userBean2 = new UserBean();
                userBean2.setUsername("user2");
                userBean2.setPassword("pw2");
                users.add(userBean2);

                servletContext.setAttribute("userList", users);
            }
        }
    }
}
