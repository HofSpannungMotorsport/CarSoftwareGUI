package de.hofspannung.carsoftware.registry;

import de.hofspannung.carsoftware.data.number.Number;
import de.hofspannung.carsoftware.exception.DuplicateException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class Entry<T extends Number> {

  private static final int MAX_INDEX = Integer.MAX_VALUE;

  @NotNull
  private Registry<T> registry;

  @Range(from = 0, to = MAX_INDEX)
  private int index;
  @NotNull
  private T value;

  @NotNull
  private String name;
  private String unit;

  /**
   * Creates a new entry and adds it to the registry.
   *
   * @param registry Registry this belongs to.
   * @param index    Index of this entry.
   * @throws DuplicateException If this entry already exists in the registry.
   */
  public Entry(@NotNull Registry<T> registry, @Range(from = 0, to = MAX_INDEX) int index)
      throws DuplicateException {
    this(registry, index, String.format("%s-%03d", registry.getTypeName(), index));
  }

  /**
   * Creates a new entry and adds it to the registry.
   *
   * @param registry Registry this belongs to.
   * @param index    Index of this entry.
   * @throws DuplicateException If this entry already exists in the registry.
   */
  public Entry(@NotNull Registry<T> registry, @Range(from = 0, to = MAX_INDEX) int index,
      @NotNull String name)
      throws DuplicateException {
    this(registry, index, name, null);
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
  public Entry(@NotNull Registry<T> registry, @Range(from = 0, to = MAX_INDEX) int index,
      @NotNull String name, String unit)
      throws DuplicateException {
    super();

    this.registry = registry;
    this.index = index;
    this.value = registry.getDefaultValue();
    this.name = name;
    this.unit = unit;
    if (!this.registry.addEntry(this)) {
      throw new DuplicateException("This entry already exists in the registry!");
    }
  }

  @NotNull
  public T getValue() {
    return value;
  }

  public void setValue(@NotNull T value) {
    this.value = value;
  }

  @NotNull
  public Registry<T> getRegistry() {
    return registry;
  }

  public int getIndex() {
    return index;
  }

  @NotNull
  public String getName() {
    return name;
  }

  public String getUnit() {
    return unit;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Entry<?>)) {
      return false;
    }

    var entry = (Entry<?>) obj;

    if (!this.registry.equals(entry.registry)) {
      return false;
    }

    if (this.index != entry.index) {
      return false;
    }

    if (this.value.getClass() != entry.value.getClass()) {
      return false;
    }

    return true;
  }

}
