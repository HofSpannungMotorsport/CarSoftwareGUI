package de.hofspannung.carsoftware.registry;

import de.hofspannung.carsoftware.data.number.Number;
import de.hofspannung.carsoftware.exception.DuplicateException;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;


public class Registry<T extends Number> {

  @NotNull
  private final String name;
  private final RegistryType type;
  @NotNull
  private final T defaultValue;
  @NotNull
  private final ArrayList<Entry<T>> entries = new ArrayList<>();

  /**
   * Creates a new registry with a default value.
   *
   * @param defaultValue Default value for entries. Cannot be null.
   */
  public Registry(@NotNull T defaultValue) {
    this(defaultValue.getClass().getSimpleName(), defaultValue);
  }

  /**
   * Creates a new registry with a name and default value for its entries.
   *
   * @param name         Name of this registry.
   * @param defaultValue Default value for entries. Cannot be null.
   */
  public Registry(@NotNull String name, @NotNull T defaultValue) {
    this.name = name;
    this.defaultValue = defaultValue;
    this.type = RegistryType.valueOf(defaultValue.getClass().getSimpleName());
  }

  /**
   * Adds an entry to this registry. If it already exists, it won't be added.
   *
   * @param entry Entry to add.
   * @return true if it was added, false otherwise.
   */
  public boolean addEntry(@NotNull Entry<T> entry) {
    if (entries.contains(entry)) {
      return false;
    }

    entries.add(entry);
    return true;
  }

  public void addNewEntry(int index, @NotNull String name) throws DuplicateException {
    new Entry<T>(this, index, name);
  }

  public void addNewEntry(int index, @NotNull String name, String unit) throws DuplicateException {
    new Entry<>(this, index, name, unit);
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
  @NotNull
  public String getName() {
    return name;
  }

  /**
   * Type of the registry.
   *
   * @return Name of the type.
   */
  public String getTypeName() {
    return type.name();
  }

  /**
   * Default value of the registry.
   *
   * @return Default value.
   */
  public @NotNull T getDefaultValue() {
    return defaultValue;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Registry)) {
      return false;
    }

    var registry = (Registry<?>) obj;

    return this.defaultValue.equals(registry.defaultValue)
        && this.type == registry.type;
  }

}