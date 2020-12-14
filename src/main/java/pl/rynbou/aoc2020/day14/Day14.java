package pl.rynbou.aoc2020.day14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day14 {

    public static void main(String[] args) throws IOException {
        final List<String> input = Files.readAllLines(Paths.get("src/main/resources/day14.txt"));
        final Memory memory = new Memory();

        for (int part = 1; part <= 2; part++) {
            for (final String line : input) {
                if (line.startsWith("mask")) {
                    final String mask = line.substring(7);
                    if (part == 1) {
                        memory.setValueMask(mask);
                    } else {
                        memory.setAddressMask(mask);
                    }
                } else {
                    final long address = Long.parseLong(line.substring(4).split("]")[0]);
                    final long value = Long.parseLong(line.split(" = ")[1]);
                    memory.setValue(address, value);
                }
            }
            System.out.println(memory.getMemoryDump().values().stream().mapToLong(Long::longValue).sum());
            memory.reset();
        }
    }
}
