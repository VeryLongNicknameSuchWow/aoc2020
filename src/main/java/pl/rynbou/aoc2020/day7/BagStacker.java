package pl.rynbou.aoc2020.day7;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BagStacker {

    private List<String> input;
    private String innerBag;

    public BagStacker(List<String> input, String bag) {
        this.input = input;
        this.innerBag = bag;
    }

    public int countInner() {
        return countInner(this.innerBag);
    }

    private int countInner(String innerBag) {
        int counter = 0;
        for (String line : input) {
            if (!line.startsWith(innerBag)) {
                continue;
            }
            if (line.contains("no other bags")) {
                return 0;
            }

            String[] containedBags = line.split(" bags contain ")[1].split("bag");
            for (String capacityStr : containedBags) {
                capacityStr = capacityStr
                        .replace("s,", "")
                        .replace("s.", "")
                        .replace(",", "")
                        .replace(".", "")
                        .strip();
                if (capacityStr.isBlank()) {
                    continue;
                }

                String numStr = capacityStr.split(" ")[0];
                try {
                    int amount = Integer.parseInt(numStr);
                    counter += amount;
                    String bagStr = capacityStr.substring(numStr.length() + 1).strip();
                    counter += amount * countInner(bagStr);
                } catch (NumberFormatException e) {
                    counter++;
                    String bagStr = capacityStr.strip();
                    counter += countInner(bagStr);
                }
            }
        }

        return counter;
    }

    public int countOuter() {
        Set<String> cache = new HashSet<>();
        findOuter(cache, this.innerBag);
        return cache.size();
    }

    private void findOuter(Set<String> cache, String innerBag) {
        for (String line : input) {
            if (line.contains(innerBag) && !line.startsWith(innerBag)) {
                String outer = line.split(" bags contain ")[0];
                cache.add(outer);
                findOuter(cache, outer);
            }
        }
    }
}
