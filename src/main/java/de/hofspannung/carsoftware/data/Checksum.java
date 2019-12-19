package de.hofspannung.carsoftware.data;

/**
 * Various checksums.
 */
public class Checksum {

    /**
     * Calculates the fletcher's checksum.
     *
     * @param data Data to iterate over
     * @return checksum (2 bytes)
     */
    public static short fletcher(ByteArrayList data) {
        int sum1 = 0;
        int sum2 = 0;

        for (byte b : data) {
            sum1 = (sum1 + (b & 0xFF)) % 0xFF;
            sum2 = (sum2 + sum1) % 0xFF;
        }

        return (short) ((sum1 & 0xFF) << 8 | (sum2 & 0xFF));
    }

    /**
     * Calculates the fletcher's checksum and appends it to the specified data.
     * The checksum is added in a way, that wen calculating the checksum over the result, it returns 0 if the data is correct.
     * Use {@link Checksum#checkFletcher(ByteArrayList)} to check.
     *
     * @param data Data to append its own checksum
     * @return {@code data} with appended checksum
     */
    public static ByteArrayList addFletcher(ByteArrayList data) {
        short f = fletcher(data);

        int f0 = (f & 0xFF00) >> 8;
        int f1 = (f & 0xFF);
        int c0 = 0xFF - ((f0 + f1) % 0xFF);
        int c1 = 0xFF - ((f0 + c0) % 0xFF);

        data.addByte(c0);
        data.addByte(c1);

        return data;
    }

    /**
     * Checks data with fletchers's checksum generated with {@link Checksum#addFletcher(ByteArrayList)}.
     *
     * @param data Data to verify
     * @return true if {@code data} has no error, false otherwise
     */
    public static boolean checkFletcher(ByteArrayList data) {
        return fletcher(data) == 0x0;
    }

}
