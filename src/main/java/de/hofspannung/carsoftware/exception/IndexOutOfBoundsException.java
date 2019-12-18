package de.hofspannung.carsoftware.exception;

/**
 * {@link java.lang.IndexOutOfBoundsException} with value and bounds information.
 * <p>
 * The bounds should be inclusive, that means if the value cannot be negative, the lower bound should be 0.
 */
public class IndexOutOfBoundsException extends java.lang.IndexOutOfBoundsException {

    protected long upperBound;
    protected long lowerBound;
    protected long index;
    protected boolean indexGiven;
    protected boolean boundsGiven;

    public IndexOutOfBoundsException() {
        super();
    }

    public IndexOutOfBoundsException(String message) {
        super(message);
    }

    public IndexOutOfBoundsException(String message, long index) {
        super(message);
        this.index = index;
        indexGiven = true;
    }

    public IndexOutOfBoundsException(String message, long index, long lowerBound, long upperBound) {
        super(message);
        this.index = index;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        indexGiven = true;
        boundsGiven = true;
    }

    public IndexOutOfBoundsException(long index) {
        super();
        this.index = index;
        indexGiven = true;
    }

    public IndexOutOfBoundsException(long index, long lowerBound, long upperBound) {
        super();
        this.index = index;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        indexGiven = true;
        boundsGiven = true;
    }

    public long getUpperBound() {
        return upperBound;
    }

    public long getLowerBound() {
        return lowerBound;
    }

    public long getIndex() {
        return index;
    }

    public boolean indexGiven() {
        return indexGiven;
    }

    public boolean boundsGiven() {
        return boundsGiven;
    }

    @Override
    public String toString() {
        String s = super.toString();
        if (indexGiven || boundsGiven)
            s += "\nAdditional INFO:   ";
        if (indexGiven)
            s += "Value: " + index + "   ";
        if (boundsGiven)
            s += "Bounds: " + lowerBound + " to " + upperBound;
        return s;
    }
}
