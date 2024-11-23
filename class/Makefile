# Variables de compilación
JAVAC=javac
CUPJAR="C:/Users/Mayco/Desktop/semestre 8/Compiladores/Compiler/class/compiler/lib/java-cup-11b.jar"
CUPRUNTIME="C:/Users/Mayco/Desktop/semestre 8/Compiladores/Compiler/class/compiler/lib/java-cup-11b-runtime.jar"
CLASSPATH=".;compiler/lib/java-cup-11b-runtime.jar"

# Directorios de origen y destino
SRC_DIR=.
BIN_DIR=.

# Archivos fuente y clases objetivo
SRC=$(wildcard $(SRC_DIR)/**/*.java)
CLASSES=$(SRC:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

# compilar todos los archivos
all: 
	javac -cp "$(CLASSPATH)" compiler/parser/sym.java compiler/scanner/Scanner.java compiler/parser/Parser.java compiler/ast/*.java compiler/Compiler.java compiler/parser/ParserTest.java compiler/scanner/ScannerTest.java compiler/semantic/*.java

# Compilación de todas las clases
Compile-all: 

# Limpiar los archivos .class
clean:
	del /S /Q $(BIN_DIR)\*.class

# Generar el Scanner.java mediante JFLEX
generate-scanner:
	cd compiler/scanner && jflex Scanner.flex

#Generar el Parser.java mediante CUP
generate-parser:
	cd compiler && java -cp "C:/Users/Mayco/Desktop/semestre 8/Compiladores/Compiler/class/compiler/lib/java-cup-11b.jar" java_cup.Main -parser Parser -symbols sym -interface -destdir parser parser/parser.cup

# Testear el scanner con ScannerTest.java
test-scanner:
	java -cp "$(CLASSPATH)" compiler/scanner/ScannerTest.java

#Testear el Parser con ParserTest.java
test-parser:
	java -cp "$(CLASSPATH)" compiler/parser/ParserTest.java

# Realizar la fase del escaneo
run-scanner:
	java -cp "$(CLASSPATH)" compiler/Compiler -target scan compiler/input.txt -o Scan.txt

# realizar la fase del parsing y AST
run-parser:
	java -cp "$(CLASSPATH)" compiler/Compiler -target parse compiler/input.txt -o ast.txt

# Generar el archivo DOT
run-dot:
	java -cp "$(CLASSPATH)" compiler/Compiler -target dot compiler/input.txt -o graph.dot

# Visualizar el AST en formato PDF
generate-graphviz: 
	dot -Tpdf graph.dot -o output_parse.pdf

# Correr la CLI
run-CLI:
	java -cp "$(CLASSPATH)" compiler/Compiler.java

