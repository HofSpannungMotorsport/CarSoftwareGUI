package de.hofspannung.carsoftware.message;

public abstract class Message {

    protected MessageType type = MessageType.NONE;

    public abstract byte[] toByteArray();
}
