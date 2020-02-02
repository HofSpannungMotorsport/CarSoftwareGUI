package de.hofspannung.carsoftware.message.registry;

import de.hofspannung.carsoftware.exception.ParseException;
import org.junit.Test;

public class TestRegistryMessage extends RegistryMessage {

  public TestRegistryMessage() {
    super(RegistryAction.VALUE, (byte) 0, false, (short) 0, false, 0, false);
  }


  @Test
  public void testRegistryMessage() throws ParseException {
    int values = 8;

    byte registry = 0;
    short entry = 0;
    int value = 0;
    int flags = 0x0;
    for (RegistryAction action : RegistryAction.values()) {
      registry = Byte.MIN_VALUE;
      do {

        entry = Short.MIN_VALUE;
        do {

          value = Integer.MIN_VALUE;
          do {

            flags = 0x0;
            do {

              testRegistryMessage(action,
                  registry, (flags & 0x1) > 0,
                  entry, (flags & 0x2) > 0,
                  value, (flags & 0x4) > 0);

              flags++;
            } while (flags < 0x8);

            value += ((long) Integer.MAX_VALUE + 1) / values;
          } while (value != Integer.MIN_VALUE);

          entry += (Short.MAX_VALUE + 1) / values;
        } while (entry != Short.MIN_VALUE);

        registry += (Byte.MAX_VALUE + 1) / values;
      } while (registry != Byte.MIN_VALUE);
    }
  }

  private void testRegistryMessage(RegistryAction action,
      byte registry, boolean hasRegistry,
      short entry, boolean hasEntry,
      int value, boolean hasValue) throws ParseException {
    RegistryMessage m1 = new RegistryMessage(action, registry, hasRegistry, entry, hasEntry, value,
        hasValue);

    RegistryMessage m2 = new RegistryMessage(m1.toBytes().getArray());

    assert m2.action == action;
    assert m2.hasRegistry == hasRegistry;
    assert !hasRegistry || m2.registry == registry;
    assert m2.hasEntry == hasEntry;
    if (hasEntry) {
      assert m2.entry == entry;
    }
    assert m2.hasValue == hasValue;
    if (hasValue) {
      assert m2.value == value;
    }
  }
}
