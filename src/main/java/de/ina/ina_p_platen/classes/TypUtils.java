package de.ina.ina_p_platen.classes;

public class TypUtils {

    public static int toInt(String s) {

        int output = 0; // Standardwert, falls der Parameter nicht vorhanden oder ungültig ist

        if (s != null && !s.isEmpty()) {
            try {
                output = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                // Fehlerbehandlung, wenn der String keine gültige Zahl ist
            }
        }

        return output;
    }
}
