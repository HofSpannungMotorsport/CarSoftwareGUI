package de.hofspannung.carsoftware.message;

public class StatusMessage extends Message {

    public StatusMessage() {
        super();
        type = MessageType.STATUS;
    }


    @Override
    public byte[] toBytes() {
        return new byte[0];
    }
}
