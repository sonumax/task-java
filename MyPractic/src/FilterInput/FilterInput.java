package FilterInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class FilterInput {
    public static void main(String[] args) {
        if(args.length  == 0){
            System.err.println("No arguments");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] containsWord = reader.lines().filter(s -> s.toLowerCase().contains(args[0].toLowerCase()))
                .toArray(String[]::new);

        for (String string : containsWord){
            System.out.println(string);
        }
    }
}
