package pl.rynbou.aoc2020.day11;

import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Day11 {

    public static char[][] input;

    public static void main(String[] args) throws IOException {
        input = Files.readAllLines(Paths.get("src/main/resources/day11.txt")).stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        simulate(false);
        System.out.println(countOccupied());

        input = Files.readAllLines(Paths.get("src/main/resources/day11.txt")).stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        simulate(true);
        System.out.println(countOccupied());
    }

    public static void simulate(final boolean updatedRules) {
        int changed;
        char[][] cache;
        do {
            changed = 0;
            cache = new char[input.length][input[0].length];

            for (int row = 0; row < input.length; row++) {
                for (int column = 0; column < input[0].length; column++) {
                    final int count = updatedRules
                            ? countVisibleOccupiedSeats(column, row)
                            : countAdjacentOccupiedSeats(column, row);

                    if (input[row][column] == 'L' && count == 0) {
                        changed++;
                        cache[row][column] = '#';
                    }
                    if (input[row][column] == '#' && ((!updatedRules && count >= 4) || (updatedRules && count >= 5))) {
                        changed++;
                        cache[row][column] = 'L';
                    }
                }
            }

            for (int row = 0; row < cache.length; row++) {
                for (int column = 0; column < cache[0].length; column++) {
                    if (cache[row][column] != '\u0000') {
                        input[row][column] = cache[row][column];
                    }
                }
            }
        } while (changed != 0);
    }

    public static long countOccupied() {
        return Arrays.stream(input)
                .flatMapToInt(chars -> CharBuffer.wrap(chars).chars())
                .filter(ch -> (char) ch == '#')
                .count();
    }

    public static int countAdjacentOccupiedSeats(final int seatCol, final int seatRow) {
        int counter = 0;
        for (int adjRow = seatRow - 1; adjRow <= seatRow + 1; adjRow++) {
            for (int adjCol = seatCol - 1; adjCol <= seatCol + 1; adjCol++) {
                if (adjCol == seatCol && adjRow == seatRow) {
                    continue;
                }
                if (isOutOfBounds(adjRow, adjCol)) {
                    continue;
                }
                if (input[adjRow][adjCol] == '#') {
                    counter++;
                }
            }
        }
        return counter;
    }

    public static int countVisibleOccupiedSeats(final int seatCol, final int seatRow) {
        int counter = 0;
        for (int rowD = -1; rowD <= 1; rowD++) {
            for (int colD = -1; colD <= 1; colD++) {
                if (rowD == 0 && colD == 0) {
                    continue;
                }
                for (int i = 1; true; i++) {
                    final int row = seatRow + i * rowD;
                    final int col = seatCol + i * colD;
                    if (isOutOfBounds(row, col)) {
                        break;
                    }
                    if (input[row][col] == 'L') {
                        break;
                    } else if (input[row][col] == '#') {
                        counter++;
                        break;
                    }
                }
            }
        }
        return counter;
    }

    public static boolean isOutOfBounds(int row, int column) {
        return row < 0 || column < 0 || row >= input.length || column >= input[0].length;
    }
}
