package de.hofspannung.carsoftware.data.number;

import org.jetbrains.annotations.NotNull;

public class Int32 extends Number {

  public static final int MIN = Integer.MIN_VALUE;
  public static final int MAX = Integer.MAX_VALUE;

  private int value;

  public Int32(int value) {
    this.value = value;
  }

  @Override
  public int serializedAsInt() {
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

  @NotNull
  @Override
  public Number minValue() {
    return new Int32(MIN);
  }

  @NotNull
  @Override
  public Number maxValue() {
    return new Int32(MAX);
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
    var val = Math.subtractExact(value, number.longValue());
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
    if (number.isFloatingPoint()) {
      var val = value * number.doubleValue();
      testRange(val);
      value = (int) val;
    } else {
      var val = Math.multiplyExact(value, number.longValue());
      testRange(val);
      value = (int) val;
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
      value = (int) val;
    } else {
      var val = value / number.longValue();
      testRange(val);
      value = (int) val;
    }
    changed();
  }

  @Override
  public Int32 clone() {
    return new Int32(value);
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }
}
