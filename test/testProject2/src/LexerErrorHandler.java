import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class LexerErrorHandler extends BaseErrorListener {

    public static final LexerErrorHandler INSTANCE = new LexerErrorHandler();
 
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
       throws ParseCancellationException {
          throw new ParseCancellationException("VINISUCKS");
       }
 }
