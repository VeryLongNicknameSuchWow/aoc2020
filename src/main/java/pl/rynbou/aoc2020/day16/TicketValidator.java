package pl.rynbou.aoc2020.day16;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TicketValidator {

    private final Map<String, Range> bounds = new HashMap<>();
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
            this.bounds.put(key, range);
        }
    }

    public long getDeparturesProduct() {
        Set<Integer> possibleIndexes = IntStream.range(0, myTicket.getFields().length)
                .boxed()
                .collect(Collectors.toSet());

        Map<String, Range> boundsCopy = new HashMap<>(bounds);
        Map<String, Integer> labelIndexMap = new HashMap<>();

        while (boundsCopy.size() > 0) {
            for (int index : possibleIndexes) {
                if (labelIndexMap.containsValue(index)) {
                    continue;
                }

                rangeLoop:
                for (Map.Entry<String, Range> entry : boundsCopy.entrySet()) {
                    String label = entry.getKey();
                    Range range = entry.getValue();
                    if (nearbyTickets.stream().anyMatch(ticket -> !range.contains(ticket.getFields()[index]))) {
                        continue;
                    }

                    for (Range otherRange : boundsCopy.values()) {
                        if (otherRange.equals(range)) {
                            continue;
                        }
                        if (nearbyTickets.stream().allMatch(ticket -> otherRange.contains(ticket.getFields()[index]))) {
                            continue rangeLoop;
                        }
                    }

                    labelIndexMap.put(label, index);
                    boundsCopy.remove(label);
                    break;
                }
            }
        }

        long product = 1;
        for (Map.Entry<String, Integer> entry : labelIndexMap.entrySet()) {
            String label = entry.getKey();
            int index = entry.getValue();

            if (label.startsWith("departure")) {
                product *= myTicket.getFields()[index];
            }
        }

        return product;
    }

    public void discardInvalidTickets() {
        nearbyTickets.removeIf(ticket -> !isValid(ticket));
    }

    public int getErrorRate() {
        return this.nearbyTickets.stream()
                .flatMapToInt(ticket -> Arrays.stream(ticket.getFields()))
                .filter(value -> bounds.values().stream().noneMatch(range -> range.contains(value)))
                .sum();
    }

    public boolean isValid(Ticket ticket) {
        return Arrays.stream(ticket.getFields())
                .allMatch(value -> bounds.values().stream()
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
