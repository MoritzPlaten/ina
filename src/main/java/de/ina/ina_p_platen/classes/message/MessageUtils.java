package de.ina.ina_p_platen.classes.message;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Soll die Benachrichtigungen in volle Sätze umwandeln
 *
 * @author Moritz
 * */
public class MessageUtils {

    /**
     * @param message Die Message, die sie in vollen Sätzen ausgeben mächten
     * @return Die Nachricht mit einem boolean, ob es sich um ein Error oder um Kein-Error Nachricht handelt
     */
    public static Message translateMessage(String message) {

        Message messageBody = null;


        switch (message) {

            case "exists":
                messageBody = new Message("Benutzername existiert bereits!", true);
                break;

            case "pass":
                messageBody = new Message("Das Password muss min. 8 Zeichen, ein Großbuchstaben, ein Kleinbuchstaben, min. eine Ziffer, min. ein Sonderzeichen besitzen!", true);
                break;

            case "successRegister":
                messageBody = new Message("Sie haben sich erfolgreich Registriert!", false);
                break;

            case "notconfirm":
                messageBody = new Message("'Password' und 'Password bestaetigen' stimmen nicht ueber ein!", true);
                break;

            case "alreadylogin":
                messageBody = new Message("Sie sind bereits angemeldet!", true);
                break;

            case "notexists":
                messageBody = new Message("Diesen Username gibt es nicht!", true);
                break;

            case "passnotright":
                messageBody = new Message("Das Password ist falsch!", true);
                break;

            case "notavailable":
                messageBody = new Message("Die gewuenschte Anzahl ist nicht verfügbar!", true);
                break;

            case "buyed":
                messageBody = new Message("Sie haben erfolgreich eingekauft!", false);
                break;
        }

        return messageBody;
    }

    public static Message receiveMessage(HttpServletRequest request) {

        String message = request.getParameter("message");
        if (message != null) {
            return MessageUtils.translateMessage(message);
        } else {
            return null;
        }
    }
}
