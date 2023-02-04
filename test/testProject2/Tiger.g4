grammar Tiger;

WS : [ \t\r\n]+ -> skip;
COMMENT :  '/*' .*? '*/'  -> skip;

tigerProgram: PROGRAM ID LET declarationSegment BEGIN functList END EOF;
valueTail: OPENBRACK expr CLOSEBRACK | ;
value: ID valueTail;
exprListTail: COMMA expr exprListTail | ;
exprList: expr exprListTail | ;
binaryOperator: PLUS |
                MINUS |
                MULT |
                DIV |
                POW |
                EQUAL |
                NEQUAL |
                LESS |
                GREAT |
                LESSEQ |
                GREATEQ |
                AND |
                OR;

const: INTLIT | FLOATLIT;
expr: const | value | expr binaryOperator expr |
      OPENPAREN expr CLOSEPAREN;


optprefix: value ASSIGN | ;
optreturn: expr | ;



stat: value ASSIGN expr SEMICOLON |
      IF expr THEN statSeq ENDIF SEMICOLON |
      IF expr THEN statSeq ELSE statSeq ENDIF SEMICOLON |
      WHILE expr DO statSeq ENDDO SEMICOLON |
      FOR ID ASSIGN expr TO expr DO statSeq ENDDO SEMICOLON |
      optprefix ID OPENPAREN exprList CLOSEPAREN SEMICOLON |
      BREAK SEMICOLON |
      RETURN optreturn SEMICOLON |
      LET declarationSegment BEGIN statSeq END;


statSeq: stat | stat statSeq;
param: ID COLON type;
retType: COLON type | ;
paramListTail: COMMA param paramListTail | ;
paramList: param paramListTail | ;
funct: FUNCTION ID OPENPAREN paramList CLOSEPAREN retType BEGIN statSeq END;
optionalInit: ASSIGN const | ;
idList: ID | ID COMMA idList;
storageClass: VAR | STATIC;
varDeclaration: storageClass idList COLON type optionalInit SEMICOLON;
baseType: INT | FLOAT;
type: baseType | ARRAY OPENBRACK INTLIT CLOSEBRACK OF baseType | ID;
typeDeclaration: TYPE ID TASSIGN type SEMICOLON;
varDeclarationList: varDeclaration varDeclarationList | ;
typeDeclarationList: typeDeclaration typeDeclarationList | ;
functList: funct functList | ;
declarationSegment: typeDeclarationList varDeclarationList;

// Keywords
ARRAY: 'array';
BEGIN: 'begin';
BREAK: 'break';
DO: 'do';
ELSE: 'else';
END: 'end';
ENDDO: 'enddo';
ENDIF: 'endif';
FLOAT: 'float';
FOR: 'for';
FUNCTION: 'function';
IF: 'if';
INT: 'int';
LET: 'let';
OF: 'of';
PROGRAM: 'program';
RETURN: 'return';
STATIC: 'static';
THEN: 'then';
TO: 'to';
TYPE: 'type';
VAR: 'var';
WHILE: 'while';

// Punctuation
COMMA: ','      ;
DOT: '.'        ;
COLON: ':'      ;
SEMICOLON: ';'  ;
OPENPAREN: '('  ;
CLOSEPAREN: ')' ;
OPENBRACK: '['  ;
CLOSEBRACK: ']' ;
OPENCURLY: '{'  ;
CLOSECURLY: '}' ;


// Binary Operations
PLUS: '+'       ;
MINUS: '-'      ;
MULT: '*'       ;
DIV: '/'        ;
POW: '**'       ;
EQUAL: '=='     ;
NEQUAL: '!='    ;
LESS: '<'       ;
GREAT: '>'      ;
LESSEQ: '<='    ;
GREATEQ: '>='    ;
AND: '&'        ;
OR: '|'         ;

// Assignment Operators
ASSIGN: ':='    ;
TASSIGN: '='    ;

//Special Lexical Rules
ID: [a-zA-Z][a-zA-Z0-9_]* ;
INTLIT: '0'|[1-9]([0-9]*);
FLOATLIT: [0]'.'[0-9]*|([1-9]+([0]+)? '.' [0-9]*);
