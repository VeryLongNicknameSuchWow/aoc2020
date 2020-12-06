package pl.rynbou.aoc2020.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day3 {

    private static char[][] input;

    public static void main(String[] args) throws IOException {
        input = Files.readAllLines(Paths.get("src/main/resources/day3.txt")).stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        int product = 1;
        for (int down = 1; down <= 2; down++) {
            for (int right = 1; right <= 7; right += 2) {
                int count = countTrees(down, right);
                product *= count;

                if (right == 3) {
                    System.out.println(count);
                }

                if (down == 2) {
                    System.out.println(product);
                    return;
                }
            }
        }
    }

    public static int countTrees(int down, int right) {
        int treeCount = 0;

        for (int i = 0; i * down < input.length; i++) {
            if (input[i * down][(i * right) % (input[0].length)] == '#') {
                treeCount++;
            }
        }

        return treeCount;
    }
}
