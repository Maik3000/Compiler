# Variables de compilación
JAVAC=javac
CUPJAR="C:/Users/Mayco/Desktop/semestre 8/Compiladores/Compiler/class/compiler/lib/java-cup-11b.jar"
CUPRUNTIME="C:/Users/Mayco/Desktop/semestre 8/Compiladores/Compiler/class/compiler/lib/java-cup-11b-runtime.jar"
CLASSPATH=.;compiler/lib/java-cup-11b-runtime.jar

# Directorios de origen y destino
SRC_DIR=compiler
BIN_DIR=classes
OUTPUT_DIR=outputs

# Comandos del sistema
MKDIR=mkdir
RMDIR=rmdir /S /Q
DEL=del /S /Q

# Archivos fuente
SRC_FILES := $(wildcard $(SRC_DIR)/*.java) \
             $(wildcard $(SRC_DIR)//.java) \
             $(wildcard $(SRC_DIR)///*.java) \
             $(wildcard $(SRC_DIR)////.java)
# Compilar todos los archivos
all: $(BIN_DIR)
	@echo "Archivos fuente:"
	@echo $(SRC_FILES)
	$(JAVAC) -d $(BIN_DIR) -cp "$(CLASSPATH)" $(SRC_FILES)

# Crear el directorio BIN_DIR si no existe
$(BIN_DIR):
	@if not exist "$(BIN_DIR)" $(MKDIR) "$(BIN_DIR)"

# Limpiar los archivos .class y la carpeta outputs
clean:
	@if exist "$(BIN_DIR)" $(RMDIR) "$(BIN_DIR)"
	@if exist "$(OUTPUT_DIR)" $(RMDIR) "$(OUTPUT_DIR)"
	@echo Eliminando archivos .class...
	@$(DEL) *.class
	@$(DEL) compiler\*.class
	@$(DEL) compiler\\.class
	@$(DEL) compiler\\\*.class
	@$(DEL) compiler\\\\.class
# Generar el Scanner.java mediante JFlex
generate-scanner:
	cd compiler\scanner && jflex Scanner.flex

# Generar el Parser.java mediante CUP
generate-parser:
	cd compiler && java -cp $(CUPJAR) java_cup.Main -parser Parser -symbols sym -interface -destdir parser parser\parser.cup

# Testear el scanner con ScannerTest.java
test-scanner: $(BIN_DIR)
	java -cp "$(CLASSPATH);$(BIN_DIR)" compiler.scanner.ScannerTest

# Testear el Parser con ParserTest.java
test-parser: $(BIN_DIR)
	java -cp "$(CLASSPATH);$(BIN_DIR)" compiler.parser.ParserTest

# Crear el directorio OUTPUT_DIR si no existe
$(OUTPUT_DIR):
	@if not exist "$(OUTPUT_DIR)" $(MKDIR) "$(OUTPUT_DIR)"

# Realizar la fase del escaneo
run-scanner: $(OUTPUT_DIR)
	java -cp "$(CLASSPATH);$(BIN_DIR)" compiler.Compiler -target scan compiler\input.txt -o $(OUTPUT_DIR)\Scan.txt

# Realizar la fase del parsing y AST
run-parser: $(OUTPUT_DIR)
	java -cp "$(CLASSPATH);$(BIN_DIR)" compiler.Compiler -target parse compiler\input.txt -o $(OUTPUT_DIR)\ast.txt

# Generar el archivo DOT
run-dot: $(OUTPUT_DIR)
	java -cp "$(CLASSPATH);$(BIN_DIR)" compiler.Compiler -target dot compiler\input.txt -o $(OUTPUT_DIR)\graph.dot

# Visualizar el AST en formato PDF
generate-graphviz: run-dot
	dot -Tpdf $(OUTPUT_DIR)\graph.dot -o $(OUTPUT_DIR)\output_parse.pdf

# Realizar el análisis semántico
run-semantic: $(OUTPUT_DIR)
	java -cp "$(CLASSPATH);$(BIN_DIR)" compiler.Compiler -target semantic compiler\input.txt -o $(OUTPUT_DIR)\semantic.txt

# Ejecutar la fase de IRT
run-IRT: $(OUTPUT_DIR)
	java -cp "$(CLASSPATH);$(BIN_DIR)" compiler.Compiler -target IRT compiler\input.txt -o $(OUTPUT_DIR)\IRTOutput.txt

# Ejecutar todas las fases del compilador
run-all: all run-scanner run-parser generate-graphviz run-semantic run-IRT
	@echo "Todas las fases del compilador se han ejecutado correctamente."

# Correr la CLI
run-CLI: $(BIN_DIR)
	java -cp "$(CLASSPATH);$(BIN_DIR)" compiler.Compiler