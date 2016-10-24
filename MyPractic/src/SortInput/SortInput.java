package SortInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class SortInput {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] sortingArray = sortArg(reader.lines(), args);

        for (String line : sortingArray){
            System.out.println(line);
        }
    }

    private static String[] sortIgnoreCase(Stream<String> stream){
         return stream.sorted(String::compareToIgnoreCase)
                .toArray(String[]::new);
    }

    private static String[] sortLength(Stream<String> stream){
        return stream.sorted((first, second) -> first.length() > second.length() ? 1 : -1)
                .toArray(String[]::new);
    }

    private static String[] sortArg(Stream<String> stream, String[] strings){
        if(strings.length == 0){
            System.err.println("No argument");
            return new String[0];
        }

        Integer numberWord = new Integer(strings[0]) - 1;
        return stream.sorted((first, second) -> first.split("[\\W]+")[numberWord].compareToIgnoreCase(second.split("[\\W]+")[numberWord]))
                .toArray(String[]::new);
    }
}
