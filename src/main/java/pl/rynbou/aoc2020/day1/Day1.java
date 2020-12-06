package pl.rynbou.aoc2020.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1 {

    private final static List<Integer> inputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Files.readAllLines(Paths.get("src/main/resources/day1.txt")).stream()
                .mapToInt(Integer::parseInt)
                .forEach(inputs::add);
        System.out.println("Part 1: " + part1());
        System.out.println("Part 2: " + part2());
    }

    public static int part1() {
        for (int input1 : inputs) {
            for (int input2 : inputs) {
                if (input1 + input2 == 2020) {
                    return input1 * input2;
                }
            }
        }
        return 0;
    }

    public static int part2() {
        for (int input1 : inputs) {
            for (int input2 : inputs) {
                for (int input3 : inputs) {
                    if (input1 + input2 + input3 == 2020) {
                        return input1 * input2 * input3;
                    }
                }
            }
        }
        return 0;
    }
}
