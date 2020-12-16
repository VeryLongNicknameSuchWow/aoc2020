package pl.rynbou.aoc2020.day16;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicketValidator {

    private final Set<Range> bounds = new HashSet<>();
    private final Set<Range> departureBounds = new HashSet<>();
    private final Set<Ticket> nearbyTickets = new HashSet<>();
    private Ticket myTicket;

    public TicketValidator(List<String> data) {
        boolean myNext = false;
        boolean nearbyNext = false;

        for (String line : data) {
            if (line.isBlank()) {
                continue;
            }

            if (line.startsWith("your ticket")) {
                myNext = true;
                continue;
            } else if (line.startsWith("nearby tickets")) {
                myNext = false;
                nearbyNext = true;
                continue;
            }

            if (myNext) {
                this.myTicket = new Ticket(line);
                continue;
            } else if (nearbyNext) {
                this.nearbyTickets.add(new Ticket(line));
                continue;
            }

            String key = line.split(":")[0];
            String boundsStr = line.split(":")[1];

            Range range = new Range(boundsStr);
            if (key.contains("departure")) {
                departureBounds.add(range);
            }
            this.bounds.add(range);
        }
    }

    public long getDeparturesProduct() {
        Set<Integer> possibleIndexes = IntStream.range(0, myTicket.getFields().length)
                .boxed()
                .collect(Collectors.toSet());

        possibleIndexes.removeIf(index -> !nearbyTickets.stream()
                .allMatch(ticket -> departureBounds.stream()
                        .noneMatch(range -> range.contains(ticket.getFields()[index]))));

        long product = 1;
        for (int index : possibleIndexes) {
            product *= myTicket.getFields()[index];
        }
        return product;
    }

    public void discardInvalidTickets() {
        nearbyTickets.removeIf(ticket -> !isValid(ticket));
    }

    public int getErrorRate() {
        return this.nearbyTickets.stream()
                .flatMapToInt(ticket -> Arrays.stream(ticket.getFields()))
                .filter(value -> bounds.stream().noneMatch(range -> range.contains(value)))
                .sum();
    }

    public boolean isValid(Ticket ticket) {
        return Arrays.stream(ticket.getFields())
                .allMatch(value -> bounds.stream()
                        .anyMatch(range -> range.contains(value)));
    }

    public static class Range {
        private final int[][] bounds;

        public Range(String bounds) {
            String[] split = bounds.strip().split("or");
            this.bounds = new int[split.length][2];
            for (int i = 0; i < split.length; i++) {
                String[] ranges = split[i].split("-");
                for (int j = 0; j < ranges.length; j++) {
                    this.bounds[i][j] = Integer.parseInt(ranges[j].strip());
                }
            }
        }

        public boolean contains(int number) {
            for (int[] bound : bounds) {
                if (number >= bound[0] && number <= bound[1]) {
                    return true;
                }
            }
            return false;
        }
    }
}
