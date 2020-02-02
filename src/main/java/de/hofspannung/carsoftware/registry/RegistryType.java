package de.hofspannung.carsoftware.registry;

public enum RegistryType {
  NONE, FLOAT, UINT8, UINT16, UINT32, INT8, INT16, INT32, BOOLEAN;


  public static boolean isObjectOfType(RegistryType type, Object obj) {
    assert obj != null;
    System.out.println(type);
    System.out.println(obj.getClass());
    switch (type) {
      case FLOAT:
        return obj.getClass().equals(Float.class);
      case UINT8:
      case INT16:
        return obj.getClass().equals(Short.class);
      case UINT16:
      case INT32:
        return obj.getClass().equals(Integer.class);
      case UINT32:
        return obj.getClass().equals(Long.class);
      case INT8:
        return obj.getClass().equals(Byte.class);
      case BOOLEAN:
        return obj.getClass().equals(Boolean.class);
      default:
        return false;
    }
  }
}
