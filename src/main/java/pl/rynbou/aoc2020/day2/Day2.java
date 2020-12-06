package pl.rynbou.aoc2020.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day2 {

    public static void main(String[] args) throws IOException {
        int p1 = 0;
        int p2 = 0;

        for (String line : Files.readAllLines(Paths.get("src/main/resources/day2.txt"))) {
            String[] split = line.split(" ");
            int[] nums = Arrays.stream(split[0].split("-")).mapToInt(Integer::parseInt).toArray();
            char c = split[1].charAt(0);

            long count = split[2].chars().filter(passwordChar -> passwordChar == c).count();
            if (nums[0] <= count && count <= nums[1]) {
                p1++;
            }

            if ((split[2].charAt(nums[0] - 1) == c && split[2].charAt(nums[1] - 1) != c) ||
                    (split[2].charAt(nums[0] - 1) != c && split[2].charAt(nums[1] - 1) == c)) {
                p2++;
            }
        }

        System.out.println(p1);
        System.out.println(p2);
    }
}
