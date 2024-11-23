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
%line
%column
%function next_token
%type java_cup.runtime.Symbol

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
{WHITESPACE}       { /* Ignorar espacios en blanco */ }

/* Manejo de saltos de línea */
{NEWLINE}          { /* JFlex actualiza automáticamente yyline y yycolumn */ }

/* Comentarios de una línea */
"//".*             { /* Ignorar comentarios de una línea */ }

/* Comentarios de múltiples líneas */
"/*"               { yybegin(MULTI_LINE_COMMENT); }

<MULTI_LINE_COMMENT> {
    "*/"           { yybegin(YYINITIAL); }
    [^*]+          { /* Consumir cualquier caracter que no sea '*' */ }
    "*"            { /* Consumir '*' */ }
    "\n"           { /* JFlex actualiza automáticamente yyline y yycolumn */ }
}
/* Palabras reservadas */

"class"            { return new Symbol(sym.CLASS, yyline + 1, yycolumn + 1, yytext()); }
"if"               { return new Symbol(sym.IF, yyline + 1, yycolumn + 1, yytext()); }
"callout"          { return new Symbol(sym.CALLOUT, yyline + 1, yycolumn + 1, yytext()); }
"continue"         { return new Symbol(sym.CONTINUE, yyline + 1, yycolumn + 1, yytext()); }
"for"              { return new Symbol(sym.FOR, yyline + 1, yycolumn + 1, yytext()); }
"break"            { return new Symbol(sym.BREAK, yyline + 1, yycolumn + 1, yytext()); }
"new"              { return new Symbol(sym.NEW, yyline + 1, yycolumn + 1, yytext()); }
"true"             { return new Symbol(sym.TRUE, yyline + 1, yycolumn + 1, yytext()); }
"false"            { return new Symbol(sym.FALSE, yyline + 1, yycolumn + 1, yytext()); }
"boolean"          { return new Symbol(sym.BOOLEAN, yyline + 1, yycolumn + 1, yytext()); }
"void"             { return new Symbol(sym.VOID, yyline + 1, yycolumn + 1, yytext()); }
"else"             { return new Symbol(sym.ELSE, yyline + 1, yycolumn + 1, yytext()); }
"int"              { return new Symbol(sym.INT, yyline + 1, yycolumn + 1, yytext()); }
"while"            { return new Symbol(sym.WHILE, yyline + 1, yycolumn + 1, yytext()); }
"char"             { return new Symbol(sym.CHAR, yyline + 1, yycolumn + 1, yytext()); }
"return"           { return new Symbol(sym.RETURN, yyline + 1, yycolumn + 1, yytext()); }

/* Operadores y símbolos */
";"                { return new Symbol(sym.SEMI, yyline + 1, yycolumn + 1, yytext()); }
","                { return new Symbol(sym.COMMA, yyline + 1, yycolumn + 1, yytext()); }

"&&"               { return new Symbol(sym.AND, yyline + 1, yycolumn + 1, yytext()); }
"||"               { return new Symbol(sym.OR, yyline + 1, yycolumn + 1, yytext()); }
"!"                { return new Symbol(sym.NOT, yyline + 1, yycolumn + 1, yytext()); }
"<="               { return new Symbol(sym.LE, yyline + 1, yycolumn + 1, yytext()); }
">="               { return new Symbol(sym.GE, yyline + 1, yycolumn + 1, yytext()); }
"<"                { return new Symbol(sym.LT, yyline + 1, yycolumn + 1, yytext()); }
">"                { return new Symbol(sym.GT, yyline + 1, yycolumn + 1, yytext()); }
"=="               { return new Symbol(sym.EQ, yyline + 1, yycolumn + 1, yytext()); }
"!="               { return new Symbol(sym.NEQ, yyline + 1, yycolumn + 1, yytext()); }
"="                { return new Symbol(sym.ASSIGN, yyline + 1, yycolumn + 1, yytext()); }
"+="               { return new Symbol(sym.PLUS_ASSIGN, yyline + 1, yycolumn + 1, yytext()); }
"-="               { return new Symbol(sym.MINUS_ASSIGN, yyline + 1, yycolumn + 1, yytext()); }

"+"                { return new Symbol(sym.PLUS, yyline + 1, yycolumn + 1, yytext()); }
"-"                { return new Symbol(sym.MINUS, yyline + 1, yycolumn + 1, yytext()); }
"*"                { return new Symbol(sym.TIMES, yyline + 1, yycolumn + 1, yytext()); }
"/"                { return new Symbol(sym.DIVIDE, yyline + 1, yycolumn + 1, yytext()); }
"%"                { return new Symbol(sym.MOD, yyline + 1, yycolumn + 1, yytext()); }

"("                { return new Symbol(sym.LPAREN, yyline + 1, yycolumn + 1, yytext()); }
")"                { return new Symbol(sym.RPAREN, yyline + 1, yycolumn + 1, yytext()); }
"["                { return new Symbol(sym.LBRACKET, yyline + 1, yycolumn + 1, yytext()); }
"]"                { return new Symbol(sym.RBRACKET, yyline + 1, yycolumn + 1, yytext()); }
"{"                { return new Symbol(sym.LBRACE, yyline + 1, yycolumn + 1, yytext()); }
"}"                { return new Symbol(sym.RBRACE, yyline + 1, yycolumn + 1, yytext()); }


/* Identificadores y literales */
{ID}               { return new Symbol(sym.ID, yyline + 1, yycolumn + 1, yytext()); }
{HEX_LITERAL}      { return new Symbol(sym.INT_LITERAL, yyline + 1, yycolumn + 1, yytext()); }
{INT_LITERAL}      { return new Symbol(sym.INT_LITERAL, yyline + 1, yycolumn + 1, Integer.parseInt(yytext())); }
{CHAR_LITERAL}     { return new Symbol(sym.CHAR_LITERAL, yyline + 1, yycolumn + 1, yytext()); }
{STRING_LITERAL}   { return new Symbol(sym.STRING_LITERAL, yyline + 1, yycolumn + 1, yytext()); }

/* Manejo de caracteres ilegales */
.                  { System.err.println("Caracter ilegal: " + yytext() + " en línea " + (yyline + 1) + ", columna " + (yycolumn + 1)); }