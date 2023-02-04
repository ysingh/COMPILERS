import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ParserErrorHandler extends BaseErrorListener {

    public static final ParserErrorHandler INSTANCE = new ParserErrorHandler();
 
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
       throws ParseCancellationException {
          throw new ParseCancellationException("YUDISUCKS");
       }
 }
