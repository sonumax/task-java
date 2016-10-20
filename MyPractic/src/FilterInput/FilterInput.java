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
            int temp = i;
            containsWord = Arrays.stream(containsWord).filter(s -> s.toLowerCase().contains(args[temp].toLowerCase()) || s.toLowerCase().matches(args[temp].toLowerCase()))
                    .toArray(String[]::new);
        }

        for (String string : containsWord){
            System.out.println(string);
        }
    }
}
