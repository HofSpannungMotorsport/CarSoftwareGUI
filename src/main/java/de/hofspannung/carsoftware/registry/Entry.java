package de.hofspannung.carsoftware.registry;

import de.hofspannung.carsoftware.exception.DuplicateException;

public class Entry<T> {

    private Registry<T> registry;

    private int index;
    private T value;

    private String name;
    private String unit;

    /**
     * Creates a new entry and adds it to the registry.
     *
     * @param registry Registry this belongs to.
     * @param index    Index of this entry.
     * @throws DuplicateException If this entry already exists in the registry.
     */
    public Entry(Registry<T> registry, int index) throws DuplicateException {
        this(registry, index, null, null);
    }

    /**
     * Creates a new entry and adds it to the registry.
     *
     * @param registry Registry this belongs to.
     * @param index    Index of this entry.
     * @param name     Name of this.
     * @param unit     Unit of measurement of this.
     * @throws DuplicateException If this entry already exists in the registry.
     */
    public Entry(Registry<T> registry, int index, String name, String unit) throws DuplicateException {
        super();

        assert registry != null : "Registry must not be 'null'.";
        assert index >= 0 : "Index must not be negative.";

        this.registry = registry;
        this.index = index;
        this.value = registry.getDefaultValue();
        this.name = name;
        this.unit = unit;
        if (!this.registry.addEntry(this)) {
            throw new DuplicateException("This entry already exists in the registry!");
        }
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Registry<T> getRegistry() {
        return registry;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entry<?>))
            return false;

        Entry<?> entry = (Entry<?>) obj;

        if (!this.registry.equals(entry.registry))
            return false;

        if (this.index != entry.index)
            return false;

        if (this.value.getClass() != entry.value.getClass())
            return false;

        return true;
    }

}
