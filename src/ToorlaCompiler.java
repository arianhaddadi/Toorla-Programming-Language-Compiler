import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import toorla.ast.Program;
import toorla.nameAnalyzer.NameAnalyzer;
import toorla.typeAnalyzer.TypeAnalysisErrorPrinter;
import toorla.typeAnalyzer.TypeAnalyzer;

public class ToorlaCompiler {
    public void compile(CharStream textStream) {
        ToorlaLexer toorlaLexer = new ToorlaLexer(textStream);
        CommonTokenStream tokenStream = new CommonTokenStream(toorlaLexer);
        ToorlaParser toorlaParser = new ToorlaParser(tokenStream);
        Program toorlaASTCode = toorlaParser.program().mProgram;
        NameAnalyzer nameAnalyzer = new NameAnalyzer(toorlaASTCode);
        nameAnalyzer.analyze();
        TypeAnalyzer typeanalyzer = new TypeAnalyzer(nameAnalyzer.getClassHierarchy());
        toorlaASTCode.accept(typeanalyzer);
        TypeAnalysisErrorPrinter errorer = new TypeAnalysisErrorPrinter();
        toorlaASTCode.accept(errorer);
    }
}
