package pl.rynbou.aoc2020.day16;

import java.util.Arrays;

public class Ticket {

    private final int[] fields;

    public Ticket(String ticket) {
        this.fields = Arrays.stream(ticket.strip().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public int[] getFields() {
        return fields;
    }
}
