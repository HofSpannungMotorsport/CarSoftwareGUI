package de.hofspannung.carsoftware.exception;

public class TooMuchDataException extends Exception {

  boolean hasLength;
  long length;
  long maxLength;

  public TooMuchDataException() {
    super();
  }

  public TooMuchDataException(String message) {
    super(message);
  }

  public TooMuchDataException(long length, long maxLength) {
    super();
    this.length = length;
    this.maxLength = maxLength;
    this.hasLength = true;
  }

  public TooMuchDataException(String message, long length, long maxLength) {
    this(message);
    this.length = length;
    this.maxLength = maxLength;
    this.hasLength = true;
  }

  @Override
  public String toString() {
    String s = super.toString();
    if (hasLength) {
      s += "\nAdditional INFO:   length: " + length + "   maxLength: " + maxLength;
    }
    return s;
  }
}
