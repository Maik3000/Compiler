package compiler.parser;

import compiler.scanner.Scanner;
import compiler.parser.Parser;
import compiler.ast.ProgramNode;
import compiler.ast.ASTPrinter;
import java_cup.runtime.Symbol;
import compiler.ast.ASTDotGenerator;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;


public class ParserTest {
    public static void main(String[] args) {
        // Ruta al archivo de entrada
        String filename = "compiler/input.txt";  // Asegúrate de tener este archivo en el directorio actual o proporciona una ruta completa
        String output = "compiler/parser/outputParserTest.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(output))) {
            // Indicar el inicio de la etapa de parsing
            writer.println("stage: parsing");
            System.out.println("stage: parsing");
        
            // Inicializar Scanner y Parser
            Scanner scanner = new Scanner(new FileReader(filename));
            Parser parser = new Parser(scanner);
        
            // Realizar el parsing
            Symbol result = parser.parse();

            // Obtener el nodo raíz del AST
            ProgramNode program = (ProgramNode) result.value;

            // Confirmar que el parsing fue exitoso
            writer.println("Parsing completed successfully.");

            // Indicar el inicio de la impresión del AST
            writer.println("AST:");
            ASTPrinter printer = new ASTPrinter(writer);

            // Traversar el AST y generar la representación
            program.accept(printer);
            writer.println(); // Añadir una línea en blanco al final

            // Mensaje de éxito
            System.out.println("Parsing completado exitosamente. AST generado en " + output);
        } catch (IOException e) {
            System.err.println("Error reading the input file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error during parsing: " + e.getMessage());
            e.printStackTrace();  // Mostrar la traza del error
        }
        
    }
}



