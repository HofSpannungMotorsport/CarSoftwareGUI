package de.hofspannung.carsoftware.registry;

import de.hofspannung.carsoftware.data.number.Float;
import de.hofspannung.carsoftware.data.number.Int16;
import de.hofspannung.carsoftware.data.number.Int32;
import de.hofspannung.carsoftware.data.number.Int8;
import de.hofspannung.carsoftware.data.number.Number;
import de.hofspannung.carsoftware.data.number.UInt16;
import de.hofspannung.carsoftware.data.number.UInt32;
import de.hofspannung.carsoftware.data.number.UInt8;
import de.hofspannung.carsoftware.exception.DuplicateException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class RegistryBuilder {

  private final JSONObject json;

  public RegistryBuilder(File json) throws FileNotFoundException {
    var tokener = new JSONTokener(new FileInputStream(json));
    this.json = new JSONObject(tokener);
  }

  public Registry<?> build() {
    var registry = makeRegistry();

    fillRegistry(registry);

    return registry;
  }

  private Registry<?> makeRegistry() {
    var type = json.getEnum(RegistryType.class, "type");
    var name = json.getString("name");
    var defValue = json.getDouble("default");

    switch (type) {
      case Float:
        return new Registry<>(name, new Float((float) defValue));
      case UInt8:
        return new Registry<>(name, new UInt8((short) defValue));
      case UInt16:
        return new Registry<>(name, new UInt16((int) defValue));
      case UInt32:
        return new Registry<>(name, new UInt32((long) defValue));
      case Int8:
        return new Registry<>(name, new Int8((byte) defValue));
      case Int16:
        return new Registry<>(name, new Int16((short) defValue));
      case Int32:
        return new Registry<>(name, new Int32((int) defValue));
      default:
        return null;
    }
  }

  private <T extends Number> void fillRegistry(Registry<T> registry) {
    var array = json.getJSONObject("entries");
    var keys = array.keys();

    keys.forEachRemaining(key -> {
      var index = Integer.parseInt(key);
      var obj = array.getJSONObject(key);
      var name = obj.getString("name");
      var unit = obj.getString("unit");

      var editable = obj.has("editable") && obj.getBoolean("editable");

      Entry<?> entry = null;
      try {
        entry = registry.addNewEntry(index, editable, name, unit);
      } catch (DuplicateException e) {
        e.printStackTrace();
        return;
      }

      if (obj.has("value")) {
        entry.getValue().cast(obj.getDouble("value"));
      }
      if (obj.has("min")) {
        entry.getMin().cast(obj.getDouble("min"));
      }
      if (obj.has("max")) {
        entry.getMax().cast(obj.getDouble("max"));
      }
    });
  }

}
