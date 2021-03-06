package de.hofspannung.carsoftware.message;

import de.hofspannung.carsoftware.data.ByteArrayList;
import de.hofspannung.carsoftware.exception.ParseException;

public abstract class Message implements ByteSerializable {

  public static MessageType type = MessageType.NONE;

  protected MessageType instanceType = type;

  public Message() {
  }

  public Message(byte[] bytes) throws ParseException {
    fromBytes(new ByteArrayList(bytes));
  }

  protected static boolean firstByteCorrect(byte first, MessageType type) {
    if ((first & 0xF0) != 0xA0) {
      return false;
    }
    return (first & 0xF) == type.ordinal();
  }

  public MessageType getInstanceType() {
    return instanceType;
  }

  protected byte firstByte() {
    return (byte) ((instanceType.ordinal() & 0xF) | 0xA0);
  }

  protected MessageType firstByte(byte first) throws ParseException {
    if ((first & 0xF0) != 0xA0) {
      throw new ParseException("Not a Message");
    }

    return MessageType.NONE;
  }

  @Override
  public void fromBytes(ByteArrayList bytes) throws ParseException {
    byte b = 0;
    try {
      b = bytes.get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new ParseException("Not a valid message!");
    }

    if ((b & 0xF0) != 0xA0) {
      throw new ParseException("Not a message");
    }

    int type = b & 0xF;
    MessageType[] values = MessageType.values();
    if (type >= values.length) {
      throw new ParseException("Invalid message type");
    }

    MessageType callerType = null;
    try {
      Class<?> callerClass = Class
          .forName(Thread.currentThread().getStackTrace()[2].getClassName());
      callerType = (MessageType) callerClass.getDeclaredField("type").get(callerClass);
    } catch (Exception e) {
      throw new ParseException("Invalid caller");
    }

    if (callerType.ordinal() != values[type].ordinal()) {
      throw new ParseException("Wrong message type!");
    }
  }

  public ByteArrayList toBytes() {
    ByteArrayList array = new ByteArrayList();
    array.add(firstByte());
    return array;
  }
}
