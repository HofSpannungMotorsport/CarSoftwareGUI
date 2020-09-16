package de.hofspannung.carsoftware.connection;

import de.hofspannung.carsoftware.data.ByteArrayList;
import java.util.LinkedList;
import java.util.List;

public class DataBuffer {

  private final List<ByteArrayList> data = new LinkedList<>();
  private final List<DataReceiveListener> listeners = new LinkedList<>();


  public DataReceiver getReciever() {
    return new DataReceiver();
  }

  public DataAdder getAdder() {
    return new DataAdder();
  }

  /**
   * Check for data.
   *
   * @return true, if data is available.
   */
  public boolean hasData() {
    return !data.isEmpty();
  }

  /**
   * Gets the oldest data.
   *
   * @return data.
   */
  public ByteArrayList getData() {
    if (data.isEmpty()) {
      throw new IndexOutOfBoundsException("No data available!");
    }
    return data.remove(0);
  }

  /**
   * Adds data to this receiver.
   *
   * @param bytes data to add.
   */
  public void addData(ByteArrayList bytes) {
    data.add(bytes);
    if (data.size() == 1) {
      listeners.forEach(DataReceiveListener::hasData);
    }
    listeners.forEach(DataReceiveListener::received);
  }

  public void addListener(DataReceiveListener listener) {
    listeners.add(listener);
  }

  public void removeListener(DataReceiveListener listener) {
    listeners.remove(listener);
  }

  public class DataReceiver {

    /**
     * Gets the oldest data.
     *
     * @return data.
     */
    public ByteArrayList getData() {
      return DataBuffer.this.getData();
    }

    /**
     * Check for data.
     *
     * @return true, if data is available.
     */
    public boolean hasData() {
      return DataBuffer.this.hasData();
    }

    public void addListener(DataReceiveListener listener) {
      DataBuffer.this.addListener(listener);
    }

    public void removeListener(DataReceiveListener listener) {
      DataBuffer.this.removeListener(listener);
    }
  }

  public class DataAdder {

    /**
     * Adds data.
     *
     * @param bytes data to add.
     */
    public void addData(ByteArrayList bytes) {
      DataBuffer.this.addData(bytes);
    }
  }
}
