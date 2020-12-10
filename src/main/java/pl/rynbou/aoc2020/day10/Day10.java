package pl.rynbou.aoc2020.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10 {

    public static void main(String[] args) throws IOException {
        final List<Long> input = Files.readAllLines(Paths.get("src/main/resources/day10.txt")).stream()
                .mapToLong(Long::parseLong)
                .sorted()
                .boxed()
                .collect(Collectors.toList());
        input.add(input.get(input.size() - 1) + 3); //the device adapter

        long currentJ = 0;
        int counter1J = 0;
        int counter3J = 0;
        for (final long adapter : input) {
            final long diff = adapter - currentJ;
            if (diff == 1) {
                counter1J++;
            }
            if (diff == 3) {
                counter3J++;
            }
            currentJ = adapter;
        }
        System.out.println(counter1J * counter3J);

        final Map<Long, Long> cache = new HashMap<>();
        cache.put(0L, 1L);
        for (final long adapter : input) {
            cache.put(adapter, cache.getOrDefault(adapter - 1, 0L)
                    + cache.getOrDefault(adapter - 2, 0L)
                    + cache.getOrDefault(adapter - 3, 0L));
        }
        System.out.println(cache.get(input.get(input.size() - 1)));
    }
}
