package de.hofspannung.carsoftware.message;

public abstract class Message implements Serializable {

    protected static MessageType type = MessageType.NONE;

    public static Message fromBytes(byte[] arr) throws ParseException {
        return null;
    }

    protected static boolean firstByteCorrect(byte first, MessageType type) {
        if ((first & 0xF0) != 0xA0)
            return false;
        return (first & 0xF) == type.ordinal();
    }

    public static MessageType getType() {
        return type;
    }

    protected byte firstByte() {
        return (byte) ((type.ordinal() & 0xF) | 0xA0);
    }

    public abstract byte[] toBytes();
}
