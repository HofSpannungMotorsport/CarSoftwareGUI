package de.hofspannung.carsoftware.data.number;

import jdk.jfr.Unsigned;
import org.jetbrains.annotations.NotNull;

public class UInt8 extends Number {

  public static final short MIN = 0;
  public static final short MAX = 0xff;
  @Unsigned
  byte value;

  public UInt8(short value) {
    this((byte) value);
  }

  protected UInt8(byte rawValue) {
    this.value = rawValue;
  }

  @Override
  public int serializedAsInt() {
    return Byte.toUnsignedInt(value);
  }

  @Override
  public int intValue() {
    return Byte.toUnsignedInt(value);
  }

  @Override
  public long longValue() {
    return intValue();
  }

  @Override
  public float floatValue() {
    return intValue();
  }

  @Override
  public double doubleValue() {
    return intValue();
  }

  @Override
  public @NotNull Number minValue() {
    return new UInt8(MIN);
  }

  @Override
  public @NotNull Number maxValue() {
    return new UInt8(MAX);
  }

  @Override
  public boolean isFloatingPoint() {
    return false;
  }

  @Override
  public void add(@NotNull Number number) throws ArithmeticException {
    value += number.longValue();
    changed();
  }

  @Override
  public void addExact(@NotNull Number number) throws ArithmeticException {
    var val = Math.addExact(longValue(), number.longValue());
    testRange(val);
    value = (byte) val;
    changed();
  }

  @Override
  public void subtract(@NotNull Number number) throws ArithmeticException {
    value -= number.longValue();
    changed();
  }

  @Override
  public void subtractExact(@NotNull Number number) throws ArithmeticException {
    var val = Math.subtractExact(longValue(), number.longValue());
    testRange(val);
    value = (byte) val;
    changed();
  }

  @Override
  public void multiply(@NotNull Number number) throws ArithmeticException {
    if (number.isFloatingPoint()) {
      value *= number.doubleValue();
    } else {
      value *= number.longValue();
    }
    changed();
  }

  @Override
  public void multiplyExact(@NotNull Number number) throws ArithmeticException {
    var val = Math.multiplyExact(longValue(), number.longValue());
    testRange(val);
    value = (byte) val;
    changed();
  }

  @Override
  public void divide(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      value = (byte) (intValue() / number.doubleValue());
    } else {
      value = (byte) (longValue() / number.longValue());
    }
    changed();
  }

  @Override
  public void divideExact(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      var val = intValue() / number.doubleValue();
      testRange(val);
      value = (byte) val;
    } else {
      var val = longValue() / number.longValue();
      testRange(val);
      value = (byte) val;
    }
    changed();
  }

  @Override
  public UInt8 clone() {
    return new UInt8(value);
  }

  @Override
  public String toString() {
    return Integer.toString(Byte.toUnsignedInt(value));
  }
}
