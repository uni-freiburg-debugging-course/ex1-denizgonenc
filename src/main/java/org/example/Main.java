package org.example;

import java.io.*;
import java.util.ArrayList;

import static org.example.Lexer.lex;
import static org.example.Parser.evaluate;
import static org.example.Parser.parse;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader;

        if (args.length > 0) {
            reader = new BufferedReader(
                    new FileReader(args[0]));
        }
        else {
            reader = new BufferedReader(
                    new InputStreamReader(System.in));
        }

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            ArrayList<String> tokens = lex(currentLine);
            AST ast = parse(tokens);
            if (ast.root.left != null && !ast.root.value.equals("simplify")) {
                System.out.println(currentLine);

            }
            else {
                long result = evaluate(ast.root);
                if (result >= 0) System.out.println(result);
                else System.out.printf("(- %d)%n", -result);
            }
        }

    }
}