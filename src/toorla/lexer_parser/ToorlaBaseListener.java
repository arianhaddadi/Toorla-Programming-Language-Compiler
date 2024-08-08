// Generated from D:/Codes/IntelliJ Projects/Toorla-Programming-Language-Compiler/grammar/Toorla.g4
// by ANTLR 4.13.1
package toorla.lexer_parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import toorla.ast.declarations.classes.*;
import toorla.ast.declarations.classes.classmembers.*;
import toorla.ast.declarations.localvars.*;
import toorla.ast.expressions.*;
import toorla.ast.expressions.binaryexpressions.*;
import toorla.ast.expressions.unaryexpressions.*;
import toorla.ast.expressions.value.*;
import toorla.ast.statements.*;
import toorla.ast.statements.localvars.*;
import toorla.types.*;

/**
 * This class provides an empty implementation of {@link ToorlaListener}, which can be extended to
 * create a listener which only needs to handle a subset of the available methods.
 */
@SuppressWarnings("CheckReturnValue")
public class ToorlaBaseListener implements ToorlaListener {
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterProgram(ToorlaParser.ProgramContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitProgram(ToorlaParser.ProgramContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterClassDeclaration(ToorlaParser.ClassDeclarationContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitClassDeclaration(ToorlaParser.ClassDeclarationContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterEntryClassDeclaration(ToorlaParser.EntryClassDeclarationContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitEntryClassDeclaration(ToorlaParser.EntryClassDeclarationContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterFieldDeclaration(ToorlaParser.FieldDeclarationContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitFieldDeclaration(ToorlaParser.FieldDeclarationContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterAccess_modifier(ToorlaParser.Access_modifierContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitAccess_modifier(ToorlaParser.Access_modifierContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterMethodDeclaration(ToorlaParser.MethodDeclarationContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitMethodDeclaration(ToorlaParser.MethodDeclarationContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterClosedStatement(ToorlaParser.ClosedStatementContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitClosedStatement(ToorlaParser.ClosedStatementContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterClosedConditional(ToorlaParser.ClosedConditionalContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitClosedConditional(ToorlaParser.ClosedConditionalContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterOpenConditional(ToorlaParser.OpenConditionalContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitOpenConditional(ToorlaParser.OpenConditionalContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterOpenStatement(ToorlaParser.OpenStatementContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitOpenStatement(ToorlaParser.OpenStatementContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatement(ToorlaParser.StatementContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatement(ToorlaParser.StatementContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementVarDef(ToorlaParser.StatementVarDefContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementVarDef(ToorlaParser.StatementVarDefContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementBlock(ToorlaParser.StatementBlockContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementBlock(ToorlaParser.StatementBlockContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementContinue(ToorlaParser.StatementContinueContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementContinue(ToorlaParser.StatementContinueContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementBreak(ToorlaParser.StatementBreakContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementBreak(ToorlaParser.StatementBreakContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementReturn(ToorlaParser.StatementReturnContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementReturn(ToorlaParser.StatementReturnContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementClosedLoop(ToorlaParser.StatementClosedLoopContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementClosedLoop(ToorlaParser.StatementClosedLoopContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementOpenLoop(ToorlaParser.StatementOpenLoopContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementOpenLoop(ToorlaParser.StatementOpenLoopContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementWrite(ToorlaParser.StatementWriteContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementWrite(ToorlaParser.StatementWriteContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementAssignment(ToorlaParser.StatementAssignmentContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementAssignment(ToorlaParser.StatementAssignmentContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementInc(ToorlaParser.StatementIncContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementInc(ToorlaParser.StatementIncContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterStatementDec(ToorlaParser.StatementDecContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitStatementDec(ToorlaParser.StatementDecContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpression(ToorlaParser.ExpressionContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpression(ToorlaParser.ExpressionContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionOr(ToorlaParser.ExpressionOrContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionOr(ToorlaParser.ExpressionOrContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionOrTemp(ToorlaParser.ExpressionOrTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionOrTemp(ToorlaParser.ExpressionOrTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionAnd(ToorlaParser.ExpressionAndContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionAnd(ToorlaParser.ExpressionAndContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionAndTemp(ToorlaParser.ExpressionAndTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionAndTemp(ToorlaParser.ExpressionAndTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionEq(ToorlaParser.ExpressionEqContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionEq(ToorlaParser.ExpressionEqContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionEqTemp(ToorlaParser.ExpressionEqTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionEqTemp(ToorlaParser.ExpressionEqTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionCmp(ToorlaParser.ExpressionCmpContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionCmp(ToorlaParser.ExpressionCmpContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionCmpTemp(ToorlaParser.ExpressionCmpTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionCmpTemp(ToorlaParser.ExpressionCmpTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionAdd(ToorlaParser.ExpressionAddContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionAdd(ToorlaParser.ExpressionAddContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionAddTemp(ToorlaParser.ExpressionAddTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionAddTemp(ToorlaParser.ExpressionAddTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionMultMod(ToorlaParser.ExpressionMultModContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionMultMod(ToorlaParser.ExpressionMultModContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionMultModTemp(ToorlaParser.ExpressionMultModTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionMultModTemp(ToorlaParser.ExpressionMultModTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionUnary(ToorlaParser.ExpressionUnaryContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionUnary(ToorlaParser.ExpressionUnaryContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionMethods(ToorlaParser.ExpressionMethodsContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionMethods(ToorlaParser.ExpressionMethodsContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionMethodsTemp(ToorlaParser.ExpressionMethodsTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionMethodsTemp(ToorlaParser.ExpressionMethodsTempContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterExpressionOther(ToorlaParser.ExpressionOtherContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitExpressionOther(ToorlaParser.ExpressionOtherContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterToorlaType(ToorlaParser.ToorlaTypeContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitToorlaType(ToorlaParser.ToorlaTypeContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterSingleType(ToorlaParser.SingleTypeContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitSingleType(ToorlaParser.SingleTypeContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void exitEveryRule(ParserRuleContext ctx) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void visitTerminal(TerminalNode node) {}

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation does nothing.
     */
    @Override
    public void visitErrorNode(ErrorNode node) {}
}
