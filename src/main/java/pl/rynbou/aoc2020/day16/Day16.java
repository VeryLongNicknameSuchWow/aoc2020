package pl.rynbou.aoc2020.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day16 {

    public static void main(String[] args) throws IOException {
        final List<String> input = Files.readAllLines(Paths.get("src/main/resources/day16.txt"));
        final TicketValidator validator = new TicketValidator(input);
        System.out.println(validator.getErrorRate());
        validator.discardInvalidTickets();
        System.out.println(validator.getDeparturesProduct());
    }
}
