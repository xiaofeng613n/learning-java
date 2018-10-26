package xiaofeng;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String [] args) throws IOException {

        CharStream input = CharStreams.fromString("12*2+12\r\n");
        MathLexer lexer=new MathLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MathParser parser = new MathParser(tokens);
        ParseTree tree = parser.prog(); // parse
        MathBaseVisitor vt=new MathBaseVisitor();
        vt.visit(tree);

    }

    public static void testAssign() throws IOException {

        ANTLRInputStream input = new ANTLRInputStream(System.in);

        AssignLexer lexer = new AssignLexer(input);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        AssignParser parser = new A(tokens);

        ParseTree tree = parser.assign();

        System.out.println(tree.toStringTree(parser));

    }

}
