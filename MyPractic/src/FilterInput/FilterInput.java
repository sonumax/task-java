package FilterInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Stream;

public class FilterInput {
    public static void main(String[] args) {
        if(args.length  == 0){
            System.err.println("No arguments");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] containsWord = someWordsAndReg(reader.lines(), args);

        for (String string : containsWord){
            System.out.println(string);
        }
    }

    private static String[] ignoreCase(Stream<String> stream, String[] strings){
        return stream.filter(s -> s.toLowerCase().contains(strings[0].toLowerCase()))
                .toArray(String[]::new);
    }

    private static String[] someWords(Stream<String> stream, String[] strings){
        String[] containsWord = stream.toArray(String[]::new);
        for (int i = 0; i < strings.length; i++){
            String word = strings[i].toLowerCase();
            containsWord = Arrays.stream(containsWord).filter(s -> s.toLowerCase().contains(word))
                    .toArray(String[]::new);
        }
        return containsWord;
    }

    private static String[] someWordsAndReg(Stream<String> stream, String[] strings){
        String[] containsWord = stream.toArray(String[]::new);
        for (int i = 0; i < strings.length; i++){
            String word = strings[i].toLowerCase();
            containsWord = Arrays.stream(containsWord).filter(s -> s.toLowerCase().contains(word) || s.toLowerCase().matches(word))
                    .toArray(String[]::new);
        }
        return containsWord;
    }
}
