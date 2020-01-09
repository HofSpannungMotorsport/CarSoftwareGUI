package de.hofspannung.carsoftware.connection;

import de.hofspannung.carsoftware.exception.TooMuchDataException;

public abstract class Connection implements AutoCloseable {

    public abstract void send(byte[] data) throws TooMuchDataException;
}
