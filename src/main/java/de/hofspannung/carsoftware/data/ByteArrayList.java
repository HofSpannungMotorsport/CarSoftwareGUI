package de.hofspannung.carsoftware.data;

import de.hofspannung.carsoftware.exception.IndexOutOfBoundsException;

import java.util.ArrayList;

/**
 * Modifiable array of bytes.
 * Contains
 */
public class ByteArrayList extends ArrayList<Byte> {

    public ByteArrayList() {
        super();
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     * @throws IllegalArgumentException if {@code initialCapacity} is negative
     */
    public ByteArrayList(int initialCapacity) throws IllegalArgumentException {
        super(initialCapacity);
    }

    /**
     * Constructs a list containing the data of the specified array.
     *
     * @param array byte data to fill this list with
     */
    public ByteArrayList(byte[] array) {
        super(array.length);
        addAll(array);
    }

    /**
     * Adds the specified integer in its binary form.
     * 4 bytes will be added in total.
     *
     * @param integer integer value to add
     */
    public void addInt(int integer) {
        addByte(integer >>> 24);
        addByte(integer >>> 16);
        addByte(integer >>> 8);
        addByte(integer);
    }

    /**
     * Gets an integer value from this list using the byte at {@code startIndex} and the 3 following bytes.
     *
     * @param startIndex first byte of the integer value
     * @return integer converted from bytes
     * @throws IndexOutOfBoundsException if {@code startIndex < 0} or  {@code startIndex > (size() - 4) }
     */
    public int getInt(int startIndex) throws IndexOutOfBoundsException {
        if (startIndex < 0 || startIndex > (size() - 4))
            throw new IndexOutOfBoundsException(startIndex, 0, size() - 4);
        int ret = get(startIndex) << 24;
        ret |= (get(++startIndex) << 16) & 0xFF0000;
        ret |= (get(++startIndex) << 8) & 0xFF00;
        ret |= get(++startIndex) & 0xFF;
        return ret;
    }

    /**
     * Adds the specified short in its binary form.
     * 2 bytes will be added in total.
     *
     * @param e short value to add
     */
    public void addShort(short e) {
        addByte(e >>> 8);
        addByte(e);
    }

    /**
     * Gets an short value from this list using the byte at {@code startIndex} and the following byte.
     *
     * @param startIndex first byte of the short value
     * @return short converted from bytes
     * @throws IndexOutOfBoundsException if {@code startIndex < 0} or  {@code startIndex > (size() - 2) }
     */
    public short getShort(int startIndex) throws IndexOutOfBoundsException {
        if (startIndex < 0 || startIndex > (size() - 2))
            throw new IndexOutOfBoundsException(startIndex, 0, size() - 2);
        int ret = get(startIndex) << 8;
        ret |= get(++startIndex) & 0xFF;
        return (short) ret;
    }

    /**
     * Adds the rightmost 8 bits of the specified integer to the list.
     *
     * @param number integer to add
     */
    public void addByte(int number) {
        add((byte) (number & 0xFF));
    }

    /**
     * Inserts the rightmost 8 bits of the specified integer at the specified index to the list.
     * See {@link ArrayList#add(int, Object)} for further information.
     *
     * @param index  position at which the value will be inserted
     * @param number value to insert
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index > size()}
     */
    public void addByte(int index, int number) throws IndexOutOfBoundsException {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(index, 0, size());
        add(index, (byte) (number & 0xFF));
    }

    /**
     * Binary representation of this list's content.
     *
     * @param withSpaces if a space should be added every 8 bits.
     * @return binary representation
     * @see ByteArrayList#toBinaryString()
     */
    public String toBinaryString(boolean withSpaces) {
        StringBuilder ret = new StringBuilder();
        for (byte b : this) {
            if (withSpaces)
                ret.append(" ");
            ret.append(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
        }
        return ret.toString();
    }

    /**
     * Binary representation of this list's content in groups of 8 bit (1 byte).
     *
     * @return binary representation
     * @see ByteArrayList#toBinaryString(boolean)
     */
    public String toBinaryString() {
        return toBinaryString(true);
    }

    /**
     * Hexadecimal representation of this list's content.
     *
     * @param withSpaces if a space should be added every byte.
     * @return hex representation
     * @see ByteArrayList#toHexString()
     */
    public String toHexString(boolean withSpaces) {
        StringBuilder ret = new StringBuilder();
        for (byte b : this) {
            if (withSpaces)
                ret.append(" ");
            ret.append(Integer.toHexString((b & 0xFF) + 0x100).substring(1));
        }
        return ret.toString().toUpperCase();
    }

    /**
     * Hexadecimal representation of this list's content in groups of 2 chars (1 byte).
     *
     * @return hex representation
     * @see ByteArrayList#toHexString(boolean)
     */
    public String toHexString() {
        return toHexString(true);
    }

    /**
     * This list's content as a byte array.
     *
     * @return byte array
     */
    public byte[] getArray() {
        byte[] arr = new byte[size()];
        int index = 0;
        for (byte b : this) {
            arr[index++] = b;
        }
        return arr;
    }

    /**
     * Sets the bit at the specified index (sets it to 1).
     *
     * @param index index of the bit on the whole list
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= (size() * 8}
     */
    public void setBit(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= (size() * 8))
            throw new IndexOutOfBoundsException(index, 0, size() * 8 - 1);

        int byt = index / 8;
        int bit = index % 8;
        setBit(byt, bit);
    }

    /**
     * Sets the bit at the specified index of the specified byte (sets it to 1).
     *
     * @param byteIndex index of the byte
     * @param bitIndex  index of the bit in the byte
     * @throws IndexOutOfBoundsException if {@code byteIndex < 0} or {@code byteIndex >= size()}
     *                                   or {@code bitIndex < 0 } or {@code bitIndex > 7}
     */
    public void setBit(int byteIndex, int bitIndex) throws IndexOutOfBoundsException {
        if (byteIndex < 0 || byteIndex >= size())
            throw new IndexOutOfBoundsException(byteIndex, 0, size() - 1);
        if (bitIndex < 0 || bitIndex > 7)
            throw new IndexOutOfBoundsException(bitIndex, 0, 7);

        set(byteIndex, (byte) (get(byteIndex) | (0x1 << bitIndex)));
    }

    /**
     * Clears the bit at the specified index (sets it to 0).
     *
     * @param index index of the bit on the whole list
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= (size() * 8}
     */
    public void clearBit(int index) {
        if (index < 0 || index >= (size() * 8))
            throw new IndexOutOfBoundsException(index, 0, size() * 8 - 1);

        int byt = index / 8;
        int bit = index % 8;
        clearBit(byt, bit);
    }

    /**
     * Sets the bit at the specified index of the specified byte (sets it to 0).
     *
     * @param byteIndex index of the byte
     * @param bitIndex  index of the bit in the byte
     * @throws IndexOutOfBoundsException if {@code byteIndex < 0} or {@code byteIndex >= size()}
     *                                   or {@code bitIndex < 0 } or {@code bitIndex > 7}
     */
    public void clearBit(int byteIndex, int bitIndex) throws IndexOutOfBoundsException {
        if (byteIndex < 0 || byteIndex >= size())
            throw new IndexOutOfBoundsException(byteIndex, 0, size() - 1);
        if (bitIndex < 0 || bitIndex > 7)
            throw new IndexOutOfBoundsException(bitIndex, 0, 7);

        set(byteIndex, (byte) (get(byteIndex) & ~(0x1 << bitIndex)));
    }

    /**
     * Flips the bit at the specified index (sets it to 0).
     *
     * @param index index of the bit on the whole list
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= (size() * 8}
     */
    public void flipBit(int index) {
        if (index < 0 || index >= (size() * 8))
            throw new IndexOutOfBoundsException(index, 0, size() * 8 - 1);

        int byt = index / 8;
        int bit = index % 8;
        flipBit(byt, bit);
    }

    /**
     * Flips the bit at the specified index of the specified byte (sets it to 0).
     *
     * @param byteIndex index of the byte
     * @param bitIndex  index of the bit in the byte
     * @throws IndexOutOfBoundsException if {@code byteIndex < 0} or {@code byteIndex >= size()}
     *                                   or {@code bitIndex < 0 } or {@code bitIndex > 7}
     */
    public void flipBit(int byteIndex, int bitIndex) throws IndexOutOfBoundsException {
        if (byteIndex < 0 || byteIndex >= size())
            throw new IndexOutOfBoundsException(byteIndex, 0, size() - 1);
        if (bitIndex < 0 || bitIndex > 7)
            throw new IndexOutOfBoundsException(bitIndex, 0, 7);

        set(byteIndex, (byte) (get(byteIndex) ^ (0x1 << bitIndex)));
    }

    /**
     * Gets the bit at the specified index.
     *
     * @param index index of the bit on the whole list
     * @return the specified bit
     * @throws IndexOutOfBoundsException if {@code index < 0} or {@code index >= (size() * 8}
     */
    public boolean getBit(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= (size() * 8))
            throw new IndexOutOfBoundsException(index, 0, size() * 8 - 1);

        int byt = index / 8;
        int bit = index % 8;
        return getBit(byt, bit);
    }

    /**
     * Gets the bit at the specified index of the specified byte.
     *
     * @param byteIndex index of the byte
     * @param bitIndex  index of the bit in the byte
     * @return the specified bit
     * @throws IndexOutOfBoundsException if {@code byteIndex < 0} or {@code byteIndex >= size()}
     *                                   or {@code bitIndex < 0 } or {@code bitIndex > 7}
     */
    public boolean getBit(int byteIndex, int bitIndex) throws IndexOutOfBoundsException {
        if (byteIndex < 0 || byteIndex >= size())
            throw new IndexOutOfBoundsException(byteIndex, 0, size() - 1);
        if (bitIndex < 0 || bitIndex > 7)
            throw new IndexOutOfBoundsException(bitIndex, 0, 7);

        return (get(byteIndex) & (0x1 << bitIndex)) > 0;
    }

    public void addAll(byte[] array) {
        for (byte b : array)
            add(b);
    }
}
