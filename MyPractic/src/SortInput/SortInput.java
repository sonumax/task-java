package SortInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SortInput {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().sorted(String::compareToIgnoreCase)
                .forEach(System.out::println);
    }
}
