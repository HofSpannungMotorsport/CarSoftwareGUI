package de.hofspannung.carsoftware.data.number;

import org.jetbrains.annotations.NotNull;

public class Int16 extends Number {

  public static final short MIN = Short.MIN_VALUE;
  public static final short MAX = Short.MAX_VALUE;
  private short value;

  public Int16(short value) {
    this.value = value;
  }

  @Override
  public int serializedAsInt() {
    return Short.toUnsignedInt(value);
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
    return new Int16(MIN);
  }

  @Override
  public @NotNull Number maxValue() {
    return new Int16(MAX);
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
    var val = Math.addExact(value, number.longValue());
    testRange(val);
    value = (short) val;
  }

  @Override
  public void subtract(@NotNull Number number) throws ArithmeticException {
    value -= number.longValue();
  }

  @Override
  public void subtractExact(@NotNull Number number) throws ArithmeticException {
    var val = Math.subtractExact(value, number.longValue());
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
    if (number.isFloatingPoint()) {
      var val = value * number.doubleValue();
      testRange(val);
      value = (short) val;
    } else {
      var val = Math.multiplyExact(value, number.longValue());
      testRange(val);
      value = (short) val;
    }
  }

  @Override
  public void divide(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      value /= number.doubleValue();
    } else {
      value /= number.longValue();
    }
  }

  @Override
  public void divideExact(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    if (number.isFloatingPoint()) {
      var val = value / number.doubleValue();
      testRange(val);
      value = (short) val;
    } else {
      var val = value / number.longValue();
      testRange(val);
      value = (short) val;
    }
  }

  @Override
  public Int16 clone() {
    return new Int16(value);
  }

  @Override
  public String toString() {
    return Short.toString(value);
  }
}
