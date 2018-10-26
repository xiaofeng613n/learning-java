package xiaofeng;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import xiaofeng.expr.EvalVisitor;
import xiaofeng.expr.ExprLexer;
import xiaofeng.expr.ExprParser;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String [] args) throws IOException {

        testExpr();

    }

    public static void testExpr() throws IOException {

        //ExprLexer 是词法分析器， ExprParser是语法分析器

        ANTLRInputStream inputStream = new ANTLRInputStream("1 + 2 + 3 * 4+ 6 / 2");
        ExprLexer lexer = new ExprLexer(inputStream);

        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        ExprParser parser = new ExprParser(tokenStream);
        ParseTree parseTree = parser.prog();
        EvalVisitor visitor = new EvalVisitor();
        Integer rtn = visitor.visit(parseTree);
        System.out.println("#result#"+rtn.toString());

    }

}
