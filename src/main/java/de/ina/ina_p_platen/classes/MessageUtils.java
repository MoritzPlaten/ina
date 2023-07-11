package de.ina.ina_p_platen.classes;

public class MessageUtils {

    public static String translateMessage(String message) {

        String messageBody = null;

        switch (message) {

            case "exists":
                messageBody = "Benutzername existiert bereits!";
                break;

            case "pass":
                messageBody = "Das Password muss min. 8 Zeichen, ein Großbuchstaben, ein Kleinbuchstaben, min. eine Ziffer, min. ein Sonderzeichen besitzen!";
                break;

            case "successRegister":
                messageBody = "Sie haben sich erfolgreich Registriert!";
                break;

            case "notconfirm":
                messageBody = "'Password' und 'Password bestaetigen' stimmen nicht ueber ein!";
                break;

            case "alreadylogin":
                messageBody = "Sie sind bereits angemeldet!";
                break;

            case "notexists":
                messageBody = "Diesen Username gibt es nicht!";
                break;

            case "passnotright":
                messageBody = "Das Password ist falsch!";
                break;

            case "notavailable":
                messageBody = "Die gewuenschte Anzahl ist nicht verfügbar";
                break;
        }

        return messageBody;
    }
}
