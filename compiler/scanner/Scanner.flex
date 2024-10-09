package scanner;

import java_cup.runtime.Symbol;
import parser.sym;

%%

%public
%class Scanner
%unicode
%cup
%line
%column

%{
  // Java code, if any, goes here
  // You can include additional imports if needed
%}

%%

/* Definition of tokens */

// Ignore whitespace
[ \t\r\n\f]+                      { /* skip whitespace */ }

// Operators
"+"                               { return new Symbol(sym.PLUS, yyline, yycolumn); }
"-"                               { return new Symbol(sym.MINUS, yyline, yycolumn); }
"*"                               { return new Symbol(sym.MULTIPLY, yyline, yycolumn); }
"/"                               { return new Symbol(sym.DIVIDE, yyline, yycolumn); }

// Comparison operators
"=="                              { return new Symbol(sym.EQUALS_EQUALS, yyline, yycolumn); }
"!="                              { return new Symbol(sym.NOT_EQUALS, yyline, yycolumn); }
"<"                               { return new Symbol(sym.LESS_THAN, yyline, yycolumn); }
"<="                              { return new Symbol(sym.LESS_THAN_EQUALS, yyline, yycolumn); }
">"                               { return new Symbol(sym.GREATER_THAN, yyline, yycolumn); }
">="                              { return new Symbol(sym.GREATER_THAN_EQUALS, yyline, yycolumn); }

// Parentheses
"("                               { return new Symbol(sym.LPAREN, yyline, yycolumn); }
")"                               { return new Symbol(sym.RPAREN, yyline, yycolumn); }

// Braces
"{"                               { return new Symbol(sym.LBRACE, yyline, yycolumn); }
"}"                               { return new Symbol(sym.RBRACE, yyline, yycolumn); }

// Literals
[0-9]+                            { return new Symbol(sym.INTLIT, yyline, yycolumn, Integer.parseInt(yytext())); }
[0-9]+"."([0-9]+)?               { return new Symbol(sym.FLOATLIT, yyline, yycolumn, Double.parseDouble(yytext())); }
\"([^\"]*)\"                      { return new Symbol(sym.STRINGLIT, yyline, yycolumn, yytext()); }

// Identifiers
[a-zA-Z][a-zA-Z0-9_]*            { return new Symbol(sym.IDENTIFIER, yyline, yycolumn, yytext()); }

// Keywords
"class"                           { return new Symbol(sym.CLASS, yyline, yycolumn); }
"Program"                         { return new Symbol(sym.PROGRAM, yyline, yycolumn); }
"if"                              { return new Symbol(sym.IF, yyline, yycolumn); }
"else"                            { return new Symbol(sym.ELSE, yyline, yycolumn); }
"while"                           { return new Symbol(sym.WHILE, yyline, yycolumn); }
"for"                             { return new Symbol(sym.FOR, yyline, yycolumn); }
"int"                             { return new Symbol(sym.INT, yyline, yycolumn); }
"float"                           { return new Symbol(sym.FLOAT, yyline, yycolumn); }
"boolean"                         { return new Symbol(sym.BOOLEAN, yyline, yycolumn); }
"true"                            { return new Symbol(sym.TRUE, yyline, yycolumn); }
"false"                           { return new Symbol(sym.FALSE, yyline, yycolumn); }
"void"                            { return new Symbol(sym.VOID, yyline, yycolumn); }
"return"                          { return new Symbol(sym.RETURN, yyline, yycolumn); }

// Other symbols
";"                               { return new Symbol(sym.SEMICOLON, yyline, yycolumn); }
","                               { return new Symbol(sym.COMMA, yyline, yycolumn); }

// Illegal characters
.                                 { System.err.println("Illegal character: " + yytext()); }
