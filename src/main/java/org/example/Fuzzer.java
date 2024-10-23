package org.example;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Fuzzer {
    static final ArrayList<String> tokens = new ArrayList<>(Arrays.asList("+", "-", "*"));

    public static void main(String[] args)  {
        try {
            FileWriter myWriter = new FileWriter("fuzz.txt", false);
            Random rand = new Random();
            for (int i=0; i < 100000; i++) {
                int index = rand.nextInt(tokens.size());
                myWriter.write(
                    "(simplify (" +
                    tokens.get(index) +
                    " " +
                    rand.nextInt(0, Integer.MAX_VALUE) +
                    " " +
                    rand.nextInt(0, Integer.MAX_VALUE) +
                    "))\n"
                );
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}
