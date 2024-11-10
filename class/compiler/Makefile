# Variables de compilación
JAVAC=javac
CUPJAR="C:\Users\Mayco\desktop\semestre 8\Compiladores\scanner\Compiladores\lib\java-cup-11b.jar"
CUPRUNTIME="C:\Users\Mayco\desktop\semestre 8\Compiladores\scanner\Compiladores\lib\java-cup-11b-runtime.jar"
CLASSPATH=.;$(CUPJAR);$(CUPRUNTIME)

# Directorios de origen y destino
SRC_DIR=.
BIN_DIR=.

# Archivos fuente y clases objetivo
SRC=$(wildcard $(SRC_DIR)/**/*.java)
CLASSES=$(SRC:$(SRC_DIR)/%.java=$(BIN_DIR)/%.class)

Compiler.class: Compiler.java
	$(JAVAC) -cp $(CLASSPATH) -d $(BIN_DIR) $(SRC_DIR)/Compiler.java

# Compilación de todas las clases
Compile-all: javac -cp ".;compiler/lib/java-cup-11b-runtime.jar" compiler/parser/sym.java compiler/scanner/Scanner.java compiler/parser/Parser.java compiler/ast/*.java compiler/Compiler.java compiler/parser/ParserTest.java compiler/scanner/ScannerTest.java

# Limpiar los archivos .class
clean:
	del /S /Q $(BIN_DIR)\*.class

generate-scanner:
	cd compiler/scanner 
	jflex Scanner.flex

generate-parser:
	cd compiler
	java -cp "C:/Users/Mayco/Desktop/semestre 8/Compiladores/Compiler/class/compiler/lib/java-cup-11b.jar" java_cup.Main -parser Parser -symbols sym -interface -destdir parser parser/parser.cup

test-scanner:
	java -cp ".;compiler/lib/java-cup-11b-runtime.jar" compiler/scanner/ScannerTest.java

test-parser:
	java -cp ".;compiler/lib/java-cup-11b-runtime.jar" compiler/parser/ParserTest.java

run-scanner:
	java -cp ".;compiler/lib/java-cup-11b-runtime.jar" compiler/Compiler -target scan compiler/input.txt -o Scan.txt

run-parser:
	java -cp ".;compiler/lib/java-cup-11b-runtime.jar" compiler/Compiler -target parse compiler/input.txt -o ast.txt

run-dot:
	java -cp ".;compiler/lib/java-cup-11b-runtime.jar" compiler/Compiler -target dot compiler/input.txt -o graph.dot

generate-graphviz_pdf: 
	dot -Tpdf graph.dot -o output_parse.pdf

run-CLI:
	java -cp ".;compiler/lib/java-cup-11b-runtime.jar" compiler/Compiler.java
