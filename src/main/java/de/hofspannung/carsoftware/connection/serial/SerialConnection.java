package de.hofspannung.carsoftware.connection.serial;

import com.fazecast.jSerialComm.SerialPort;
import de.hofspannung.carsoftware.connection.Connection;
import de.hofspannung.carsoftware.data.ByteArrayList;
import de.hofspannung.carsoftware.data.Checksum;
import de.hofspannung.carsoftware.exception.PortException;
import de.hofspannung.carsoftware.exception.TooMuchDataException;
import java.util.Timer;
import java.util.TimerTask;

public class SerialConnection extends Connection {

    /*
    Aubau Nachricht:
    1           1 - 128         2
    1 7         8 - 1024        16
    1 0000000   0000...0000     00000000 00000000
    1 Length    Data            Checksum
     */

  public static final int MAX_BYTES = 128; // max bytes to send at once
  public static final int MAX_BITS = MAX_BYTES * 8; // max bits to send at once
  private static final int timerPeriod = 100; // period the port is asked for new data
  private static final int emptyPeriodesBeforeBufferReset = 5; // periods with no new data, after which the buffer is reset
  protected ByteArrayList buffer = new ByteArrayList(); // internal buffer
  private int emptyPeriods; // periods since no new data

  private SerialPort port;

  private Timer timer;

  public SerialConnection(SerialPort port) throws PortException {
    super();
    this.port = port;

    if (!this.port.openPort()) {
      throw new PortException("Port could not be opened", this.port);
    }

    timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        work();
      }
    }, 0, timerPeriod);
  }

  private void work() {
    // read data from port
    if (!readPort()) {
      emptyPeriods++;
      // reset buffer, if necessary
      if (emptyPeriods >= emptyPeriodesBeforeBufferReset) {
        resetBuffer();
      }
    } else {
      emptyPeriods = 0;
    }

    readBuffer();
  }

  /**
   * Reads all new available bytes into the buffer.
   *
   * @return true if new bytes were added, false otherwise
   */
  private boolean readPort() {
    int available = port.bytesAvailable();

    if (available > 0) {
      byte[] data = new byte[available];
      port.readBytes(data, available);
      buffer.addAll(data);
      return true;
    }
    return false;
  }

  private void readBuffer() {
    while (buffer.size() >= 3) {

      // remove first byte from buffer, if not valid
      if (!buffer.getBit(7)) {
        buffer.remove(0);
        continue;
      }

      // decode first byte (length)
      int dataLength = (buffer.get(0) & 0x7F) + 1;
      int length = dataLength + 3;

      // enough data in buffer? otherwise wait
      if (buffer.size() < length) {
        return;
      }

      // get data from buffer
      var message = buffer.getRange(0, length);

      // if checksum not valid, remove first byte and try again
      if (!Checksum.checkFletcher(message)) {
        buffer.remove(0);
        continue;
      }

      // successfully retrieved data!
      var data = message.getRange(1, dataLength);

      // TODO: do something with data
    }
  }

  /**
   * Sends the specified data over the serial bus. Automatically applies a checksum.
   *
   * @param data Data to send
   * @throws TooMuchDataException if {@code data} is too long (more than {@value MAX_BYTES} bytes}
   */
  @Override
  public void send(byte[] data) throws TooMuchDataException {
    if (data.length == 0) {
      throw new IllegalArgumentException("Data must not be empty");
    }
    if (data.length > MAX_BYTES) {
      throw new TooMuchDataException(data.length, MAX_BYTES);
    }

    ByteArrayList dataAl = new ByteArrayList(data);

    dataAl.addByte(data.length - 1);
    dataAl.setBit(7);

    Checksum.addFletcher(dataAl);

    write(dataAl);
  }

  private void write(ByteArrayList data) {
    port.writeBytes(data.getArray(), data.size());
  }

  /**
   * Empties the buffer.
   */
  private void resetBuffer() {
    buffer.clear();
  }

  @Override
  public void close() throws Exception {
    timer.cancel();
    if (!port.closePort()) {
      throw new PortException("Port could not be closed", port);
    }
  }

}
