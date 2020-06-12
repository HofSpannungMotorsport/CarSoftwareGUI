package de.hofspannung.carsoftware.registry;


import de.hofspannung.carsoftware.data.number.Number;

public interface EntryValueChangedListener<T extends Number> {

  void changed(Entry<T> entry, T value);
}
