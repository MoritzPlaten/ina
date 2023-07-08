package de.ina.ina_p_platen.login;

public class UserBean {

    private String username;
    private String password;

    public UserBean() {
        // Standardkonstruktor
    }

    // Getter und Setter für den Benutzernamen
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter und Setter für das Passwort
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
