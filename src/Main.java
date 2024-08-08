import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("No input files given.");
        } else if (args.length > 1) {
            System.out.println("Too many arguments. " +
                    "You must only give the address of input source code file" +
                    ".");
        } else {
            CharStream textStream = CharStreams.fromFileName(args[0]);
            ToorlaCompiler compiler = new ToorlaCompiler();
            compiler.compile(textStream);
        }
    }
}
