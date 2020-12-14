package pl.rynbou.aoc2020.day14;

import java.util.*;

public class Memory {

    private final Map<Long, Long> memory = new HashMap<>();
    private final Map<Byte, Boolean> valueMask = new HashMap<>();
    private final Map<Byte, Boolean> addressMask = new HashMap<>();

    public void reset() {
        this.memory.clear();
        this.valueMask.clear();
        this.addressMask.clear();
    }

    public void setValueMask(final String mask) {
        this.valueMask.clear();
        final char[] chars = mask.toCharArray();
        final int maxI = mask.length() - 1;
        for (byte i = (byte) maxI; i >= 0; i--) {
            final char c = chars[i];
            if (c == 'X') {
                continue;
            }
            this.valueMask.put((byte) (maxI - i), c == '1');
        }
    }

    public void setAddressMask(final String mask) {
        this.addressMask.clear();
        final char[] chars = mask.toCharArray();
        final int maxI = mask.length() - 1;
        for (byte i = (byte) maxI; i >= 0; i--) {
            final char c = chars[i];
            final byte position = (byte) (maxI - i);
            if (c == 'X') {
                this.addressMask.put(position, false);
            } else if (c == '1') {
                this.addressMask.put(position, true);
            }
        }
    }

    public void setValue(long address, long value) {
        for (Map.Entry<Byte, Boolean> entry : this.valueMask.entrySet()) {
            final byte position = entry.getKey();
            final boolean enabled = entry.getValue();
            if (enabled) {
                value |= 1L << position;
            } else {
                value &= ~(1L << position);
            }
        }
        for (Map.Entry<Byte, Boolean> entry : this.addressMask.entrySet()) {
            final byte position = entry.getKey();
            final boolean enabled = entry.getValue();
            if (enabled) {
                address |= 1L << position;
            }
        }

        Set<Long> addresses = new HashSet<>();
        Set<Long> newAddresses = new HashSet<>();
        addresses.add(address);
        do {
            newAddresses.clear();
            for (final long possibleAddress : addresses) {
                for (Map.Entry<Byte, Boolean> entry : this.addressMask.entrySet()) {
                    final byte position = entry.getKey();
                    final boolean enabled = entry.getValue();
                    if (!enabled) {
                        long a1 = possibleAddress | 1L << position;
                        long a2 = possibleAddress & ~(1L << position);
                        if (!addresses.contains(a1)) {
                            newAddresses.add(a1);
                        }
                        if (!addresses.contains(a2)) {
                            newAddresses.add(a2);
                        }
                    }
                }
            }
            addresses.addAll(newAddresses);
        } while (newAddresses.size() != 0);

        for (long possibleAddress : addresses) {
            this.memory.put(possibleAddress, value);
        }
    }

    public Map<Long, Long> getMemoryDump() {
        return Collections.unmodifiableMap(this.memory);
    }
}
