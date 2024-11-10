package compiler.scanner;
import compiler.parser.sym;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java_cup.runtime.Symbol;  // Importa la clase Symbol

public class ScannerTest {
    public static void main(String[] args) {
        try {
            // Crear el PrintWriter para escribir los resultados en el archivo output.txt
            PrintWriter output = new PrintWriter("compiler/scanner/outputScannerTest.txt");

            // Crear el scanner generado por JFlex
            Scanner scanner = new Scanner(new FileReader("compiler/scanner/input.txt"));
            Symbol token;

            // Leer y mostrar los tokens hasta llegar al final del archivo
            while ((token = scanner.next_token()) != null) {
                // Verificar que el token no sea nulo y sea un token válido
                if (token.sym != sym.EOF) {
                    String tokenOutput = "Token: " + getTokenName(token.sym) + ", Value: " + token.value;
                    
                    // Escribir en el archivo
                    output.println(tokenOutput);
                } else {
                    break; // Salir cuando se encuentra el EOF
                }
            }

            // Cerrar el PrintWriter
            output.close();
            System.out.println("Output written to outputScannerTest.txt");

        } catch (IOException e) {
            System.err.println("Error al abrir el archivo de entrada: " + e.getMessage());
        }
    }

    // Función auxiliar para obtener el nombre del token a partir de su valor en sym.java
    private static String getTokenName(int tokenSym) {
        try {
            Field[] fields = sym.class.getFields();
            for (Field field : fields) {
                if (field.getType() == int.class && field.getInt(null) == tokenSym) {
                    return field.getName();
                }
            }
        } catch (IllegalAccessException e) {
            System.err.println("Error al obtener el nombre del token: " + e.getMessage());
        }
        return "UNKNOWN";
    }
}