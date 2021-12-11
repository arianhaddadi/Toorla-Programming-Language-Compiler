grammar Toorla;

@header {
    import toorla.* ;
    import toorla.ast.* ;
    import toorla.ast.declaration.* ;
    import toorla.ast.expression.* ;
    import toorla.ast.statement.* ;
    import toorla.ast.statement.localVarStats.* ;
    import toorla.ast.statement.returnStatement.*;
    import toorla.ast.expression.binaryExpression.*;
    import toorla.ast.expression.unaryExpression.* ;
    import toorla.ast.expression.value.* ;
    import toorla.ast.declaration.classDecs.*;
    import toorla.ast.declaration.classDecs.classMembersDecs.*;
    import toorla.ast.declaration.localVarDecs.* ;
    import toorla.types.* ;
    import toorla.types.arrayType.* ;
    import toorla.types.singleType.* ;
    import java.util.ArrayList ;
}

@members {

}

// Parser Rules

// Line Numbers arent added

program
	returns[Program p]:
	toorlaProgram { $p = $toorlaProgram.p; $p.line = $toorlaProgram.start.getLine() ;} EOF;

toorlaProgram
	returns[Program p]:{$p = new Program();}
	(a = classDeclaration { $p.addClass($a.dec); })* b = entryClassDeclaration { $p.addClass($b.dec); 
		} (c = classDeclaration { $p.addClass($c.dec); })* {$p.line = $start.getLine() ;};

classDeclaration
	returns[ClassDeclaration dec]: {}
	CLASS id = IDENTIFIER (INHERITS parent = IDENTIFIER)?{
                if($parent != null)
                        $dec = new ClassDeclaration(new Identifier($id.text) , new Identifier($parent.text)); 
                else
                        $dec = new ClassDeclaration(new Identifier($id.text)); 
        } COLLON (
		(m = methodDecSentence | f = fieldDecSentence) {
            if (($m.ctx != null)) {
                $dec.addMethodDeclaration($m.md) ;
            }
            else if (($f.ctx != null)) {
                $dec.addFieldsDeclarations($f.fdl);
            }
        }
	)* END {$dec.line = $CLASS.getLine() ;};

entryClassDeclaration
	returns[EntryClassDeclaration dec]:
        ENTRY CLASS id = IDENTIFIER (
		INHERITS parent = IDENTIFIER
	)? {
                if($parent != null)
                        $dec = new EntryClassDeclaration(new Identifier($id.text) , new Identifier($parent.text)); 
                else
                        $dec = new EntryClassDeclaration(new Identifier($id.text)); 
        } COLLON (
		(m = methodDecSentence | f = fieldDecSentence) {
            if (($m.ctx != null)) {
                $dec.addMethodDeclaration($m.md) ;
            }
            else if (($f.ctx != null)) {
                $dec.addFieldsDeclarations($f.fdl);
            }
        }
	)* END {$dec.line = $ENTRY.getLine() ;};

methodDecSentence
	returns[MethodDeclaration md]
	locals[ArrayList<Identifier> argsIds , ArrayList<Type> argsTypes]:
	{$argsIds = new ArrayList<Identifier>(); $argsTypes = new ArrayList<Type>();} a =
		ACCESS_MODIFIER? FUNCTION id = IDENTIFIER LPARENT (
		argId1 = IDENTIFIER COLLON (
			argType1 = DATA_TYPE
			| argDefdType1 = IDENTIFIER
		) (isArr1 = LBRACKET RBRACKET)? {
                        $argsIds.add(new Identifier($argId1.text));
                if($isArr1 != null){
                        if($argDefdType1 != null)
                                $argsTypes.add(new ArrayType(new UserDefinedType(new ClassDeclaration(new Identifier($argDefdType1.text)))));
                        else if ($argType1.text.equals( "string"))
                                $argsTypes.add(new ArrayType(new StringType()));
                        else if ($argType1.text.equals( "int"))
                                $argsTypes.add(new ArrayType(new IntType()));
                        else if ($argType1.text.equals( "bool"))
                                $argsTypes.add(new ArrayType(new BoolType()));}
                else { 
                    if($argDefdType1 != null)
                            $argsTypes.add(new UserDefinedType(new ClassDeclaration(new Identifier($argDefdType1.text))));
                    else if ($argType1.text.equals( "string"))
                            $argsTypes.add(new StringType());
                    else if ($argType1.text.equals( "int"))
                            $argsTypes.add(new IntType());
                    else if ($argType1.text.equals( "bool"))
                            $argsTypes.add(new BoolType());}
        } (
			COMMA argIdi = IDENTIFIER COLLON (
				argTypei = DATA_TYPE
				| argDefdTypei = IDENTIFIER
			) (isArri = LBRACKET RBRACKET)? {
                $argsIds.add(new Identifier($argIdi.text));
                if($isArri != null){
                    if($argDefdTypei != null)
                            $argsTypes.add(new ArrayType(new UserDefinedType(new ClassDeclaration(new Identifier($argDefdTypei.text)))));
                    else if ($argTypei.text.equals("string"))
                            $argsTypes.add(new ArrayType(new StringType()));
                    else if ($argTypei.text.equals("int"))
                            $argsTypes.add(new ArrayType(new IntType()));
                    else if ($argTypei.text.equals("bool"))
                            $argsTypes.add(new ArrayType(new BoolType()));}
                else  {
                    if($argDefdTypei != null)
                            $argsTypes.add(new UserDefinedType(new ClassDeclaration(new Identifier($argDefdTypei.text))));
                    else if ($argTypei.text.equals("string"))
                            $argsTypes.add(new StringType());
                    else if ($argTypei.text.equals("int"))
                            $argsTypes.add(new IntType());
                    else if ($argTypei.text.equals("bool"))
                            $argsTypes.add(new BoolType());}
            }
		)*
	)? RPARENT RETURNS (
		argTypeR = DATA_TYPE
		| argDefdTypeR = IDENTIFIER
	) (isArrR = LBRACKET RBRACKET)? COLLON body = block END {
        $md = new MethodDeclaration(new Identifier($id.text)) ;
        if($a != null)
            if($a.text.equals("public"))
                $md.setAccessModifier(AccessModifier.ACCESS_MODIFIER_PUBLIC);
            else
                $md.setAccessModifier(AccessModifier.ACCESS_MODIFIER_PRIVATE);
        for(int i = 0; (i < $argsIds.size()) && ($argsIds.size() > 0) ; i++)
            $md.addArg(new ParameterDeclaration($argsIds.get(i) , $argsTypes.get(i)));
        if($isArrR != null){
            if($argDefdTypeR != null)
                $md.setReturnType(new ArrayType(new UserDefinedType(new ClassDeclaration(new Identifier($argDefdTypeR.text)))));
            else if ($argTypeR.text.equals("string"))
                $md.setReturnType(new ArrayType(new StringType()));
            else if ($argTypeR.text.equals("int"))
                $md.setReturnType(new ArrayType(new IntType()));
            else if ($argTypeR.text.equals("bool"))
                $md.setReturnType(new ArrayType(new BoolType()));}
        else  {
            if($argDefdTypeR != null)
                $md.setReturnType(new UserDefinedType(new ClassDeclaration(new Identifier($argDefdTypeR.text))));
            else if ($argTypeR.text.equals("string"))
                $md.setReturnType(new StringType());
            else if ($argTypeR.text.equals("int"))
                $md.setReturnType(new IntType());
            else if ($argTypeR.text.equals("bool"))
                $md.setReturnType(new BoolType());}
        for(int i = 0; i < $body.sts.body.size() ; i++)
            $md.addStatement($body.sts.body.get(i));
        $md.line = $FUNCTION.getLine() ;
    };

fieldDecSentence
	returns[ArrayList<FieldDeclaration> fdl]
	locals[ArrayList<Identifier> ids , Type t]:
	{$ids = new ArrayList<Identifier>();} a = ACCESS_MODIFIER? FIELD id1 = IDENTIFIER {
        $ids.add(new Identifier($id1.text));
    } (
		(COMMA idi = IDENTIFIER) {
            $ids.add(new Identifier($idi.text));
        }
	)* (type = DATA_TYPE | defdType = IDENTIFIER) (
		isArr = LBRACKET RBRACKET
	)? SEMICOLLON {
        if($isArr != null){
            if($defdType != null)
                    $t = new ArrayType(new UserDefinedType(new ClassDeclaration(new Identifier($defdType.text))));
            else if ($type.text.equals("string"))
                    $t = new ArrayType(new StringType());
            else if ($type.text.equals("int"))
                    $t = new ArrayType(new IntType());
            else if ($type.text.equals("bool"))
                    $t = new ArrayType(new BoolType());}
        else  {
            if($defdType != null)
                    $t = new UserDefinedType(new ClassDeclaration(new Identifier($defdType.text)));
            else if ($type.text.equals("string"))
                    $t = new StringType();
            else if ($type.text.equals("int"))
                    $t = new IntType();
            else if ($type.text.equals("bool"))
                    $t = new BoolType();}
        $fdl = new ArrayList<FieldDeclaration>();
        if($a == null)
                for (int i = 0 ; i < $ids.size() ; i++ ) {
                        $fdl.add(new FieldDeclaration($ids.get(i) , $t));
                }
        else if($a.text.equals("public"))
                for (int i = 0 ; i < $ids.size() ; i++ ) 
                        $fdl.add(new FieldDeclaration($ids.get(i) , $t , AccessModifier.ACCESS_MODIFIER_PUBLIC));
        else 
                for (int i = 0 ; i < $ids.size() ; i++ ) 
                        $fdl.add(new FieldDeclaration($ids.get(i) , $t , AccessModifier.ACCESS_MODIFIER_PRIVATE));   
             
    };

block
	returns[Block sts]:{$sts = new Block() ;} (
		(
			(s = statement | BEGIN b = block END) {
                if($s.ctx != null){
                        $sts.addStatement($s.s);
                        $sts.line = $s.start.getLine() ;
                        }
                else if($b.ctx != null){
                        $sts.addStatement($b.sts);
                        $sts.line = $BEGIN.getLine() ;
                }        
                }
		)*
	);

statement
	returns[Statement s]:
	printLine {$s = $printLine.s ;}
	| variableDec {$s = $variableDec.s ;}
	| assignStatement {$s = $assignStatement.s ;}
	| incOrDecStatement {$s = $incOrDecStatement.s ;}
	| ifStatement {$s = $ifStatement.s ;}
	| returnStatement {$s = $returnStatement.s ;}
	| whileStatement {$s = $whileStatement.s ;}
	| BREAK SEMICOLLON {$s = new Break(); $s.line = $BREAK.getLine() ;}
	| CONTINUE SEMICOLLON {$s = new Continue() ;$s.line = $CONTINUE.getLine() ;}
	| SEMICOLLON {$s = new Skip() ;$s.line = $SEMICOLLON.getLine() ;};

printLine
	returns[PrintLine s]:
	PRINT LPARENT (e = expr) RPARENT SEMICOLLON {
                $s = new PrintLine($e.e);
                $s.line = $PRINT.getLine() ;
        };

/*
 objectMethodCall:
 IDENTIFIER DOT IDENTIFIER LPARENT (expr (COMMA expr)*)? RPARENT SEMICOLLON;
 
 methodCall:
 IDENTIFIER LPARENT (expr (COMMA expr)*)? RPARENT SEMICOLLON;
 */

variableDec
	returns[Statement s]
	locals[LocalVarDef temp , ArrayList<LocalVarDef> ds , LocalVarsDefinitions temp1]:
	{$ds = new ArrayList<LocalVarDef>();} VAR id1 = IDENTIFIER ASSIGN e1 = expr {
                $temp = new LocalVarDef(new Identifier($id1.text) , $e1.e);
                $temp.line = $VAR.getLine() ;
                $ds.add($temp) ;
        } (
		(COMMA idi = IDENTIFIER ASSIGN ei = expr) {
                $temp = new LocalVarDef(new Identifier($idi.text) , $ei.e);
                $temp.line = $VAR.getLine() ;
                $ds.add($temp) ;                
                }
	)* SEMICOLLON {
                if($ds.size() == 1) 
                        $s = $ds.get(0) ;
                else if($ds.size() > 1){
                        $temp1 = (LocalVarsDefinitions)new LocalVarsDefinitions() ;
                        for(int i=0; i < $ds.size() ; i++)
                                $temp1.addVarDefinition($ds.get(i));
                        $s = $temp1 ;
                        $s.line = $VAR.getLine() ;
                }
        };

assignStatement
	returns[Assign s]: (id = IDENTIFIER | f = fieldOrArrayCall | l = lonelyArrayCall) ASSIGN e = expr SEMICOLLON {
        if($id != null)
                $s = new Assign(new Identifier($id.text) , $e.e);
        else if($f.ctx != null) 
                $s = new Assign( $f.e , $e.e); 
        else if($l.ctx != null)
             $s = new Assign( $l.e , $e.e); 
        $s.line = $ASSIGN.getLine() ;        

};
incOrDecStatement
	returns[Statement s]:
	ex = expr (i = INCREASE | DECREASE) SEMICOLLON {
        if($i != null)
                $s = new IncStatement($ex.e);
        else
                $s = new DecStatement($ex.e);
        $s.line = $ex.start.getLine() ;              
};

ifStatement
	returns[Conditional s]:
	IF? p=LPARENT c = expr RPARENT (
		(BEGIN tb = block END)
		| ts = statement
	) (
		ELIF i = ifStatement
		| ELSE ((BEGIN etb = block END) | ets = statement)
	)? {
                if($i.ctx != null){
                        if( $tb.ctx != null )
                                $s = new Conditional($c.e , $tb.sts , $i.s );
                        else 
                                $s = new Conditional($c.e , $ts.s , $i.s );
                }
                else if($etb.ctx != null ){
                        if( $tb.ctx != null )
                                $s = new Conditional($c.e , $tb.sts , $etb.sts );
                        else 
                                $s = new Conditional($c.e , $ts.s , $etb.sts );}
                else if($ets.ctx != null){
                        if( $tb.ctx != null )
                                $s = new Conditional($c.e , $tb.sts , $ets.s );
                        else 
                                $s = new Conditional($c.e , $ts.s , $ets.s );}
                else{
                        if( $tb.ctx != null )
                                $s = new Conditional($c.e , $tb.sts );
                        else 
                                $s = new Conditional($c.e , $ts.s  );}

                $s.line = $c.start.getLine() ;
        };

returnStatement
	returns[Return s]:
	RETURN expr SEMICOLLON {
                $s = new Return($expr.e);
                $s.line = $RETURN.getLine() ;
        };

whileStatement
	returns[While s]:
	WHILE LPARENT expr RPARENT (statement | BEGIN block END) {
                if($statement.ctx != null)
                        $s = new While($expr.e , $statement.s);
                else
                        $s = new While($expr.e , $block.sts);
                $s.line = $WHILE.getLine() ;

        };

expr
	returns[Expression e]:
	orExpr {
                $e = $orExpr.e;
                $e.line = $orExpr.start.getLine() ;
        };
orExpr
	returns[Expression e]:
	a1 = andExpr {$e = $a1.e;$e.line = $a1.start.getLine() ;}
	| o1 = orExpr OR a2 = andExpr {$e = new Or($o1.e , $a2.e);$e.line = $OR.getLine() ;};
andExpr
	returns[Expression e]:
	e1 = equalityExpr {$e = $e1.e;$e.line = $e1.start.getLine() ;}
	| a1 = andExpr AND e2 = equalityExpr {$e = new And($a1.e , $e2.e);$e.line = $AND.getLine() ;};
equalityExpr
	returns[Expression e]:
	r1 = relationalExpr {$e = $r1.e;$e.line = $r1.start.getLine() ;}
	| e1 = equalityExpr EQUALS r2 = relationalExpr {$e = new Equals($e1.e , $r2.e);$e.line = $EQUALS.getLine() ;
		}
	| e2 = equalityExpr NOT_EQUAL r3 = relationalExpr {$e = new NotEquals($e2.e , $r3.e);$e.line = $NOT_EQUAL.getLine() ;
		};

relationalExpr
	returns[Expression e]:
	a1 = additionalExpr {$e = $a1.e;$e.line = $a1.start.getLine() ;}
	| r1 = relationalExpr GREATER_THAN a2 = additionalExpr {$e = new GreaterThan($r1.e , $a2.e);$e.line = $GREATER_THAN.getLine() ;
		}
	| r2 = relationalExpr LESS_THAN a3 = additionalExpr {$e = new LessThan($r2.e , $a3.e);$e.line = $LESS_THAN.getLine() ;
		};

additionalExpr
	returns[Expression e]:
	m1 = multiplicativeExpression {$e = $m1.e;$e.line = $m1.start.getLine() ;}
	| a1 = additionalExpr PLUS m2 = multiplicativeExpression {$e = new Plus($a1.e , $m2.e);$e.line = $PLUS.getLine() ;
		}
	| a2 = additionalExpr MINUS m3 = multiplicativeExpression {$e = new Minus($a2.e , $m3.e);$e.line = $MINUS.getLine() ;
		};

multiplicativeExpression
	returns[Expression e]:
	u1 = unaryExpr {$e = $u1.e;$e.line = $u1.start.getLine() ;}
	| m1 = multiplicativeExpression MULT u2 = unaryExpr {$e = new Times($m1.e , $u2.e); $e.line = $MULT.getLine() ;
		}
	| m2 = multiplicativeExpression DIV u3 = unaryExpr {$e = new Division($m2.e , $u3.e);$e.line = $DIV.getLine() ;
		}
	| m3 = multiplicativeExpression MOD u4 = unaryExpr {$e = new Modulo($m3.e , $u4.e);$e.line = $MOD.getLine() ;
		};

unaryExpr
	returns[Expression e]:
	a = arrayOrMethodOrfieldCall {
                $e = $a.e;
                $e.line = $a.start.getLine() ;
        }
	| MINUS u1 = unaryExpr {
                $e = new Neg($u1.e);
                $e.line = $MINUS.getLine() ;
        }
	| NOT u2 = unaryExpr {
                $e = new Not($u2.e);
                $e.line = $NOT.getLine() ;                
        };

// (SELF | IDENTIFIER | arrayCall) ( DOT (IDENTIFIER | arrayCall) )+
objectMethod
	returns[MethodCall e]
	locals[ArrayList<Expression> args]:
	{$args = new ArrayList<Expression>();} (
		fieldOrArrayCall DOT 
		| ins = IDENTIFIER DOT 
		| lonelyArrayCall DOT  |
	) id=IDENTIFIER LPARENT (
		e1 = expr {
                $args.add($e1.e) ;
        } (
			(COMMA ei = expr) {
                $args.add($ei.e) ;
        }
		)*
	)? RPARENT {
        if($fieldOrArrayCall.ctx != null ){
                $e = new MethodCall ($fieldOrArrayCall.e , new Identifier($id.text)); 
                for(int i = 0 ; i < $args.size() ; i++)
                        $e.addArg($args.get(i));
                $e.line = $fieldOrArrayCall.start.getLine() ;
        }
        else if($ins != null) {
                $e = new MethodCall (new Identifier($ins.text) , new Identifier($id.text)); 
                for(int i = 0 ; i < $args.size() ; i++)
                        $e.addArg($args.get(i));
                $e.line = $ins.getLine() ;
        } else if($lonelyArrayCall.ctx != null) {
                $e = new MethodCall ($lonelyArrayCall.e , new Identifier($id.text)); 
                for(int i = 0 ; i < $args.size() ; i++)
                        $e.addArg($args.get(i));
                $e.line = $lonelyArrayCall.start.getLine() ;
        } else {
                $e = new MethodCall (new Self() , new Identifier($id.text)); 
                for(int i = 0 ; i < $args.size() ; i++)
                        $e.addArg($args.get(i));
                $e.line = $id.getLine() ;
        }
        };
/*1
 method
 returns[Expression e]:
 IDENTIFIER LPARENT (expr (COMMA expr)*)? RPARENT;
 */
constVal
	returns[Expression e]:
	INT_LITERAL { $e = new IntValue($INT_LITERAL.int);
                $e.line = $INT_LITERAL.getLine() ;
        }
	| STRING_LITERAL {$e = new StringValue($STRING_LITERAL.text);
                $e.line = $STRING_LITERAL.getLine() ;
        }
	| BOOL_LITERAL {
                if($BOOL_LITERAL.text.equals("true"))
                        $e = new BoolValue(true) ;
                else
                        $e = new BoolValue(false) ;
                $e.line = $BOOL_LITERAL.getLine() ;
     
        };
lonelyArrayCall
	returns[Expression e]: (IDENTIFIER LBRACKET (expr) RBRACKET) {
         $e = new ArrayCall(new Identifier($IDENTIFIER.text) , $expr.e );
         $e.line = $IDENTIFIER.getLine() ;
 };
/*
 fieldCall
 returns[Expression e]: (
 (arrayCall | IDENTIFIER | SELF) (
 DOT ( arrayCall |
 IDENTIFIER)
 )+ SEMICOLLON?
 );
 
 */
/*
 fieldCall returns[Expression e] : 
 (fieldCall | arrayCall) DOT IDENTIFIER | IDENTIFIER | SELF;
 
 arrayCall returns [Expression e]: fieldCall LBRACKET expr RBRACKET ;
 */

fieldOrArrayCall
	returns[Expression e]: (id1 = IDENTIFIER | SELF) {
                if($id1 != null)
                        $e = new Identifier($id1.text) ;
                else
                        $e = new Self() ;
        } (
		(LBRACKET e1 = expr RBRACKET) {
                if($e1.ctx != null)
                        $e = new ArrayCall($e , $e1.e) ;
        }
	)? DOT id2 = IDENTIFIER { $e = new FieldCall($e , new Identifier($id2.text)) ;} (
		(LBRACKET e2 = expr RBRACKET) {
                if($e2.ctx != null)
                        $e = new ArrayCall($e , $e2.e) ;
        }
	)? (
		DOT idi = IDENTIFIER { $e = new FieldCall($e , new Identifier($id2.text)) ;} (
			(LBRACKET e2 = expr RBRACKET) {
                if($e2.ctx != null)
                        $e = new ArrayCall($e , $e2.e) ;
        }
		)?
	)*;

newArray
	returns[Expression e]
	locals[SingleType t]: (
		NEW (type = DATA_TYPE | defdType = IDENTIFIER) LBRACKET expr RBRACKET
	) {
                if($defdType != null)
                        $t = new UserDefinedType(new ClassDeclaration(new Identifier($defdType.text)));
                else if ($type.text.equals("string"))
                        $t = new StringType();
                else if ($type.text.equals("int"))
                        $t = new IntType();
                else if ($type.text.equals("bool"))
                        $t = new BoolType();
                $e = new NewArray($t , $expr.e);
        };
newClassInstance
	returns[Expression e]: (NEW IDENTIFIER LPARENT RPARENT) {
              $e = new NewClassInstance(new Identifier($IDENTIFIER.text)) ;
        };

arrayOrMethodOrfieldCall
	returns[Expression e]:
	o = objectMethod {$e = $o.e;}
	| f = fieldOrArrayCall {$e = $f.e;}
	| a = lonelyArrayCall {$e = $a.e;}
	| na = newArray {$e = $na.e;}
	| nc = newClassInstance {$e = $nc.e;}
	| v = constVal {$e = $v.e;}
	| id = IDENTIFIER {$e = new Identifier($id.text);}
	| s = SELF {$e = new Self();}
	| LPARENT pe = expr RPARENT {$e = $pe.e;};

// Lexer Rules

// KEWORDS

IF: 'if';
ELIF: 'elif';
ELSE: 'else';
CLASS: 'class';
DATA_TYPE: 'int' | 'bool' | 'string';
INHERITS: 'inherits';
FIELD: 'field';
ACCESS_MODIFIER: 'public' | 'private';
RETURN: 'return';
RETURNS: 'returns';
BEGIN: 'begin';
END: 'end';
CONTINUE: 'continue';
BREAK: 'break';
WHILE: 'while';
ENTRY: 'entry';
SELF: 'self';
NEW: 'new';
VAR: 'var';
FUNCTION: 'function';
PRINT: 'print';

// OPERATORS

NOT_EQUAL: '<>';
GREATER_THAN: '>';
LESS_THAN: '<';

EQUALS: '==';
ASSIGN: '=';

NOT: '!';
AND: '&&';
OR: '||';

INCREASE: '++';
DECREASE: '--';

PLUS: '+';
MINUS: '-';
MULT: '*';
DIV: '/';
MOD: '%';

// SYMBOLS

LPARENT: '(';
RPARENT: ')';
DOT: '.';

LBRACKET: '[';
RBRACKET: ']';

COMMA: ',';
COLLON: ':';
SEMICOLLON: ';';

INT_LITERAL: '0' | [1-9][0-9]*;
STRING_LITERAL: '"' ~["\\]* '"';
BOOL_LITERAL: 'true' | 'false';

//ID

IDENTIFIER: [a-zA-Z_][A-Za-z0-9_]*;
//WHITESPACE

WS: ([ \t\n\r] | '//' ~( '\r' | '\n')* | '/*' .*? '*/') -> skip;