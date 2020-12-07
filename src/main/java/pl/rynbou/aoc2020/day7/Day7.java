package pl.rynbou.aoc2020.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day7 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/day7.txt"));
        BagStacker bagStacker = new BagStacker(input, "shiny gold");
        System.out.println(bagStacker.countOuter());
        System.out.println(bagStacker.countInner());
    }
}
