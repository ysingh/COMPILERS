import java.io.File;
import java.io.*;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class Main {
	private static String filepath;
	private static boolean pFlag = false;
	private static boolean sFlag = false;
	private static String sFilePath;
	private static int returnCode = 0;

	public static void main(String[] args) throws Exception {
		// System.out.println(args.length);
		// for (String s: args) {
		// System.out.println(s);
		// }
		if (args.length < 2 || args.length > 4 || !args[0].equals("-f")) {
			System.out.println("Usage: tigerc -f <pathToInputFile> [-s] [-p]");
			System.exit(1);
		}

		filepath = args[1];

		if (filepath == null || filepath.isBlank()) {
			System.out.println("Empty or null filepath");
			System.exit(1);
		}

		String[] pathParts = filepath.split("\\.");

		// System.out.println(pathParts.length);
		// System.out.println(filepath.length());

		pathParts[pathParts.length - 1] = "tokens";
		sFilePath = String.join(".", pathParts);

		// System.out.println(filepath);
		// System.out.println(sFilePath);

		if (args.length == 3) {
			if (args[2].equals("-s")) {
				sFlag = true;
			} else if (args[2].equals("-p")) {
				pFlag = true;
			} else {
				System.out.println("Usage: tigerc -f <pathToInputFile> [-s] [-p]");
				System.exit(1);
			}
		}

		if (args.length == 4) {
			if (args[2].equals("-s")) {
				sFlag = true;
			} else if (args[2].equals("-p")) {
				pFlag = true;
			} else {
				System.out.println("Usage: tigerc -f <pathToInputFile> [-s] [-p]");
				System.exit(1);
			}

			if (args[3].equals("-s")) {
				sFlag = true;
			} else if (args[3].equals("-p")) {
				pFlag = true;
			} else {
				System.out.println("Usage: tigerc -f <pathToInputFile> [-s] [-p]");
				System.exit(1);
			}
		}

		// System.out.println(filepath);
		// System.out.println("pflag: " + pFlag);
		// System.out.println("sflag: " + sFlag);

		try {
			File file = new File(filepath);
			FileInputStream fileStream = new FileInputStream(file);

			ANTLRInputStream input = new ANTLRInputStream(fileStream);

			TigerLexer lexer = new TigerLexer(input);
			
			lexer.removeErrorListeners();
			lexer.addErrorListener(LexerErrorHandler.INSTANCE);
			
			
			CommonTokenStream tokens = new CommonTokenStream(lexer);

			/*
			 * List<Token> tokenList = tokens.getTokens();
			 * for (Token token : tokenList) {
			 * System.out.println(token);
			 * }
			 */

			TigerParser parser = new TigerParser(tokens);
			
			parser.removeErrorListeners();
			parser.addErrorListener(ParserErrorHandler.INSTANCE);
			
			if (sFlag == true) {
				// System.out.println("PRINTING TOKENS");
				try {
					File tokenFile = new File(sFilePath);
					FileOutputStream tokenOutputFileStream = new FileOutputStream(tokenFile);
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(tokenOutputFileStream));

					for (Token token : lexer.getAllTokens()) {
						bw.write("<" + parser.getVocabulary().getSymbolicName(token.getType()) + ", " + '"'
								+ token.getText() + '"' + ">");
						lexer.setToken(token);
						bw.newLine();
					}

					bw.close();
				} catch (IOException e) {
					System.out.println("Error writing token file");
					System.exit(1);
				}
			}

			else {
				ParseTree tree = parser.tigerProgram();
				// System.out.println(tree.toStringTree(parser));

				ParseTreeWalker walker = new ParseTreeWalker();

				// walker.walk(new ShortToUnicodeString(), tree);
				// System.out.println();
			}
			//System.exit(returnCode);
		} catch (IOException e) {
			System.out.println("Error reading from file");
			System.exit(1);
		} catch(ParseCancellationException rex) {
			if (rex.getMessage() == "VINISUCKS") {
				returnCode = 2;
			} else if (rex.getMessage() == "YUDISUCKS") {
				returnCode = 3;
			}
		}

		System.exit(returnCode);
	}
}
