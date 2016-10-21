package SortInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class SortInput {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        sortArg(reader.lines(), args);
    }

    private static void sortIgnoreCase(Stream<String> stream){
        stream.sorted(String::compareToIgnoreCase)
                .forEach(System.out::println);
    }

    private static void sortLength(Stream<String> stream){
        stream.sorted((first, second) -> first.length() > second.length() ? 1 : -1)
                .forEach(System.out::println);
    }

    private static void sortArg(Stream<String> stream, String[] args){
        if(args.length == 0) {
            System.err.print("No argument");
            return;
        }
        stream.sorted((first, second) -> )
                .forEach(System.out::println);
    }
}
