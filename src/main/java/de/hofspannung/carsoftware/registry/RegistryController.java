package de.hofspannung.carsoftware.registry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class RegistryController {

  private Map<RegistryType, Registry<?>> registries = new EnumMap<>(RegistryType.class);

  public RegistryController() {
  }

  public Registry<?> getRegistry(RegistryType type) {
    return registries.get(type);
  }

  public Set<RegistryType> getTypes() {
    return registries.keySet();
  }

  public boolean buildFromFile(File file) {
    try {
      var builder = new RegistryBuilder(file);
      var registry = builder.build();
      return registries.putIfAbsent(registry.getType(), registry) == null;
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return false;
    }
  }

}
