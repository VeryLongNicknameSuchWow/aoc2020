package pl.rynbou.aoc2020.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day9 {

    public static void main(String[] args) throws IOException {
        final long[] input = Files.readAllLines(Paths.get("src/main/resources/day9.txt")).stream()
                .mapToLong(Long::parseLong)
                .toArray();

        long weakNum = 0;
        final int preambleLen = 25;
        for (int i = preambleLen; i < input.length; i++) {
            if (!possibleNumbers(input, i - preambleLen, preambleLen).contains(input[i])) {
                weakNum = input[i];
                System.out.println(weakNum);
                break;
            }
        }

        for (int sumLen = 2; sumLen < input.length; sumLen++) {
            for (int sumIndex = 0; sumIndex < input.length; sumIndex++) {
                final long[] nums = Arrays.copyOfRange(input, sumIndex, sumIndex + sumLen);
                if (Arrays.stream(nums).sum() == weakNum) {
                    System.out.println(Arrays.stream(nums).min().orElse(0) + Arrays.stream(nums).max().orElse(0));
                    break;
                }
            }
        }
    }

    public static Set<Long> possibleNumbers(long[] data, int from, int count) {
        final Set<Long> res = new HashSet<>();

        for (int i = from; i < from + count; i++) {
            for (int j = from; j < from + count; j++) {
                if (data[i] == data[j]) {
                    continue;
                }

                res.add(data[i] + data[j]);
            }
        }

        return res;
    }
}
