package pl.rynbou.aoc2020.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/day6.txt"));

        List<String> groupAnswers = new ArrayList<>();
        StringBuilder answerBuilder = new StringBuilder();
        for (String line : input) {
            if (line.isEmpty()) {
                groupAnswers.add(answerBuilder.toString());
                answerBuilder = new StringBuilder();
            }
            answerBuilder.append(line);
        }
        groupAnswers.add(answerBuilder.toString());

        System.out.println(groupAnswers.stream()
                .mapToLong(groupAnswer -> groupAnswer.chars().distinct().count())
                .sum());

        int counter = 0;
        Set<Integer> intersectingChars = new HashSet<>();
        boolean newGroup = true;
        for (String individualAnswer : input) {
            if (newGroup) {
                individualAnswer.chars().forEach(intersectingChars::add);
                newGroup = false;
            }
            if (individualAnswer.isEmpty()) {
                counter += intersectingChars.size();
                intersectingChars.clear();
                newGroup = true;
            }
            intersectingChars.removeIf(c1 -> individualAnswer.chars().noneMatch(c2 -> c1 == c2));
        }
        counter += intersectingChars.size();

        System.out.println(counter);
    }
}
