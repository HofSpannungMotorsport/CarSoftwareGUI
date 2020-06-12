package de.hofspannung.carsoftware.data.number;

import de.hofspannung.carsoftware.data.number.Number;

public interface NumberChangeListener<T extends Number> {

  void changed(T number);
}
