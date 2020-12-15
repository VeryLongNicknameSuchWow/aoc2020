package pl.rynbou.aoc2020.day15;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryGame {

    private final Map<Integer, Integer> numberLastTurnMap = new HashMap<>();
    private final Map<Integer, Integer> numberAgeMap = new HashMap<>();
    private int lastNumber;
    private int turn = 1;

    public MemoryGame(final List<Integer> startingNums) {
        for (int num : startingNums) {
            sayNumber(num);
            turn++;
        }
    }

    public void start(final int finalTurn) {
        for (; turn <= finalTurn; turn++) {
            final boolean usedOnce = !numberAgeMap.containsKey(lastNumber);
            if (usedOnce) {
                sayNumber(0);
            } else {
                sayNumber(numberAgeMap.get(lastNumber));
            }
        }
    }

    public void sayNumber(final int number) {
        final boolean firstUse = !numberLastTurnMap.containsKey(number);
        if (!firstUse) {
            final int previousLastTurn = numberLastTurnMap.get(number);
            numberAgeMap.put(number, turn - previousLastTurn);
        }
        numberLastTurnMap.put(number, turn);
        lastNumber = number;
    }

    public int getLastNumber() {
        return lastNumber;
    }
}
