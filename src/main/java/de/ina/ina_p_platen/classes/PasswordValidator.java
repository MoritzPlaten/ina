package de.ina.ina_p_platen.classes;

public class PasswordValidator {

    /**
     * @param password Das Password welches validiert werden soll
     * @return Ein boolean, der angibt ob das Password sicher genug ist
     */
    public static boolean validate(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        // Mindestlänge des Passworts prüfen
        if (password.length() < 8) {
            return false;
        }

        // Mindestens einen Großbuchstaben prüfen
        if (!containsUppercase(password)) {
            return false;
        }

        // Mindestens einen Kleinbuchstaben prüfen
        if (!containsLowercase(password)) {
            return false;
        }

        // Mindestens eine Ziffer prüfen
        if (!containsDigit(password)) {
            return false;
        }

        // Mindestens ein Sonderzeichen prüfen
        if (!containsSpecialCharacter(password)) {
            return false;
        }

        return true;
    }

    /**
     * @param password Das Password welches überprüft werden soll
     * @return Ein boolean, der angibt ob in dem Password ein Großbuchstabe besitzt
     */
    private static boolean containsUppercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param password Das Password welches überprüft werden soll
     * @return Ein boolean, der angibt ob in dem Password ein Kleinbuchstaben besitzt
     */
    private static boolean containsLowercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param password Das Password welches überprüft werden soll
     * @return Ein boolean, der angibt ob in dem Password eine Zahl besitzt
     */
    private static boolean containsDigit(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param password Das Password welches überprüft werden soll
     * @return Ein boolean, der angibt ob in dem Password eine Sonderzeichen besitzt
     */
    private static boolean containsSpecialCharacter(String password) {
        String specialCharacters = "!@#$%^&*()-_=+[{]}\\|;:'\",<.>/?";
        for (char c : password.toCharArray()) {
            if (specialCharacters.contains(String.valueOf(c))) {
                return true;
            }
        }
        return false;
    }
}

