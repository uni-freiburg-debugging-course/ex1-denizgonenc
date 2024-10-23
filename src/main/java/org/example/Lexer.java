package org.example;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Map.entry;

public class Lexer {
    static final Map<String, String> tokenSpecification = Map.ofEntries(
            entry("[0-9]", "number"),
            entry("[a-zA-Z]", "letter"),
            entry("\\+", "addition"),
            entry("\\*", "multiplication"),
            entry("\\-", "subtraction"),
            entry("\\(", "left-parentheses"),
            entry("\\)", "right-parentheses"),
            entry("\\s", "whitespace"));

    static ArrayList<String> lex(String input) throws Exception {
        char[] characters = input.toCharArray();
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<Character> buffer = new ArrayList<>();
        String bufferType = "";

        for (char character : characters) {
            boolean found = false;
            for (Map.Entry<String, String> entry : tokenSpecification.entrySet()) {
                Pattern regex = Pattern.compile(entry.getKey(), Pattern.UNICODE_CHARACTER_CLASS);
                Matcher matcher = regex.matcher(String.valueOf(character));

                if (matcher.find()) {
                    switch (entry.getValue()) {
                        case "number":
                            // check if buffer has letter characters already
                            if (!bufferType.equals("letter")) {
                                buffer.add(character);
                                bufferType = "number";
                            } else throw new Exception("Lexer syntax error", null);
                            break;
                        case "letter":
                            // check if buffer has number characters already
                            if (!bufferType.equals("number")) {
                                buffer.add(character);
                                bufferType = "letter";
                            } else throw new Exception("Lexer syntax error", null);
                            break;
                        case "whitespace":
                            // flush buffer or skip whitespace
                            if (!buffer.isEmpty()) {
                                String flush = buffer.stream()
                                        .map(String::valueOf)
                                        .collect(Collectors.joining());
                                if (bufferType.equals("letter") && !flush.equals("simplify"))
                                    throw new Exception(String.format("Lexer invalid keyword error: %s", flush), null);
                                tokens.add(flush);
                                buffer.clear();
                                bufferType = "";
                            }
                            found = true;
                            break;
                        case "right-parentheses":
                            if (!buffer.isEmpty()) {
                                String flush = buffer.stream()
                                        .map(String::valueOf)
                                        .collect(Collectors.joining());
                                if (bufferType.equals("number") || flush.equals("simplify")) {
                                    tokens.add(flush);
                                    buffer.clear();
                                    bufferType = "";
                                }
                                else throw new Exception("Lexer syntax error", null);
                            }
                            tokens.add(entry.getValue());
                            found = true;
                            break;
                        case "addition":
                        case "multiplication":
                        case "subtraction":
                        case "left-parentheses":
                            tokens.add(entry.getValue());
                            found = true;
                            break;
                    }
                }
            }
            if (!found && buffer.isEmpty()) {
                throw new Exception(String.format("Lexer invalid character error: %c", character), null);
            }
        }

        if (!buffer.isEmpty()) tokens.add(buffer.stream()
                .map(String::valueOf)
                .collect(Collectors.joining()));

        return tokens;
    }
}
