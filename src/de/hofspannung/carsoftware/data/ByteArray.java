package de.hofspannung.carsoftware.data;

import java.util.ArrayList;

public class ByteArray extends ArrayList<Byte> {

    public ByteArray() {
        super();
    }

    public ByteArray(int initialSize) {
        super(initialSize);
    }

    public ByteArray(byte[] array) {
        super(array.length);
        for (int i = 0; i < array.length; i++)
            set(i, array[i]);
    }

    /**
     * Adds the int in its binary form.
     * 4 bytes will be added in total.
     *
     * @param e
     */
    public void addInt(int e) {
        addByte(e >>> 24);
        addByte(e >>> 16);
        addByte(e >>> 8);
        addByte(e);
    }

    public int getInt(int startIndex) throws IndexOutOfBoundsException {
        int ret = get(startIndex++) << 24;
        ret |= get(startIndex++) << 16;
        ret |= get(startIndex++) << 8;
        ret |= get(startIndex);
        return ret;
    }

    /**
     * Adds the short in its binary form.
     * 2 bytes will be added in total.
     *
     * @param e
     */
    public void addShort(short e) {
        addByte(e >>> 8);
        addByte(e);
    }

    public short getShort(int startIndex) throws IndexOutOfBoundsException {
        int ret = get(startIndex++) << 8;
        ret |= get(startIndex);
        return (short) ret;
    }


    /**
     * Adds the rightmost 8 bits to the array.
     *
     * @param number number
     */
    public void addByte(int number) {
        add((byte) (number & 0xFF));
    }

    public void addByte(int index, int number) {
        add(index, (byte) (number & 0xFF));
    }

    public String toBinaryString() {
        String ret = "";
        for (byte b : this) {
            ret += " " + Integer.toBinaryString((b & 0xFF) + 0x100).substring(1);
        }
        return ret.substring(1);
    }

    public String toHexString() {
        String ret = "";
        for (byte b : this) {
            ret += " " + Integer.toHexString((b & 0xFF) + 0x100).substring(1);
        }
        return ret.substring(1).toUpperCase();
    }

    public byte[] getArray() {
        byte[] arr = new byte[size()];
        int index = 0;
        for (byte b : this) {
            arr[index++] = b;
        }
        return arr;
    }

    public void setBit(int index) throws IndexOutOfBoundsException {
        int byt = index / 8;
        int bit = index % 8;
        setBit(byt, bit);
    }

    public void setBit(int byteIndex, int bitIndex) throws IndexOutOfBoundsException {
        byteIndex += bitIndex / 8;
        bitIndex %= 8;
        byte b = get(byteIndex);
        set(byteIndex, (byte) (b | 2 ^ bitIndex));
    }

    public void clearBit(int byteIndex, int bitIndex) throws IndexOutOfBoundsException {
        byteIndex += bitIndex / 8;
        bitIndex %= 8;
        byte b = get(byteIndex);
        set(byteIndex, (byte) (b & ~(2 ^ bitIndex)));
    }

}
