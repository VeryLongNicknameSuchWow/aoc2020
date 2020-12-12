package pl.rynbou.aoc2020.day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day12 {

    public static void main(String[] args) throws IOException {
        final String[] input = Files.readAllLines(Paths.get("src/main/resources/day12.txt")).toArray(String[]::new);

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    public static int part1(final String[] instructions) {
        //[0] is NS axis, S+, N-
        //[1] is EW axis, E+, W-
        int[] loc = new int[2];
        //E=0, S=90, W=180, N=270 (degrees)
        int rotation = 0;

        for (final String line : instructions) {
            final String direction = line.substring(0, 1);
            final int value = Integer.parseInt(line.substring(1));

            switch (direction) {
                case "N":
                    loc[0] -= value;
                    break;
                case "S":
                    loc[0] += value;
                    break;
                case "E":
                    loc[1] += value;
                    break;
                case "W":
                    loc[1] -= value;
                    break;
                case "L":
                    rotation -= value;
                    break;
                case "R":
                    rotation += value;
                    break;
                case "F":
                    final double rad = Math.toRadians(rotation);
                    loc[0] += Math.round(Math.sin(rad)) * value;
                    loc[1] += Math.round(Math.cos(rad)) * value;
                    break;
            }
        }

        return Math.abs(loc[0]) + Math.abs(loc[1]);
    }

    public static int part2(final String[] instructions) {
        int[] shipLoc = new int[2];
        int[] waypointLoc = {-1, 10};

        for (final String line : instructions) {
            final String direction = line.substring(0, 1);
            final int value = Integer.parseInt(line.substring(1));
            //current waypoint angle in relation to the EW axis (e.g. [1E 1N] is -pi/4)
            final double radiusVectorAngle = Math.atan2(waypointLoc[0], waypointLoc[1]);
            final double radiusVectorLength = Math.sqrt(Math.pow(waypointLoc[0], 2) + Math.pow(waypointLoc[1], 2));

            switch (direction) {
                case "N":
                    waypointLoc[0] -= value;
                    break;
                case "S":
                    waypointLoc[0] += value;
                    break;
                case "E":
                    waypointLoc[1] += value;
                    break;
                case "W":
                    waypointLoc[1] -= value;
                    break;
                case "L":
                    final double anticlockwiseAngle = radiusVectorAngle - Math.toRadians(value);
                    waypointLoc[0] = (int) Math.round(radiusVectorLength * Math.sin(anticlockwiseAngle));
                    waypointLoc[1] = (int) Math.round(radiusVectorLength * Math.cos(anticlockwiseAngle));
                    break;
                case "R":
                    final double clockwiseAngle = radiusVectorAngle + Math.toRadians(value);
                    waypointLoc[0] = (int) Math.round(radiusVectorLength * Math.sin(clockwiseAngle));
                    waypointLoc[1] = (int) Math.round(radiusVectorLength * Math.cos(clockwiseAngle));
                    break;
                case "F":
                    shipLoc[0] += waypointLoc[0] * value;
                    shipLoc[1] += waypointLoc[1] * value;
                    break;
            }
        }

        return Math.abs(shipLoc[0]) + Math.abs(shipLoc[1]);
    }
}
