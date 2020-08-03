package de.hofspannung.carsoftware.registry;

import de.hofspannung.carsoftware.data.number.Number;
import de.hofspannung.carsoftware.exception.DuplicateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;


public class Registry<T extends Number> {

  @NotNull
  private final String name;
  private final RegistryType type;
  @NotNull
  private final T defaultValue;
  @NotNull
  private final ArrayList<Entry<T>> entries = new ArrayList<>();

  private List<EntryValueChangedListener<T>> entryValueChangedListeners = new LinkedList<>();
  private EntryValueChangedListener<T> entryValueChangedListener = this::entryChanged;

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
    entry.addValueChangedListener(entryValueChangedListener);
    return true;
  }

  public Entry<T> addNewEntry(int index, @NotNull String name) throws DuplicateException {
    var entry = new Entry<T>(this, index, name);
    entry.addValueChangedListener(entryValueChangedListener);
    return entry;
  }

  public Entry<T> addNewEntry(int index, @NotNull String name, String unit)
      throws DuplicateException {
    var entry = new Entry<T>(this, index, name, unit);
    entry.addValueChangedListener(entryValueChangedListener);
    return entry;
  }

  /**
   * Returns a list of all entries.
   *
   * @return Readonly list with this registries entries.
   */
  public List<Entry<T>> getEntries() {
    return Collections.unmodifiableList(entries);
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
   * Type of the registry class.
   *
   * @return Number class of the type.
   */
  public Class<T> getTypeClass() {
    return (Class<T>) defaultValue.getClass();
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

  public void addEntryValueChangedListener(EntryValueChangedListener<T> listener) {
    entryValueChangedListeners.add(listener);
  }

  public void removeEntryValueChangedListener(EntryValueChangedListener<T> listener) {
    entryValueChangedListeners.remove(listener);
  }

  private void entryChanged(Entry<T> entry, T value) {
    entryValueChangedListeners.forEach(l -> {
      l.changed(entry, value);
    });
  }

}