package de.hofspannung.carsoftware.data.number;

import org.junit.Test;

public class TestNumbers {

  @Test
  public void testIntegersVerySimple() {
    var ui8 = new UInt8((byte) 12);
    var i8 = new Int8((byte) -12);
    var ui16 = new UInt16(1234);
    var i16 = new Int16((short) -1234);
    var ui32 = new UInt32(123456);
    var i32 = new Int32(-123456);
    var f = new Float((float) 12345678.9);

    i32.add(ui8);
    i32.add(i8);
    i32.add(ui16);
    i32.add(i16);
    i32.add(ui32);
    i32.add(f);
    i32.subtract(f);

    assert i32.byteValue() == 0;
    assert i32.shortValue() == 0;
    assert i32.intValue() == 0;
    assert i32.longValue() == 0;
    assert i32.floatValue() == 0;
    assert i32.doubleValue() == 0;
    assert i32.isZeroExact();
  }

}
