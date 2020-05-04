package de.hofspannung.carsoftware.data.number;

import jdk.jfr.Unsigned;
import org.jetbrains.annotations.NotNull;

public class UInt16 extends Number {

  public static final int MIN = 0;
  public static final int MAX = 0xffff;
  @Unsigned
  private short value;

  public UInt16(int value) {
    this((short) value);
  }

  protected UInt16(short rawValue) {
    this.value = rawValue;
  }

  @Override
  public int serializedAsInt() {
    return Short.toUnsignedInt(value);
  }

  @Override
  public int intValue() {
    return Short.toUnsignedInt(value);
  }

  @Override
  public long longValue() {
    return Short.toUnsignedLong(value);
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
    return new UInt16(MIN);
  }

  @Override
  public @NotNull Number maxValue() {
    return new UInt16(MAX);
  }

  @Override
  public boolean isFloatingPoint() {
    return false;
  }

  @Override
  public void add(@NotNull Number number) throws ArithmeticException {
    value += number.longValue();
  }

  @Override
  public void addExact(@NotNull Number number) throws ArithmeticException {
    var val = Math.addExact(longValue(), number.longValue());
    testRange(val);
    value = (short) val;
  }

  @Override
  public void subtract(@NotNull Number number) throws ArithmeticException {
    value -= number.longValue();
  }

  @Override
  public void subtractExact(@NotNull Number number) throws ArithmeticException {
    var val = Math.subtractExact(longValue(), number.longValue());
    testRange(val);
    value = (short) val;
  }

  @Override
  public void multiply(@NotNull Number number) throws ArithmeticException {
    if (number.isFloatingPoint()) {
      value *= number.doubleValue();
    } else {
      value *= number.longValue();
    }
  }

  @Override
  public void multiplyExact(@NotNull Number number) throws ArithmeticException {
    var val = Math.multiplyExact(longValue(), number.longValue());
    testRange(val);
    value = (short) val;
  }

  @Override
  public void divide(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      value = (short) (intValue() / number.doubleValue());
    } else {
      value = (short) (longValue() / number.longValue());
    }
  }

  @Override
  public void divideExact(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      var val = intValue() / number.doubleValue();
      testRange(val);
      value = (short) val;
    } else {
      var val = longValue() / number.longValue();
      testRange(val);
      value = (short) val;
    }
  }

  @Override
  public UInt16 clone() {
    return new UInt16(value);
  }

  @Override
  public String toString() {
    return Integer.toString(Short.toUnsignedInt(value));
  }

}
