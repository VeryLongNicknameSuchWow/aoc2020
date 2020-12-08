package pl.rynbou.aoc2020.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Day8 {

    public static void main(String[] args) throws IOException {
        String[] input = Files.readAllLines(Paths.get("src/main/resources/day8.txt")).toArray(String[]::new);
        System.out.println(run(input).getReturnValue());

        for (int i = 0; i < input.length; i++) {
            String instruction = input[i];
            if (instruction.contains("acc")) {
                continue;
            }

            String[] newInput = input.clone();
            if (instruction.contains("jmp")) {
                newInput[i] = instruction.replace("jmp", "nop");
            } else if (instruction.contains("nop")) {
                newInput[i] = instruction.replace("nop", "jmp");
            }

            ReturnCode run = run(newInput);
            if (run.getReturnType() == 0) {
                System.out.println(run.getReturnValue());
                return;
            }
        }
    }

    public static ReturnCode run(String[] instructions) {
        Set<Integer> runInstructions = new HashSet<>();
        int acc = 0;

        for (int i = 0; i < instructions.length; i++) {
            if (runInstructions.contains(i)) {
                return new ReturnCode(-1, acc);
            }
            runInstructions.add(i);

            String[] instruction = instructions[i].split(" ");
            String operation = instruction[0];
            int parameter = Integer.parseInt(instruction[1]);
            if (operation.equals("acc")) {
                acc += parameter;
            } else if (operation.equals("jmp")) {
                i += parameter - 1;
            }
        }
        return new ReturnCode(0, acc);
    }

    public static class ReturnCode {
        //0 if reached the end
        //-1 if infinite loop
        private final int returnType;
        private final int returnValue;

        public ReturnCode(int returnType, int returnValue) {
            this.returnType = returnType;
            this.returnValue = returnValue;
        }

        public int getReturnType() {
            return returnType;
        }

        public int getReturnValue() {
            return returnValue;
        }
    }
}
