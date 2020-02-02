package de.hofspannung.carsoftware.registry;

import de.hofspannung.carsoftware.data.IntegerSerializable;
import de.hofspannung.carsoftware.exception.DuplicateException;
import java.util.ArrayList;


public class Registry<T extends IntegerSerializable> {

  private RegistryType type = RegistryType.NONE;

  private String name;
  private String typeName;

  private T defaultValue;

  private ArrayList<Entry<T>> entries;

  /**
   * Creates a new registry with a default value.
   *
   * @param defaultValue Default value for entries. Cannot be null.
   */
  public Registry(RegistryType type, T defaultValue) {
    this(type, null, defaultValue);
  }

  /**
   * Creates a new registry with a name and default value for its entrys.
   *
   * @param name         Name of this registry.
   * @param defaultValue Default value for entries. Cannot be null.
   */
  public Registry(RegistryType type, String name, T defaultValue) {
    assert type != RegistryType.NONE : "Type of Registry must not be 'NONE'.";
    assert defaultValue != null : "Default value of registry must not be 'null'.";
    assert RegistryType
        .isObjectOfType(type, defaultValue) : "Type of registry and template type do not match.";

    this.type = type;
    this.name = name;
    this.typeName = defaultValue.getClass().getSimpleName();
    this.defaultValue = defaultValue;
  }

  /**
   * Adds an entry to this registry. If it already exists, it won't be added.
   *
   * @param entry Entry to add.
   * @return true if it was added, false otherwise.
   */
  public boolean addEntry(Entry<T> entry) {
    if (entries.contains(entry)) {
      return false;
    }

    entries.add(entry);
    return true;
  }

  public void addNewEntry(int index) throws DuplicateException {
    new Entry<T>(this, index);
  }

  public void addNewEntry(int index, String name, String unit) throws DuplicateException {
    new Entry<T>(this, index, name, unit);
  }

  public boolean containsEntry(Entry<T> entry) {
    return entries.contains(entry);
  }

  /**
   * Type of the registry.
   *
   * @return type
   */
  public RegistryType getType() {
    return type;
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
    if (!(obj instanceof Registry)) {
      return false;
    }
    return true;
  }

}