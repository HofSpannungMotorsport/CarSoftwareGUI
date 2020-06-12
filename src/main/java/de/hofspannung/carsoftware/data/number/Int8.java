package de.hofspannung.carsoftware.data.number;

import org.jetbrains.annotations.NotNull;

public class Int8 extends Number {

  public static final byte MIN = Byte.MIN_VALUE;
  public static final byte MAX = Byte.MAX_VALUE;

  private byte value;

  public Int8(byte value) {
    this.value = value;
  }

  @Override
  public int serializedAsInt() {
    return Byte.toUnsignedInt(value);
  }

  @Override
  public byte byteValue() {
    return value;
  }

  @Override
  public short shortValue() {
    return value;
  }

  @Override
  public int intValue() {
    return value;
  }

  @Override
  public long longValue() {
    return value;
  }

  @Override
  public float floatValue() {
    return value;
  }

  @Override
  public double doubleValue() {
    return value;
  }

  @Override
  public @NotNull Number minValue() {
    return new Int8(MIN);
  }

  @Override
  public @NotNull Number maxValue() {
    return new Int8(MAX);
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
    var val = Math.addExact(value, number.longValue());
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
    var val = Math.subtractExact(value, number.longValue());
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
    if (number.isFloatingPoint()) {
      var val = value * number.doubleValue();
      testRange(val);
      value = (byte) val;
    } else {
      var val = Math.multiplyExact(value, number.longValue());
      testRange(val);
      value = (byte) val;
    }
    changed();
  }

  @Override
  public void divide(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      value /= number.doubleValue();
    } else {
      value /= number.longValue();
    }
    changed();
  }

  @Override
  public void divideExact(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      var val = value / number.doubleValue();
      testRange(val);
      value = (byte) val;
    } else {
      var val = value / number.longValue();
      testRange(val);
      value = (byte) val;
    }
    changed();
  }

  @Override
  public Int8 clone() {
    return new Int8(value);
  }

  @Override
  public String toString() {
    return Byte.toString(value);
  }
}
