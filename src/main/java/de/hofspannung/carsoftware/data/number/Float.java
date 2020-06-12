package de.hofspannung.carsoftware.data.number;

import org.jetbrains.annotations.NotNull;

public class Float extends Number {

  public static final float MAX = java.lang.Float.MAX_VALUE;
  public static final float MIN = -java.lang.Float.MAX_VALUE;
  private float value;

  public Float(float value) {
    this.value = value;
  }

  @Override
  public int serializedAsInt() {
    return java.lang.Float.floatToIntBits(value);
  }

  @Override
  public int intValue() {
    return (int) value;
  }

  @Override
  public long longValue() {
    return (long) value;
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
    return new Float(MIN);
  }

  @Override
  public @NotNull Number maxValue() {
    return new Float(MAX);
  }

  @Override
  public boolean isFloatingPoint() {
    return true;
  }

  @Override
  public void add(@NotNull Number number) throws ArithmeticException {
    value += number.doubleValue();
    changed();
  }

  @Override
  public void addExact(@NotNull Number number) throws ArithmeticException {
    var val = value + number.doubleValue();
    testRange(val);
    value = (float) val;
    changed();
  }

  @Override
  public void subtract(@NotNull Number number) throws ArithmeticException {
    value -= number.doubleValue();
    changed();
  }

  @Override
  public void subtractExact(@NotNull Number number) throws ArithmeticException {
    var val = value - number.doubleValue();
    testRange(val);
    value = (float) val;
    changed();
  }

  @Override
  public void multiply(@NotNull Number number) throws ArithmeticException {
    value *= number.doubleValue();
    changed();
  }

  @Override
  public void multiplyExact(@NotNull Number number) throws ArithmeticException {
    var val = value * number.doubleValue();
    testRange(val);
    value = (float) val;
    changed();
  }

  @Override
  public void divide(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    value /= number.doubleValue();
    changed();
  }

  @Override
  public void divideExact(@NotNull Number number) throws ArithmeticException {
    number.testZeroDivision();
    var val = value / number.doubleValue();
    testRange(val);
    value = (float) val;
    changed();
  }

  @Override
  public Float clone() {
    return new Float(value);
  }

  @Override
  public String toString() {
    return java.lang.Float.toString(value);
  }
}
