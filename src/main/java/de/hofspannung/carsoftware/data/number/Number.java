package de.hofspannung.carsoftware.data.number;

import de.hofspannung.carsoftware.data.IntegerSerializable;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public abstract class Number extends java.lang.Number implements IntegerSerializable,
    Comparable<Number>, Cloneable {

  private List<NumberChangeListener<Number>> changeListeners = new LinkedList<>();

  /**
   * The minimum value this number can hold (nearest to negative infinity).
   *
   * @return Min value.
   */
  @NotNull
  public abstract Number minValue();

  /**
   * The maximum value this number can hold (nearest to positive infinity).
   *
   * @return Max value.
   */
  @NotNull
  public abstract Number maxValue();

  /**
   * If this number is a floating point number.
   *
   * @return true if it is.
   */
  public abstract boolean isFloatingPoint();

  /**
   * Adds the given number to this one. This number will contain the result of the operation.
   *
   * @param number Number to add.
   */
  public abstract void add(@NotNull Number number);

  /**
   * Adds the given number to this one and checks for overflows. This number will contain the result
   * of the operation.
   *
   * @param number Number to add.
   * @throws ArithmeticException if the result would be out of range ({@code inRange(result) == *
   *                             false}).
   */
  public abstract void addExact(@NotNull Number number) throws ArithmeticException;

  /**
   * Subtracts the given number from this one. This number will contain the result of the
   * operation.
   *
   * @param number Number to subtract.
   */
  public abstract void subtract(@NotNull Number number);

  /**
   * Subtracts the given number from this one and checks for overflows. This number will contain the
   * result of the operation.
   *
   * @param number Number to subtract.
   * @throws ArithmeticException if the result would be out of range ({@code inRange(result) == *
   *                             false}).
   */
  public abstract void subtractExact(@NotNull Number number) throws ArithmeticException;

  /**
   * Multiplies the given number with this one. This number will contain the result of the
   * operation.
   *
   * @param number Number to multiply with.
   */
  public abstract void multiply(@NotNull Number number);

  /**
   * Multiplies the given number with this one and checks for overflows. This number will contain
   * the result of the  operation.
   *
   * @param number Number to multiply with.
   * @throws ArithmeticException if the result would be out of range ({@code inRange(result) ==
   *                             false}).
   */
  public abstract void multiplyExact(@NotNull Number number) throws ArithmeticException;

  /**
   * Divides this number by the given number. This number will contain  the result of the
   * operation.
   *
   * @param number Number to divide by.
   * @throws ArithmeticException if {@code number} is exactly zero ({@code number.isZeroExact() ==
   *                             true}).
   */
  public abstract void divide(@NotNull Number number) throws ArithmeticException;

  /**
   * Divides this number by the given number. This number will contain  the result of the
   * operation.
   *
   * @param number Number to divide by.
   * @throws ArithmeticException if the result would be out of range ({@code inRange(result) ==
   *                             false} or {@code number} is exactly zero ({@code
   *                             number.isZeroExact() == true}).
   */
  public abstract void divideExact(@NotNull Number number) throws ArithmeticException;

  /**
   * Checks if the value fits in the range of this data type.
   *
   * @param number Value to check.
   * @return true if in range, false otherwise.
   */
  public boolean inRange(long number) {
    return number >= minValue().longValue() && number <= maxValue().longValue();
  }

  /**
   * Checks if the value fits in the range of this data type.
   *
   * @param number Value to check.
   * @return true if in range, false otherwise.
   */
  public boolean inRange(double number) {
    return Double.isFinite(number) && number >= minValue().doubleValue() && number <= maxValue()
        .doubleValue();
  }

  /**
   * Tests a value if it is inside range. Does nothing if so.
   *
   * @param number Value to check.
   * @throws ArithmeticException if not in range
   */
  protected void testRange(long number) throws ArithmeticException {
    if (!inRange(number)) {
      throw new ArithmeticException("Result not in range");
    }
  }

  /**
   * Tests a value if it is inside range. Does nothing if so.
   *
   * @param number Value to check.
   * @throws ArithmeticException if not in range
   */
  protected void testRange(double number) throws ArithmeticException {
    if (!inRange(number)) {
      throw new ArithmeticException("Result not in range");
    }
  }

  /**
   * Test for exactly zero.
   *
   * @return {@code true} if this number is exactly zero.
   */
  public boolean isZeroExact() {
    if (isFloatingPoint()) {
      return doubleValue() == 0.0;
    } else {
      return longValue() == 0;
    }
  }

  protected void testZeroDivision() {
    if (isZeroExact()) {
      throw new ArithmeticException("Division by zero");
    }
  }

  /**
   * Compares this number to another.
   *
   * @param number Number to compare to.
   * @return {@code 0} if the numbers are numerically equal. {@code -1} if this number is smaller
   * than {@code number}. {@code 1} if this number is greater than {@code number}.
   */
  public int compareTo(@NotNull Number number) {
    if (isFloatingPoint() || number.isFloatingPoint()) {
      return Double.compare(doubleValue(), number.doubleValue());
    } else {
      return Long.compare(longValue(), number.longValue());
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Number) {
      return compareTo((Number) obj) == 0;
    }
    return false;
  }

  @Override
  public abstract Number clone();

  /**
   * This number as a string.
   *
   * @return Decimal representation.
   */
  @Override
  public abstract String toString();

  public void addChangeListener(NumberChangeListener<Number> listener) {
    changeListeners.add(listener);
  }

  public void removeChangeListener(NumberChangeListener<Number> listener) {
    changeListeners.remove(listener);
  }

  protected void changed() {
    changeListeners.forEach(l -> {
      l.changed(this);
    });
  }
}
