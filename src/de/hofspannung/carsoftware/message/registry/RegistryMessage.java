package de.hofspannung.carsoftware.message.registry;

import de.hofspannung.carsoftware.data.ByteArray;
import de.hofspannung.carsoftware.message.Message;
import de.hofspannung.carsoftware.message.MessageType;
import de.hofspannung.carsoftware.message.ParseException;
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

    protected static MessageType type = MessageType.REGISTRY;

    protected RegistryAction action;
    protected byte registry;
    protected boolean hasRegistry;
    protected short entry;
    protected boolean hasEntry;
    protected int value;
    protected boolean hasValue;

    public RegistryMessage(Entry<?> entry, RegistryAction action) {
        super();
        // TODO Konstruktor
    }

    public static RegistryMessage fromBytes(byte[] arr) throws ParseException {
        if (!firstByteCorrect(arr[0], getType()))
            throw new ParseException("Wrong message type!");
        return null;
    }

    @Override
    public byte[] toBytes() {
        ByteArray array = new ByteArray();

        int size = 2;
        if (hasRegistry) size += 1;
        if (hasEntry) size += 2;
        if (hasValue) size += 4;

        array.add(firstByte());

        int head = (action.ordinal() << 4) & 0xF0;

        if (hasRegistry) {
            head |= 0x8;
            array.add(registry);
        }
        if (hasEntry) {
            head |= 0x4;
            array.addShort(entry);
        }
        if (hasValue) {
            head |= 0x2;
            array.addInt(value);
        }

        array.addByte(1, head);

        return array.getArray();
    }
}
