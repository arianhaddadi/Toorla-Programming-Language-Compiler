import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import toorla.ast.Program;
import toorla.lexer_parser.ToorlaLexer;
import toorla.lexer_parser.ToorlaParser;
import toorla.visitors.ErrorReporter;
import toorla.visitors.codegen.CodeGenerator;
import toorla.visitors.nameanalysis.NameAnalyzer;
import toorla.visitors.typecheck.TypeChecker;

public class ToorlaCompiler {
    public void compile(CharStream textStream) {
        ToorlaLexer toorlaLexer = new ToorlaLexer(textStream);
        CommonTokenStream tokenStream = new CommonTokenStream(toorlaLexer);
        ToorlaParser toorlaParser = new ToorlaParser(tokenStream);
        Program toorlaASTCode = toorlaParser.program().mProgram;
        ErrorReporter errorReporter = new ErrorReporter();
        NameAnalyzer nameAnalyzer = new NameAnalyzer(toorlaASTCode);
        nameAnalyzer.analyze();
        toorlaASTCode.accept(errorReporter);
        TypeChecker typeChecker = new TypeChecker(nameAnalyzer.getClassHierarchy());
        toorlaASTCode.accept(typeChecker);
        CodeGenerator codeGenerator = new CodeGenerator(nameAnalyzer.getClassHierarchy());
        toorlaASTCode.accept(codeGenerator);
    }
}
