package pl.rynbou.aoc2020.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day5 {

    public static void main(String[] args) throws IOException {
        int[] seenIDs = Files.readAllLines(Paths.get("src/main/resources/day5.txt")).stream()
                .map(line -> line
                        .replaceAll("[FL]", "0")
                        .replaceAll("[BR]", "1"))
                .mapToInt(line -> Integer.parseInt(line, 2))
                .sorted()
                .toArray();

        System.out.println(seenIDs[seenIDs.length - 1]);
        System.out.println((seenIDs[0] + seenIDs[seenIDs.length - 1]) * (seenIDs.length + 1) / 2 - Arrays.stream(seenIDs).sum());
    }
}
