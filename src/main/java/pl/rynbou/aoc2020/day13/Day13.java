package pl.rynbou.aoc2020.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day13 {

    public static void main(String[] args) throws IOException {
        final List<String> input = Files.readAllLines(Paths.get("src/main/resources/day13.txt"));

        final int arrivalTime = Integer.parseInt(input.get(0));
        final List<String> schedule = Arrays.stream(input.get(1).split(","))
                .collect(Collectors.toList());

        final Set<Integer> buses = schedule.stream()
                .filter(bus -> !"x".equals(bus))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
        System.out.println(part1(buses, arrivalTime));
        System.out.println(part2(buses, schedule));
    }

    public static int part1(final Set<Integer> buses, final int arrivalTime) {
        int minWait = Integer.MAX_VALUE;
        int minBus = -1;
        for (final int bus : buses) {
            final int waitTime = bus - (arrivalTime % bus);
            if (waitTime < minWait) {
                minWait = waitTime;
                minBus = bus;
            }
        }
        return minWait * minBus;
    }

    public static long part2(final Set<Integer> busSet, final List<String> schedule) {
        final Map<Integer, Integer> busOffsetMap = IntStream.range(0, schedule.size())
                .filter(i -> !"x".equals(schedule.get(i)))
                .boxed()
                .collect(Collectors.toMap(i -> Integer.parseInt(schedule.get(i)), i -> i));
        final List<Integer> sortedBuses = busSet.stream()
                .sorted(Comparator.comparingInt(Integer::intValue).reversed())
                .collect(Collectors.toList());

        long commonMultiple = 1;
        long startTime = 0;
        for (int i = 2; i < busOffsetMap.size(); i++) {
            int[] buses = sortedBuses.stream().limit(i).mapToInt(a -> a).toArray();
            commonMultiple = 1;
            for (int bus : buses) {
                commonMultiple *= bus;
            }
            startTime = LongStream.range(startTime, commonMultiple)
                    .filter(t -> Arrays.stream(buses).noneMatch(bus -> (t + busOffsetMap.get(bus)) % bus != 0))
                    .findFirst()
                    .orElse(0);
        }

        long time = startTime;
        do {
            time += commonMultiple;
        } while (!isValidTimestamp(time, busOffsetMap));

        return time;
    }

    public static boolean isValidTimestamp(final long time, final Map<Integer, Integer> busOffsetMap) {
        return busOffsetMap.entrySet().stream()
                .allMatch(entry -> isValidForBus(time + entry.getValue(), entry.getKey()));
    }

    public static boolean isValidForBus(final long time, final int bus) {
        return time % bus == 0;
    }
}
