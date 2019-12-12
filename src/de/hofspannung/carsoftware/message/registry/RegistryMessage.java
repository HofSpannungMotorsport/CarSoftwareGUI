package de.hofspannung.carsoftware.message.registry;

import de.hofspannung.carsoftware.message.Message;
import de.hofspannung.carsoftware.registry.Entry;

public class RegistryMessage extends Message {

/*
4       4  		8			16					32
0000    0000	00000000	00000000 00000000	00000000 00000000 00000000 00000000
Action  Flags	Registry	Entry				Value (optional)

Flags:
1 has registry
2 has entry
3 has value
4
*/

    private RegistryAction action;
    private byte registry;
    private boolean hasRegistry;
    private short entry;
    private boolean hasEntry;
    private int value;
    private boolean hasValue;

    public RegistryMessage(Entry<?> entry, RegistryAction action) {
        super();
        // TODO Konstruktor
    }

    @Override
    public byte[] toByteArray() {
        int size = 1;
        if (hasRegistry) size += 1;
        if (hasEntry) size += 2;
        if (hasValue) size += 4;

        int pointer = 1;
        byte[] ret = new byte[size];

        int head = (action.ordinal() << 4) & 0xF0;

        if (hasRegistry) {
            head |= 0x8;
            ret[pointer++] = registry;
        }
        if (hasEntry) {
            head |= 0x4;
            ret[pointer++] = (byte) (entry << 8);
            ret[pointer++] = (byte) entry;
        }
        if (hasValue) {
            head |= 0x2;
            ret[pointer++] = (byte) (value << 24);
            ret[pointer++] = (byte) (value << 16);
            ret[pointer++] = (byte) (value << 8);
            ret[pointer++] = (byte) value;
        }

        ret[0] = (byte) head;

        return ret;
    }
}
