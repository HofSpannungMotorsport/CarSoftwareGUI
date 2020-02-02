package de.hofspannung.carsoftware.data;

import org.junit.Test;

public class TestByteArrayList {

  @Test
  public void testByteArrayListInt() {
    int values = 2 << 20;
    int value = Integer.MIN_VALUE;

    do {
      ByteArrayList bal = new ByteArrayList();
      bal.addInt(value);

      assert padZeros(Integer.toBinaryString(value), 32).equals(bal.toBinaryString(false));

      value += ((long) Integer.MAX_VALUE + 1) / values;
    } while (value != Integer.MIN_VALUE);
  }

  @Test
  public void testByteArrayListShort() {
    short value = Short.MIN_VALUE;

    do {
      ByteArrayList bal = new ByteArrayList();
      bal.addShort(value);

      assert Integer.toBinaryString((value & 0xFFFF) + 0x10000).substring(1)
          .equals(bal.toBinaryString(false));

      value++;
    } while (value != Short.MIN_VALUE);
  }

  public String padZeros(String string, int length) {
    StringBuilder sb = new StringBuilder(string);
    while (sb.length() < length) {
      sb.insert(0, '0');
    }
    return sb.toString();
  }
}
