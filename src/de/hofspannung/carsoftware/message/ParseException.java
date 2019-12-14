package de.hofspannung.carsoftware.message;

public class ParseException extends Exception {

    private byte[] data;

    public ParseException(String text) {
        super(text);
    }

    public ParseException(String text, byte[] data) {
        super(text);
        this.data = data;
    }

    @Override
    public String toString() {

        return this.getClass().getName() + ": " + getLocalizedMessage() + "\nRaw data: ";
    }
}
