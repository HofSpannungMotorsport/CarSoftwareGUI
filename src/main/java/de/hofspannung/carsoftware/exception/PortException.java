package de.hofspannung.carsoftware.exception;

import com.fazecast.jSerialComm.SerialPort;

public class PortException extends Exception {

    private SerialPort port = null;

    public PortException() {
    }

    public PortException(String message) {
        super(message);
    }

    public PortException(String message, SerialPort port) {
        this(message);
        this.port = port;
    }

    public SerialPort getPort() {
        return port;
    }

    @Override
    public String toString() {
        String s = super.toString();
        if (port != null)
            s += "\nAdditional INFO:   Port: " + port.toString() + "   Open: " + port.isOpen();

        return s;
    }
}
