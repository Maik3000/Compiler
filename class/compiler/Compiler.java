package compiler;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.lang.reflect.Field;
import compiler.scanner.Scanner;
import compiler.parser.sym;
import compiler.parser.Parser;
import compiler.ast.ProgramNode;
import compiler.ast.ASTPrinter;
import compiler.ast.ASTDotGenerator;
import compiler.semantic.SemanticAnalyzer;
import java_cup.runtime.Symbol;
import compiler.irt.IRTTranslator;
import compiler.irt.IRTStatement;
import compiler.irt.IRTPrinter;



public class Compiler {
    public static void main(String[] args) {
        // Verificar si hay al menos un argumento
        if (args.length < 1) {
            printHelp(); // Imprimir ayuda si no hay argumentos
            System.exit(1); // Salir con código de error
        }

        // Definir valores predeterminados para los parámetros
        String filename = "input.txt";
        String output = "output.txt";
        String target = "codegen"; // Por defecto
        boolean debug = false;

        // Procesar los argumentos proporcionados por el usuario
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        output = args[++i];
                    } else {
                        System.err.println("Error: Se espera un nombre de archivo después de -o.");
                        printHelp();
                        System.exit(1);
                    }
                    break;
                case "-target":
                    if (i + 1 < args.length) {
                        target = args[++i];
                    } else {
                        System.err.println("Error: Se espera un objetivo después de -target.");
                        printHelp();
                        System.exit(1);
                    }
                    break;
                case "-debug":
                    // Activar el modo de depuración si se proporciona el flag "-debug"
                    debug = true;
                    break;
                case "-h":
                    // Mostrar la ayuda y salir si se proporciona el flag "-h"
                    printHelp();
                    System.exit(0);
                    break;
                default:
                    filename = args[i]; // Asumir que el argumento es el nombre del archivo de entrada
            }
        }

        // Verificar si el nombre del archivo de entrada está vacío
        if (filename.isEmpty()) {
            System.err.println("Error: No se especificó un archivo de entrada.");
            printHelp();
            System.exit(1); // Error si no hay archivo
        }

        try {
            // Seleccionar la operación a realizar según el objetivo proporcionado
            switch (target) {
                case "scan":
                    runScan(filename, output, debug);
                    break;
                case "parse":
                    runParse(filename, output, debug);
                    break;
                case "dot":
                    runDot(filename, output, debug);
                    break;
                case "semantic":
                    runSemantic(filename, output, debug);
                    break;
                case "IRT":
                    runIRT(filename, output, debug);
                    break;
                default:
                    System.err.println("Objetivo desconocido: " + target);
                    printHelp();    // Imprimir ayuda para objetivos válidos
            }
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
            if (debug) {
                e.printStackTrace();
            }
        }
    }
    
    /**
    /* Método para ejecutar el análisis léxico (scanning).
     *
     * @param filename Archivo de entrada.
     * @param output   Archivo de salida.
     * @param debug    Bandera para activar el modo debug.
     * @throws IOException Si ocurre un error de E/S.
     */
    private static void runScan(String filename, String output, boolean debug) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(output))) {
            // Indicar el inicio de la etapa de scanning
            writer.println("stage: scanning");
            System.out.println("stage: scanning");

            // Abrir el archivo de entrada para leer
            try (FileReader fileReader = new FileReader(filename)) {
                Scanner scanner = new Scanner(fileReader);
                Symbol token;

                // Iterar sobre los tokens generados por el scanner
                while ((token = scanner.next_token()) != null) {
                    // Verificar que el token no sea EOF
                    if (token.sym != sym.EOF) {
                        // Imprimir el token y su valor
                        writer.printf("Token: %s, Value: %s%n", getTokenName(token.sym), token.value);
                    } else {
                        break; // Salir si se llega al final del archivo
                    }
                }

                System.out.println("Output written to " + output);  // Mensaje de confirmación
                               
            } catch (Exception e) {
                writer.println("Error durante el análisis de escaneo: " + e.getMessage());
                System.err.println("Error durante el análisis de escaneo: " + e.getMessage());
                if (debug) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Método para obtener el nombre de un token a partir de su símbolo.
     *
     * @param tokenSym Símbolo del token.
     * @return Nombre del token o "UNKNOWN" si no se encuentra.
     */
    private static String getTokenName(int tokenSym) {
        try {
            // Obtener todos los campos de la clase `sym`
            Field[] fields = sym.class.getFields();
            for (Field field : fields) { //encontrando coincidenciasa
                if (field.getType() == int.class && field.getInt(null) == tokenSym) {
                    return field.getName();
                }
            }
        } catch (IllegalAccessException e) {
            System.err.println("Error al obtener el nombre del token: " + e.getMessage());
        }
        return "UNKNOWN";
    }

    /**
     * Método para ejecutar el análisis sintáctico y generar el AST.
     *
     * @param filename Archivo de entrada.
     * @param output   Archivo de salida.
     * @param debug    Bandera para activar el modo debug.
     * @throws IOException Si ocurre un error de E/S.
     */
    private static void runParse(String filename, String output, boolean debug) throws IOException {
        // Crear el archivo de salida para el parsing normal
        try (PrintWriter writer = new PrintWriter(new FileWriter(output))) {
            // Indicar el inicio de la etapa de parsing
            writer.println("stage: parsing");
            System.out.println("stage: parsing");

            // Inicializar Scanner y Parser
            Scanner scanner = new Scanner(new FileReader(filename));
            Parser parser = new Parser(scanner);

            // Realizar el parsing
            Symbol result = parser.parse();

            // Verificar si el parsing resultó en un AST válido
            if (result == null || result.value == null) {
                writer.println("Error: No se pudo generar el AST.");
                if (debug) {
                    System.err.println("Error: No se pudo generar el AST.");
                }
                return;
            }

            // Obtener el nodo raíz del AST
            ProgramNode program = (ProgramNode) result.value;

            // Confirmar que el parsing fue exitoso
            writer.println("Parsing completed successfully.");
            if (debug) {
                System.out.println("Debug: Parsing completed successfully.");
            }

            // Indicar el inicio de la impresión del AST
            writer.println("AST:");
            ASTPrinter printer = new ASTPrinter(writer);

            // Traversar el AST y generar la representación
            program.accept(printer);
            writer.println(); // Añadir una línea en blanco al final

            if (debug) {
                System.out.println("Debug: AST generado correctamente en " + output);
            }

            // Mensaje de éxito
            System.out.println("Parsing completado exitosamente. AST generado en " + output);

    
        } catch (Exception e) {
            System.err.println("Error durante el parsing: " + e.getMessage());
            if (debug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para ejecutar la generación del archivo DOT y PDF.
     * @param filename Archivo de entrada.
     * @param output   Archivo de salida.
     * @param debug    Bandera para activar el modo debug.
     * @throws IOException Si ocurre un error de E/S.
     */
    private static void runDot(String filename, String output, boolean debug) throws IOException {
        String dotFile = output;
        String pdfFile = output.replaceAll("\\.dot$", "") + ".pdf";

        try (PrintWriter writer = new PrintWriter(new FileWriter(dotFile))) {
            // Inicializar Scanner y Parser
            Scanner scanner = new Scanner(new FileReader(filename));
            Parser parser = new Parser(scanner);

            // Realizar el parsing
            Symbol result = parser.parse();

            if (result != null && result.value != null) {
                ProgramNode program = (ProgramNode) result.value;

                // Crear el generador DOT
                ASTDotGenerator dotGenerator = new ASTDotGenerator(writer);

                // Generar el archivo DOT
                dotGenerator.beginGraph();
                program.accept(dotGenerator);
                dotGenerator.endGraph();

                System.out.println("Archivo DOT generado exitosamente en " + dotFile);

            }
        } catch (Exception e) {
            System.err.println("Error generando el archivo DOT/PDF: " + e.getMessage());
            if (debug) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para generar el PDF a partir del archivo DOT utilizando Graphviz.
     *
     * @param dotFile Ruta al archivo DOT.
     * @param pdfFile Ruta al archivo PDF de salida.
     * @param debug    Bandera para activar el modo debug.
     * @throws IOException Si ocurre un error durante la ejecución de `dot`.
     */
    private static void generatePDF(String dotFile, String pdfFile, boolean debug) throws IOException {
        // Si la bandera debug está activada, imprimir el contenido del archivo DOT
        if (debug) {
            System.out.println("Contenido del archivo DOT antes de generar el PDF:");
            try (BufferedReader br = new BufferedReader(new FileReader(dotFile))) {
                String line;
                int currentLine = 1;
                // Leer y mostrar cada línea del archivo DOT
                while ((line = br.readLine()) != null) {
                    System.out.printf("%d: %s%n", currentLine, line);
                    currentLine++;
                }
            } catch (IOException e) {
                System.err.println("Error leyendo el archivo DOT para depuración: " + e.getMessage());
                if (debug) {
                    e.printStackTrace();
                }
            }
        }
        // Configurar el comando para ejecutar `dot`
        ProcessBuilder pb = new ProcessBuilder("dot", "-Tpdf", dotFile, "-o", pdfFile);
        pb.redirectErrorStream(true); // Redirigir stderr a stdout
    
        // Ejecutar el proceso para generacion de pdf
        Process process = pb.start();
    
        // Leer la salida del proceso para detectar errores
        StringBuilder outputError = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputError.append(line).append("\n");
            }
        }

        // Esperar a que el proceso termine y verificar el código de salida
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException(outputError.toString().trim());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restablecer el estado de interrupción
            throw new IOException("Proceso de generación de PDF interrumpido", e);
        }
    }

    /**
     * Método para ejecutar el análisis semántico.
     *
     * @param filename Archivo de entrada.
     * @param output   Archivo de salida.
     * @param debug    Bandera para activar el modo debug.
     * @throws IOException Si ocurre un error de E/S.
     */
    private static void runSemantic(String filename, String output, boolean debug) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(output))) {
            // Indicar el inicio de la etapa de análisis semántico
            writer.println("stage: semantic analysis");
            System.out.println("stage: semantic analysis");

            // Inicializar Scanner y Parser
            Scanner scanner = new Scanner(new FileReader(filename));
            Parser parser = new Parser(scanner);
            
            // Realizar el parsing
            Symbol result = parser.parse();

            // Verificar si el parsing resultó en un AST válido
            if (result == null || result.value == null) {
                writer.println("Error: No se pudo generar el AST.");
                if (debug) {
                    System.err.println("Error: No se pudo generar el AST.");
                }
                return;
            }

            // Obtener el nodo raíz del AST
            ProgramNode program = (ProgramNode) result.value;

            // Realizar el análisis semántico
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
            program.accept(semanticAnalyzer);

            // Verificar si hubo errores semánticos
            if (semanticAnalyzer.hasErrors()) {
                writer.println("Se encontraron errores semánticos:");
                for (String error : semanticAnalyzer.getErrorMessages()) {
                    writer.println(error);
                    if (debug) {
                        System.err.println(error);
                    }
                }
                System.err.println("Análisis semántico terminado con errores. Ver el archivo de salida para más detalles.");
            } else {
                writer.println("Análisis semántico completado exitosamente. No se encontraron errores.");
                System.out.println("Análisis semántico completado exitosamente. No se encontraron errores.");
            }
        } catch (Exception e) {
            System.err.println("Error durante el análisis semántico: " + e.getMessage());
            if (debug) {
                e.printStackTrace();
            }
        }
    }


    private static void runIRT(String filename, String output, boolean debug) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(output))) {
            // Indicar el inicio de la etapa de análisis de IRT
            writer.println("stage: IRT analysis");
            System.out.println("stage: IRT analysis");
    
            // Inicializar Scanner y Parser
            Scanner scanner = new Scanner(new FileReader(filename));
            Parser parser = new Parser(scanner);
    
            // Realizar el parsing
            Symbol result = parser.parse();
    
            // Verificar si el parsing resultó en un AST válido
            if (result == null || result.value == null) {
                writer.println("Error: No se pudo generar el AST.");
                if (debug) {
                    System.err.println("Error: No se pudo generar el AST.");
                }
                return;
            }
    
            // Obtener el nodo raíz del AST
            ProgramNode program = (ProgramNode) result.value;
    
            // Realizar el análisis semántico
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
            program.accept(semanticAnalyzer);
    
            // Verificar si hubo errores semánticos
            if (semanticAnalyzer.hasErrors()) {
                writer.println("Se encontraron errores semánticos:");
                for (String error : semanticAnalyzer.getErrorMessages()) {
                    writer.println(error);
                    if (debug) {
                        System.err.println(error);
                    }
                }
                System.err.println("Análisis semántico terminado con errores. Ver el archivo de salida para más detalles.");
                return;
            }
    
            // Traducir el AST a IRT
            IRTTranslator irtTranslator = new IRTTranslator();
            program.accept(irtTranslator);
    
            // Obtener el IRT
            IRTStatement irtRoot = irtTranslator.getIRTRoot();
    
            // Imprimir el IRT
            IRTPrinter irtPrinter = new IRTPrinter(writer);
            irtRoot.accept(irtPrinter);
    
            System.out.println("IRT generado exitosamente en " + output);
    
        } catch (Exception e) {
            System.err.println("Error durante la traducción a IRT: " + e.getMessage());
            if (debug) {
                e.printStackTrace();
            }
        }
    }
    

    /**
     * Método para imprimir la ayuda y uso del compilador.
     */
    private static void printHelp() {
        System.out.println("");
        System.out.println("Uso: java -cp \".;compiler/lib/java-cup-11b-runtime.jar\" compiler/Compiler [option] <filename> -o <outname>");
        System.out.println("");
        System.out.println("Options:");
        System.out.println("-target <stage>: scan, parse, dot, semantic, IRT");
        System.out.println("-debug: Activa el modo debug.");
        System.out.println("-h: Muestra esta ayuda.");
        System.out.println("");
    }
}    