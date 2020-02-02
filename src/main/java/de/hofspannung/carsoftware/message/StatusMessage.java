package de.hofspannung.carsoftware.message;

import de.hofspannung.carsoftware.data.ByteArrayList;

public class StatusMessage extends Message {

  public StatusMessage() {
    super();
    instanceType = MessageType.STATUS;
  }


  @Override
  public ByteArrayList toBytes() {
    return null;
  }
}
