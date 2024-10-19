/* Main.java */
package compiler;

import java.io.*;
import scanner.Scanner;
import parser.Parser;
import java_cup.runtime.Symbol;

public class Main {
    public static void main(String[] args) {
        try {
            // Read input from command line argument or standard input
            Reader reader;
            if (args.length > 0) {
                reader = new FileReader(args[0]);
            } else {
                System.out.println("Enter expression:");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String input = br.readLine();
                reader = new StringReader(input);
            }

            // Create scanner and parser
            Scanner scanner = new Scanner(reader);
            Parser parser = new Parser(scanner);

            // Parse the input expression
            Double result = (Double) parser.parse().value;

            // Output the result
            System.out.println("Result: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}