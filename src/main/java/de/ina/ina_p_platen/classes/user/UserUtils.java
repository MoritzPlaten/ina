package de.ina.ina_p_platen.classes.user;

import de.ina.ina_p_platen.login.UserBean;
import jakarta.servlet.ServletContext;

import java.util.Objects;

public class UserUtils {

    /**
     * @param userList Die Liste in der gesucht wird
     * @param username Der Username der überprüft werden soll
     * @return Ein boolean, der angibt, ob der User existiert oder nicht
     */
    public static boolean isUserExists(Users userList, String username) {
        for (UserBean user : userList) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param user Der Benutzer dessen Password überprüft werden soll
     * @param dbPassword Das Confirm-Password welches überprüft werden soll
     * @return Ein boolean, der angibt, ob Password und Confirm-Password übereinstimmen
     */
    public static boolean IsPasswordRight(UserBean user, String dbPassword) {

        return Objects.equals(user.getPassword(), dbPassword);
    }


    /**
     * @param userList Die Liste die benutzt werden soll
     * @param username Der Username vom Benutzer, wovon man das Password haben möchte
     * @return Das Password vom Nutzer mit dem Username
     */
    public static String getPasswordFromUserList(Users userList, String username) {
        if (userList != null) {
            for (UserBean user : userList) {
                if (user.getUsername().equals(username)) {
                    return user.getPassword();
                }
            }
        }
        return null;
    }

    /**
     * @param servletContext ServletContext
     */
    public static void addUserListAndStandardUsers(ServletContext servletContext) {

        synchronized (servletContext) {
            if (servletContext.getAttribute("userList") == null) {

                Users users = new Users();

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
