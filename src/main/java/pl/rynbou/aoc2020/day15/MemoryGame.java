package pl.rynbou.aoc2020.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class MemoryGame {

    private final List<Integer> numberHistory = new ArrayList<>();

    public MemoryGame(final List<Integer> startingNums) {
        numberHistory.addAll(startingNums);
    }

    public void start(int finalTurn) {
        for (int turn = numberHistory.size() + 1; turn <= finalTurn; turn++) {
            final int lastNum = getLastNumber();
            final int count = countOccurrences(lastNum);
            if (count == 1) {
                numberHistory.add(0);
            } else {
                numberHistory.add(getAge(lastNum));
            }
        }
    }

    public int getAge(final int number) {
        int[] indexes = IntStream.range(0, numberHistory.size())
                .filter(i -> number == numberHistory.get(i))
                .toArray();
        int maxI = indexes.length - 1;
        return Math.abs(indexes[maxI] - indexes[maxI - 1]);
    }

    public int getLastNumber() {
        return numberHistory.get(numberHistory.size() - 1);
    }

    public int countOccurrences(final int number) {
        return Collections.frequency(numberHistory, number);
    }
}
