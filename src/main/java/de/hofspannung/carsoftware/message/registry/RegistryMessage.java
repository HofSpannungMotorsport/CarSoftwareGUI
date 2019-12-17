package de.hofspannung.carsoftware.message.registry;

import de.hofspannung.carsoftware.data.ByteArrayList;
import de.hofspannung.carsoftware.exception.ParseException;
import de.hofspannung.carsoftware.message.Message;
import de.hofspannung.carsoftware.message.MessageType;
import de.hofspannung.carsoftware.registry.Entry;

public class RegistryMessage extends Message {

    /*
    4       4  		8			16					32
    0000    0000	00000000	00000000 00000000	00000000 00000000 00000000 00000000
    Action  Flags	Registry	Entry				Value

    Flags:
    1 has registry
    2 has entry
    3 has value
    4
    */

    public static MessageType type = MessageType.REGISTRY;

    protected RegistryAction action;
    protected byte registry;
    protected boolean hasRegistry;
    protected short entry;
    protected boolean hasEntry;
    protected int value;
    protected boolean hasValue;

    public RegistryMessage(Entry<?> entry, RegistryAction action) {
        this(action, (byte) entry.getRegistry().getType().ordinal(), true,
                (short) entry.getIndex(), true,
                entry.getValue().serializedAsInt(), action != RegistryAction.GET);
    }

    protected RegistryMessage(RegistryAction action, byte registry, boolean hasRegistry,
                              short entry, boolean hasEntry, int value, boolean hasValue) {
        super();
        this.action = action;
        this.registry = registry;
        this.hasRegistry = hasRegistry;
        this.entry = entry;
        this.hasEntry = hasEntry;
        this.value = value;
        this.hasValue = hasValue;
    }

    public RegistryMessage(byte[] bytes) throws ParseException {
        super(bytes);
    }

    /**
     * Tries to parse raw bytes into this instance.
     *
     * @param bytes Byte representation.
     * @throws ParseException If unable to parse {@code bytes}.
     * @see RegistryMessage#fromBytes(ByteArrayList)
     */
    @Override
    public void fromBytes(ByteArrayList bytes) throws ParseException {
        super.fromBytes(bytes);

        int position = 1;

        byte head = 0;
        try {
            head = bytes.get(position++);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid Message", bytes.getArray());
        }

        // action
        RegistryAction[] actions = RegistryAction.values();
        int actionNum = (head & 0xF0) >>> 4;
        if (actionNum >= actions.length)
            throw new ParseException("Invalid action", bytes);
        RegistryAction action = actions[actionNum];

        // flags
        boolean hasRegistry = bytes.getBit(1, 3);
        boolean hasEntry = bytes.getBit(1, 2);
        boolean hasValue = bytes.getBit(1, 1);

        // registry
        byte registry = 0;
        if (hasRegistry) {
            try {
                registry = bytes.get(position++);
            } catch (IndexOutOfBoundsException e) {
                throw new ParseException("Message too short", bytes);
            }
        }

        // entry
        short entry = 0;
        if (hasEntry) {
            try {
                entry = bytes.getShort(position);
                position += 2;
            } catch (IndexOutOfBoundsException e) {
                throw new ParseException("Message too short", bytes);
            }
        }

        // value
        int value = 0;
        if (hasValue) {
            try {
                value = bytes.getInt(position);
                position += 4;
            } catch (IndexOutOfBoundsException e) {
                throw new ParseException("Message too short", bytes);
            }
        }

        // alter values
        this.action = action;
        this.hasRegistry = hasRegistry;
        this.registry = registry;
        this.hasEntry = hasEntry;
        this.entry = entry;
        this.hasValue = hasValue;
        this.value = value;
    }

    @Override
    public ByteArrayList toBytes() {
        ByteArrayList array = super.toBytes();

        // Action
        array.addByte((action.ordinal() & 0xF) << 4);

        if (hasRegistry) {
            array.setBit(1, 3);
            array.add(registry);
        }
        if (hasEntry) {
            array.setBit(1, 2);
            array.addShort(entry);
        }
        if (hasValue) {
            array.setBit(1, 1);
            array.addInt(value);
        }

        return array;
    }


    public RegistryAction getAction() {
        return action;
    }

    public byte getRawRegistry() {
        return registry;
    }

    public short getRawEntry() {
        return entry;
    }

    public int getRawValue() {
        return value;
    }

}
