package FilterInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class FilterInput {
    public static void main(String[] args) {
        if(args.length  == 0){
            System.err.println("No arguments");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] containsWord = reader.lines().toArray(String[]::new);
        for (int i = 0; i < args.length; i++){
            String word = args[i].toLowerCase();
            containsWord = Arrays.stream(containsWord).filter(s -> s.toLowerCase().contains(word) || s.toLowerCase().matches(word))
                    .toArray(String[]::new);
        }

        for (String string : containsWord){
            System.out.println(string);
        }
    }
}
