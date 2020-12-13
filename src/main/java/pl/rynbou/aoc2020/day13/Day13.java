package pl.rynbou.aoc2020.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static long part2(final Set<Integer> buses, final List<String> schedule) {
        final Map<Integer, Integer> busOffsetMap = IntStream.range(0, schedule.size())
                .filter(i -> !"x".equals(schedule.get(i)))
                .boxed()
                .collect(Collectors.toMap(i -> Integer.parseInt(schedule.get(i)), i -> i));

        long previousCommonMultiple = 1;
        long startTime = 0;
        for (int i = 2; i < busOffsetMap.size(); i++) {
            final int[] subBuses = buses.stream().limit(i).mapToInt(a -> a).toArray();
            long commonMultiple = 1;
            for (final int bus : subBuses) {
                commonMultiple *= bus;
            }
            timeLoop:
            for (long n = 1; true; n++) {
                final long time = startTime + n * previousCommonMultiple;
                for (final int bus : subBuses) {
                    if (isInvalidForBus(time + busOffsetMap.get(bus), bus)) {
                        continue timeLoop;
                    }
                }
                startTime = time;
                break;
            }
            previousCommonMultiple = commonMultiple;
        }

        timeLoop:
        for (int n = 1; true; n++) {
            final long time = startTime + n * previousCommonMultiple;
            for (final int bus : buses) {
                if (isInvalidForBus(time + busOffsetMap.get(bus), bus)) {
                    continue timeLoop;
                }
            }
            return time;
        }
    }

    public static boolean isInvalidForBus(final long time, final int bus) {
        return time % bus != 0;
    }
}
