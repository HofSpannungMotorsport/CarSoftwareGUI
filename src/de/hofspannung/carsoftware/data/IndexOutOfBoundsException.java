package de.hofspannung.carsoftware.data;

/**
 * {@link java.lang.IndexOutOfBoundsException} with value and bounds information.
 * <p>
 * The bounds should be inclusive, that means if the value cannot be negative, the lower bound should be 0.
 */
public class IndexOutOfBoundsException extends java.lang.IndexOutOfBoundsException {

    protected long upperBound;
    protected long lowerBound;
    protected long value;
    protected boolean valueGiven;
    protected boolean boundsGiven;

    public IndexOutOfBoundsException() {
        super();
    }

    public IndexOutOfBoundsException(String message) {
        super(message);
    }

    public IndexOutOfBoundsException(String message, long value) {
        super(message);
        this.value = value;
        valueGiven = true;
    }

    public IndexOutOfBoundsException(String message, long value, long lowerBound, long upperBound) {
        super(message);
        this.value = value;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        valueGiven = true;
        boundsGiven = true;
    }

    public IndexOutOfBoundsException(long value) {
        super();
        this.value = value;
        valueGiven = true;
    }

    public IndexOutOfBoundsException(long value, long lowerBound, long upperBound) {
        super();
        this.value = value;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        valueGiven = true;
        boundsGiven = true;
    }

    public long getUpperBound() {
        return upperBound;
    }

    public long getLowerBound() {
        return lowerBound;
    }

    public long getValue() {
        return value;
    }

    public boolean isValueGiven() {
        return valueGiven;
    }

    public boolean isBoundsGiven() {
        return boundsGiven;
    }

    @Override
    public String toString() {
        String s = super.toString();
        if (valueGiven || boundsGiven)
            s += "\n";
        if (valueGiven)
            s += "Value: " + value + "    ";
        if (boundsGiven)
            s += "Bounds: " + lowerBound + " to " + upperBound;
        return s;
    }
}
