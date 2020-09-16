package de.hofspannung.carsoftware.connection;

public interface DataReceiveListener {

  /**
   * Fires on every single received data packet.
   */
  void received();

  /**
   * Fires when {@link DataBuffer#hasData()} changes from false to true.
   */
  void hasData();
}
