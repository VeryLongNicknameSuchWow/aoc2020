package pl.rynbou.aoc2020.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day18 {

    public static void main(String[] args) throws IOException {
        final List<String> input = Files.readAllLines(Paths.get("src/main/resources/day18.txt"));
        final long part1 = input.stream()
                .mapToLong(Day18::eval)
                .sum();
        System.out.println(part1);
    }

    public static long eval(final String expr) {
        final String expression = expr.replace(" ", "");
        final char[] chars = expression.toCharArray();

        int openingIndex = 0;
        for (int index = 0; index < chars.length; index++) {
            final char c = chars[index];

            if (c == '(') {
                openingIndex = index;
            } else if (c == ')') {
                final String parenthesisExpression = new String(Arrays.copyOfRange(chars, openingIndex, index + 1));
                final String innerExpression = parenthesisExpression.replaceAll("[()]", "");
                final String newExpression = expression.replace(parenthesisExpression, "" + eval(innerExpression));
                return eval(newExpression);
            }
        }

        final char[] operators = expression
                .replaceAll("[^*+]", "")
                .toCharArray();
        final long[] numbers = Arrays.stream(expression.split("[*+]"))
                .mapToLong(Long::parseLong)
                .toArray();

        long result = numbers[0];
        for (int i = 0; i < operators.length; i++) {
            final char operator = operators[i];
            final long number = numbers[i + 1];

            if (operator == '+') {
                result += number;
            } else {
                result *= number;
            }
        }

        return result;
    }
}
