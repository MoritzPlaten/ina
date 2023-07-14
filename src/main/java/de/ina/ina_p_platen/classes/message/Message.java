package de.ina.ina_p_platen.classes.message;

public class Message {

    private String message;
    private boolean error;

    public Message(String message, boolean error) {
        this.error = error;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
