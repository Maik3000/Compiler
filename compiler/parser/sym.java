package parser;

public class sym {
    public static final int EOF = 0;
    public static final int CLASS = 1;
    public static final int PROGRAM = 2;
    public static final int IF = 3;
    public static final int ELSE = 4;
    public static final int WHILE = 5;
    public static final int RETURN = 6;
    public static final int TRUE = 7;
    public static final int FALSE = 8;
    public static final int INT = 9;
    public static final int FLOAT = 10;
    public static final int STRING = 11;
    public static final int VOID = 12;
    public static final int LPAREN = 13;
    public static final int RPAREN = 14;
    public static final int LBRACE = 15;
    public static final int RBRACE = 16;
    public static final int SEMICOLON = 17;
    public static final int COMMA = 18;
    public static final int IDENTIFIER = 19;
    public static final int INTLIT = 20;
    public static final int FLOATLIT = 21;
    public static final int STRINGLIT = 22;
    public static final int EQUALS = 23;
    public static final int EQUALS_EQUALS = 24;
    public static final int NOT_EQUALS = 25;
    public static final int LESS_THAN = 26;
    public static final int LESS_THAN_EQUALS = 27;
    public static final int GREATER_THAN = 28;
    public static final int GREATER_THAN_EQUALS = 29;
    public static final int PLUS = 30;
    public static final int MINUS = 31;
    public static final int MULTIPLY = 32;
    public static final int DIVIDE = 33;
    public static final int AND = 34;
    public static final int OR = 35;
    public static final int NOT = 36;
    public static final int error = 37;

    // Array para los nombres de los tokens
    public static final String[] terminalNames = new String[]{
        "EOF",
        "CLASS",
        "PROGRAM",
        "IF",
        "ELSE",
        "WHILE",
        "RETURN",
        "TRUE",
        "FALSE",
        "INT",
        "FLOAT",
        "STRING",
        "VOID",
        "LPAREN",
        "RPAREN",
        "LBRACE",
        "RBRACE",
        "SEMICOLON",
        "COMMA",
        "IDENTIFIER",
        "INTLIT",
        "FLOATLIT",
        "STRINGLIT",
        "EQUALS",
        "EQUALS_EQUALS",
        "NOT_EQUALS",
        "LESS_THAN",
        "LESS_THAN_EQUALS",
        "GREATER_THAN",
        "GREATER_THAN_EQUALS",
        "PLUS",
        "MINUS",
        "MULTIPLY",
        "DIVIDE",
        "AND",
        "OR",
        "NOT",
        "error"
    };
}
