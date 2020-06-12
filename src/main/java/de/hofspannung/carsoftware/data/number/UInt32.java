package de.hofspannung.carsoftware.data.number;

import jdk.jfr.Unsigned;
import org.jetbrains.annotations.NotNull;

public class UInt32 extends Number {

  public static final long MIN = 0;
  public static final long MAX = 0xffffffff;

  @Unsigned
  private int value;

  public UInt32(long value) {
    this((int) value);
  }

  protected UInt32(int rawValue) {
    this.value = rawValue;
  }

  @Override
  public int serializedAsInt() {
    return value;
  }

  @Override
  public int intValue() {
    return (int) longValue();
  }

  @Override
  public long longValue() {
    return Integer.toUnsignedLong(value);
  }

  @Override
  public float floatValue() {
    return longValue();
  }

  @Override
  public double doubleValue() {
    return longValue();
  }

  @Override
  public @NotNull Number minValue() {
    return new UInt32(MIN);
  }

  @Override
  public @NotNull Number maxValue() {
    return new UInt32(MAX);
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
    value = (int) val;
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
    value = (int) val;
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
    value = (int) val;
    changed();
  }

  @Override
  public void divide(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      value = (int) (longValue() / number.doubleValue());
    } else {
      value = (int) (longValue() / number.longValue());
    }
    changed();
  }

  @Override
  public void divideExact(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      var val = longValue() / number.doubleValue();
      testRange(val);
      value = (int) val;
    } else {
      var val = longValue() / number.longValue();
      testRange(val);
      value = (int) val;
    }
    changed();
  }

  @Override
  public UInt32 clone() {
    return new UInt32(value);
  }

  @Override
  public String toString() {
    return Integer.toUnsignedString(value);
  }
}
