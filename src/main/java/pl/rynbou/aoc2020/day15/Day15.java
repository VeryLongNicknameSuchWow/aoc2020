package pl.rynbou.aoc2020.day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day15 {

    public static void main(String[] args) throws IOException {
        List<Integer> input = Files.readAllLines(Paths.get("src/main/resources/day15.txt")).stream()
                .flatMap(line -> Arrays.stream(line.split(",").clone()))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        final MemoryGame game = new MemoryGame(input);
        game.start(2020);
        System.out.println(game.getLastNumber());
    }
}
