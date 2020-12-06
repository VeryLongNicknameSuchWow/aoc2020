package pl.rynbou.aoc2020.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {

    private static final Set<String> passportData = new HashSet<>();
    private static final Set<String> expectedFields = new HashSet<>() {{
        add("byr");
        add("iyr");
        add("eyr");
        add("hgt");
        add("hcl");
        add("ecl");
        add("pid");
    }};
    private static final Pattern yearPattern = Pattern.compile("[0-9]{4}");
    private static final Pattern heightMetricPattern = Pattern.compile("^[0-9]{3}cm$");
    private static final Pattern heightImperialPattern = Pattern.compile("^[0-9]{2}in$");
    private static final Pattern hairColorPattern = Pattern.compile("^#[a-f0-9]{6}$");
    private static final Pattern passportIDPattern = Pattern.compile("[0-9]{9}");


    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get("src/main/resources/day4.txt"));

        StringBuilder temp = new StringBuilder();
        for (String line : input) {
            if (line.isEmpty()) {
                passportData.add(temp.toString());
                temp = new StringBuilder();
            }
            if (temp.length() > 0) {
                temp.append(" ");
            }
            temp.append(line);
        }
        passportData.add(temp.toString());

        System.out.println(passportData.stream()
                .filter(passportString -> hasValidFields(mapPassport(passportString)))
                .count());

        System.out.println(passportData.stream()
                .map(Day4::mapPassport)
                .filter(passportMap -> hasValidFields(passportMap)
                        && passportMap.entrySet().stream().allMatch(pair -> isValidField(pair.getKey(), pair.getValue())))
                .count());
    }

    private static Map<String, String> mapPassport(String passport) {
        return Arrays.stream(passport.split(" "))
                .collect(Collectors.toMap(s -> s.split(":")[0], s -> s.split(":")[1]));
    }

    private static boolean hasValidFields(Map<String, String> passportMap) {
        return expectedFields.stream()
                .allMatch(expected -> passportMap.keySet().stream().anyMatch(expected::equals));
    }

    private static boolean isValidField(String key, String value) {
        if (key.equals("byr") && yearPattern.matcher(value).matches()) {
            int asInt = Integer.parseInt(value);
            return asInt >= 1920 && asInt <= 2002;
        } else if (key.equals("iyr") && yearPattern.matcher(value).matches()) {
            int asInt = Integer.parseInt(value);
            return asInt >= 2010 && asInt <= 2020;
        } else if (key.equals("eyr") && yearPattern.matcher(value).matches()) {
            int asInt = Integer.parseInt(value);
            return asInt >= 2020 && asInt <= 2030;
        } else if (key.equals("hgt") && heightMetricPattern.matcher(value).matches()) {
            int height = Integer.parseInt(value.replace("cm", ""));
            return height >= 150 && height <= 193;
        } else if (key.equals("hgt") && heightImperialPattern.matcher(value).matches()) {
            int height = Integer.parseInt(value.replace("in", ""));
            return height >= 59 && height <= 76;
        } else if (key.equals("hcl") && hairColorPattern.matcher(value).matches()) {
            return true;
        } else if (key.equals("ecl")) {
            return value.equals("amb") || value.equals("blu") || value.equals("brn") || value.equals("gry")
                    || value.equals("grn") || value.equals("hzl") || value.equals("oth");
        } else if (key.equals("pid") && passportIDPattern.matcher(value).matches()) {
            return true;
        } else return key.equals("cid");
    }
}
