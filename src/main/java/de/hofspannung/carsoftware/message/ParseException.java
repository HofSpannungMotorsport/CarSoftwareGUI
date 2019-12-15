package de.hofspannung.carsoftware.message;

import de.hofspannung.carsoftware.data.ByteArrayList;

public class ParseException extends Exception {

    private byte[] data;

    public ParseException(String text) {
        super(text);
    }

    public ParseException(String text, byte[] data) {
        super(text);
        this.data = data;
    }

    public ParseException(String text, ByteArrayList data) {
        this(text, data.getArray());
    }

    @Override
    public String toString() {

        return this.getClass().getName() + ": " + getLocalizedMessage() + "\nRaw data: ";
    }

    public byte[] getData() {
        return data;
    }
}
