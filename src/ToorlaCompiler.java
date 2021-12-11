import toorla.Pair;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import toorla.ast.Program;
import toorla.visitor.ClassDeclarationInspector;
import toorla.visitor.SymbolTableMaker;
import toorla.visitor.TreePrinter;
import toorla.visitor.Visitor;

import java.util.Comparator;

public class ToorlaCompiler {
    public void compile(CharStream textStream) {
        ToorlaLexer toorlaLexer = new ToorlaLexer( textStream );
        CommonTokenStream tokenStream = new CommonTokenStream( toorlaLexer );
        ToorlaParser toorlaParser = new ToorlaParser( tokenStream );
        Program toorlaASTCode = toorlaParser.program().mProgram;
        ClassDeclarationInspector classInspector = new ClassDeclarationInspector();
        toorlaASTCode.accept(classInspector) ;
        SymbolTableMaker symbolTabler = new SymbolTableMaker(classInspector.symbolTable , classInspector.Errors);
        toorlaASTCode.accept( symbolTabler );
        if(!(symbolTabler.hadError || classInspector.Error)){
            Visitor<Void> treePrinter = new TreePrinter();
            toorlaASTCode.accept( treePrinter );
        }else {
            symbolTabler.Errors.sort((o1, o2) -> {
                if(o1.getKey() == o2.getKey())
                    return 0;
                else if(o1.getKey() > o2.getKey())
                    return 1;
                else
                    return -1;
            });
            for (int i = 0 ; i < symbolTabler.Errors.size() ; i++)
                System.out.println(symbolTabler.Errors.get(i).getValue());
        }
    }
}
