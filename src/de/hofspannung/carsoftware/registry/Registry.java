package de.hofspannung.carsoftware.registry;

import java.util.ArrayList;

public class Registry<T> {
    private String name;
    private String typeName;

    private T defaultValue;

    private ArrayList<Entry<T>> entries;

    /**
     * Creates a new registry with a default value.
     *
     * @param defaultValue Default value for entries. Cannot be null.
     */
    public Registry(T defaultValue) {
        this(null, defaultValue);
    }

    /**
     * Creates a new registry with a name and default value for its entrys.
     *
     * @param name         Name of this registry.
     * @param defaultValue Default value for entries. Cannot be null.
     */
    public Registry(String name, T defaultValue) {
        assert defaultValue != null;
        this.name = name;
        this.typeName = defaultValue.getClass().getSimpleName();
        this.defaultValue = defaultValue;
    }

    /**
     * Adds an entry to this registry.
     * If it already exists, it won't be added.
     *
     * @param entry Entry to add.
     * @return true if it was added, false otherwise.
     */
    public boolean addEntry(Entry<T> entry) {
        if (entries.contains(entry))
            return false;

        entries.add(entry);
        return true;
    }

    public boolean containsEntry(Entry<T> entry) {
        return entries.contains(entry);
    }

    /**
     * Name of the registry.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Type of the registry.
     *
     * @return Name of the type.
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Default value of the registry.
     *
     * @return Default value.
     */
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Registry))
            return false;
        return true;
    }

}