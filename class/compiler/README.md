# Proyecto: Compilador para el Lenguaje Decaf

## Descripción

Este proyecto es un compilador para el lenguaje de programación **Decaf**, un lenguaje educativo diseñado para ayudar a los estudiantes a entender los conceptos fundamentales de la compilación y el análisis de lenguajes de programación. El compilador está implementado en **Java** y utiliza herramientas como **JFlex** para el análisis léxico y **JavaCup** para el análisis sintáctico.

### Características Principales

- **Análisis Léxico**: Utiliza JFlex para convertir el código fuente en una serie de tokens que representan los elementos sintácticos del lenguaje Decaf, como palabras reservadas, operadores, identificadores y literales.
  
- **Análisis Sintáctico**: Implementa un parser utilizando JavaCup, que toma los tokens generados por el escáner y construye un árbol de sintaxis abstracta (AST) basado en la gramática del lenguaje.

- **Manejo de Errores**: Incluye mecanismos para detectar y reportar errores de sintaxis y de lexemas en el código fuente.

- **Extensibilidad**: El diseño del compilador permite la fácil adición de nuevas características y la modificación de la gramática para soportar más funcionalidades del lenguaje.

### Estructura del Proyecto

- **scanner/**: Contiene el código del analizador léxico, incluyendo las definiciones de tokens y la clase `Scanner`.
  
- **parser/**: Incluye el código del analizador sintáctico, donde se define la gramática del lenguaje Decaf y las producciones necesarias para construir el AST.

- **test/**: Proporciona ejemplos de archivos de entrada para probar el compilador, así como los casos de prueba.

### Requisitos

- **Java JDK**: Asegúrate de tener instalado Java Development Kit (JDK) para compilar y ejecutar el proyecto.
- **JFlex**: Herramienta para la generación del analizador léxico.
- **JavaCup**: Herramienta para la generación del analizador sintáctico.

### Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu_usuario/tu_repositorio.git
   cd tu_repositorio

2. Compila el proyecto:
   
    ```bash
    javac -d classes -cp ".;lib/java-cup-11b-runtime.jar" -Xlint:deprecation parser/Parser.java parser/sym.java parser/ParserTest.java scanner/Scanner.java             scanner/ScannerTest.java

    javac -d classes -cp ".;lib/java-cup-11b-runtime.jar" Main.java scanner/Scanner.java parser/Parser.java

4. Ejecuta los archivos de prueba:
   ```bash
   java -cp ".;classes;lib/java-cup-11b-runtime.jar" parser.ParserTest
   java -cp ".;classes;lib/java-cup-11b-runtime.jar" scanner.ScannerTest
   java -cp ".;classes;lib/java-cup-11b-runtime.jar" compiler.Main
   
