package de.hofspannung.carsoftware.message;

import de.hofspannung.carsoftware.data.ByteArrayList;

/**
 * Makes an object serializable into raw bytes.
 */
public interface ByteSerializable {

    /**
     * Loads this object from a binary representation.
     *
     * @param bytes raw bytes.
     * @throws ParseException If this object cannot be loaded from the given binary representation.
     *                        Reasons for that may be corrupted data, missing data or invalid/outdated data.
     * @see ByteSerializable#toBytes()
     */
    void fromBytes(ByteArrayList bytes) throws ParseException;

    /**
     * Converts this object into a binary representation.
     *
     * @return raw bytes.
     * @see ByteSerializable#fromBytes(ByteArrayList)
     */
    ByteArrayList toBytes();

}
