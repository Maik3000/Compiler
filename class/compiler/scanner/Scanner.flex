package compiler.scanner;

import java_cup.runtime.Symbol;
import compiler.parser.sym;
import compiler.ast.*;

/* Declaración del scanner */
%%
%public
%class Scanner
%unicode
%cup
%function next_token
%type java_cup.runtime.Symbol

%{

/* Variables de seguimiento de líneas y columnas */
private int lineNumber = 1;
private int columnNumber = 1;

/* Actualizar la posición del token */
private void setPosition() {
    yyline = lineNumber - 1;
    yycolumn = columnNumber - 1;
}

/* Métodos para crear símbolos con o sin valores */
private Symbol createSymbol(int tokenType) {
    return new Symbol(tokenType, yyline + 1, yycolumn + 1);
}

private Symbol createSymbol(int tokenType, Object tokenValue) {
    return new Symbol(tokenType, yyline + 1, yycolumn + 1, tokenValue);
}

/* Método para impresión de depuración */
private void printDebug(String msg) {
    System.out.println("[DEBUG] " + msg);
}

%}

/* Definir estados adicionales para comentarios */
%state MULTI_LINE_COMMENT

/* Definiciones de patrones */
WHITESPACE       = [ \t\f]+
NEWLINE          = \r\n?|\n
DIGIT            = [0-9]
LETTER           = [a-zA-Z]
ALPHANUM         = ({LETTER}|{DIGIT})
ID               = {LETTER}{ALPHANUM}*
INT_LITERAL      = {DIGIT}+
HEX_LITERAL      = 0[xX][0-9a-fA-F]+
CHAR_LITERAL     = \'([^\'\\\n]|\\[btnfr0\'\"\\])\'
STRING_LITERAL   = \"([^\"\\\n]|\\[btnfr0\'\"\\])*\" 

%%

/* Reglas del scanner */

/* Ignorar espacios en blanco */
{WHITESPACE}       { columnNumber += yylength(); }

/* Manejo de saltos de línea */
{NEWLINE}          { lineNumber++; columnNumber = 1; }

/* Comentarios de una línea */
"//".*             { columnNumber += yylength(); }

/* Comentarios de múltiples líneas */
"/*"               { columnNumber += 2; yybegin(MULTI_LINE_COMMENT); }

<MULTI_LINE_COMMENT> {
    "*/"           { columnNumber += 2; yybegin(YYINITIAL); }
    [^*\n]+        { columnNumber += yylength(); }
    "\n"           { lineNumber++; columnNumber = 1; }
    "*"            { columnNumber++; }
}

/* Palabras reservadas */

"class"            { setPosition(); columnNumber += yylength(); return createSymbol(sym.CLASS, yytext()); }
"if"               { setPosition(); columnNumber += yylength(); return createSymbol(sym.IF, yytext()); }
"callout"          { setPosition(); columnNumber += yylength(); return createSymbol(sym.CALLOUT, yytext()); }
"continue"         { setPosition(); columnNumber += yylength(); return createSymbol(sym.CONTINUE, yytext()); }
"for"              { setPosition(); columnNumber += yylength(); return createSymbol(sym.FOR, yytext()); }
"break"            { setPosition(); columnNumber += yylength(); return createSymbol(sym.BREAK, yytext()); }
"new"              { setPosition(); columnNumber += yylength(); return createSymbol(sym.NEW, yytext()); }
"true"             { setPosition(); columnNumber += yylength(); return createSymbol(sym.TRUE, yytext()); }
"false"            { setPosition(); columnNumber += yylength(); return createSymbol(sym.FALSE, yytext()); }
"boolean"          { setPosition(); columnNumber += yylength(); return createSymbol(sym.BOOLEAN, yytext()); }
"void"             { setPosition(); columnNumber += yylength(); return createSymbol(sym.VOID, yytext()); }
"else"             { setPosition(); columnNumber += yylength(); return createSymbol(sym.ELSE, yytext()); }
"int"              { setPosition(); columnNumber += yylength(); return createSymbol(sym.INT, yytext()); }
"while"            { setPosition(); columnNumber += yylength(); return createSymbol(sym.WHILE, yytext()); }
"char"             { setPosition(); columnNumber += yylength(); return createSymbol(sym.CHAR, yytext()); }
"return"           { setPosition(); columnNumber += yylength(); return createSymbol(sym.RETURN, yytext()); }

/* Operadores y símbolos */
";"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.SEMI, yytext()); }
","                { setPosition(); columnNumber += yylength(); return createSymbol(sym.COMMA, yytext()); }

"&&"               { setPosition(); columnNumber += yylength(); return createSymbol(sym.AND, yytext()); }
"||"               { setPosition(); columnNumber += yylength(); return createSymbol(sym.OR, yytext()); }
"!"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.NOT, yytext()); }
"<"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.LT, yytext()); }
">"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.GT, yytext()); }
"<="               { setPosition(); columnNumber += yylength(); return createSymbol(sym.LE, yytext()); }
">="               { setPosition(); columnNumber += yylength(); return createSymbol(sym.GE, yytext()); }
"="                { setPosition(); columnNumber += yylength(); return createSymbol(sym.ASSIGN, yytext()); }
"+="               { setPosition(); columnNumber += yylength(); return createSymbol(sym.PLUS_ASSIGN, yytext()); }
"-="               { setPosition(); columnNumber += yylength(); return createSymbol(sym.MINUS_ASSIGN, yytext()); }
"=="               { setPosition(); columnNumber += yylength(); return createSymbol(sym.EQ, yytext()); }
"!="               { setPosition(); columnNumber += yylength(); return createSymbol(sym.NEQ, yytext()); }

"+"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.PLUS, yytext()); }
"-"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.MINUS, yytext()); }
"*"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.TIMES, yytext()); }
"/"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.DIVIDE, yytext()); }
"%"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.MOD, yytext()); }

"("                { setPosition(); columnNumber += yylength(); return createSymbol(sym.LPAREN, yytext()); }
")"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.RPAREN, yytext()); }
"["                { setPosition(); columnNumber += yylength(); return createSymbol(sym.LBRACKET, yytext()); }
"]"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.RBRACKET, yytext()); }
"{"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.LBRACE, yytext()); }
"}"                { setPosition(); columnNumber += yylength(); return createSymbol(sym.RBRACE, yytext()); }


/* Identificadores y literales */
{ID}               { setPosition(); columnNumber += yylength(); return createSymbol(sym.ID, yytext()); }
{HEX_LITERAL}      { setPosition(); columnNumber += yylength(); return createSymbol(sym.INT_LITERAL, yytext()); }
{INT_LITERAL}      { setPosition(); columnNumber += yylength(); return createSymbol(sym.INT_LITERAL, Integer.parseInt(yytext())); }
{CHAR_LITERAL}     { setPosition(); columnNumber += yylength(); return createSymbol(sym.CHAR_LITERAL, yytext()); }
{STRING_LITERAL}   { setPosition(); columnNumber += yylength(); return createSymbol(sym.STRING_LITERAL, yytext()); }

/* Manejo de caracteres ilegales */
.                  { System.err.println("Caracter ilegal: " + yytext() + " en línea " + lineNumber + ", columna " + columnNumber); columnNumber += yylength(); }
