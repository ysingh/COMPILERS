import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Main {
	private static String filepath;
	private static boolean pFlag = false;
	private static boolean sFlag = false;

	public static void main(String[] args) throws Exception {
		//System.out.println(args.length);
		//for (String s: args) {
			//System.out.println(s);
		//}
		if (args.length < 2  || args.length > 4 || !args[0].equals("-f")) {
			System.out.println("Usage: tigerc -f <pathToInputFile> [-s] [-p]");
			System.exit(1);
		}

		filepath = args[1];

		if (filepath == null || filepath.isBlank()) {
			System.out.println("Empty or null filepath");
			System.exit(1);
		}
		
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
			} 
			else {
				System.out.println("Usage: tigerc -f <pathToInputFile> [-s] [-p]");
				System.exit(1);
			}

			if (args[3].equals("-s")) {
				sFlag = true;
			} else if (args[3].equals("-p")) {
				pFlag = true;
			} 
			else {
				System.out.println("Usage: tigerc -f <pathToInputFile> [-s] [-p]");
				System.exit(1);
			}
		}

		//System.out.println(filepath);
		//System.out.println("pflag: " + pFlag);
		//System.out.println("sflag: " + sFlag);
		
		try {
			File file = new File(filepath);
			FileInputStream fileStream = new FileInputStream(file);
			ANTLRInputStream input = new ANTLRInputStream(fileStream);

			TigerLexer lexer = new TigerLexer(input);

			CommonTokenStream tokens = new CommonTokenStream(lexer);

			TigerParser parser = new TigerParser(tokens);

			ParseTree tree = parser.tigerProgram();
			System.out.println(tree.toStringTree(parser));

			ParseTreeWalker walker = new ParseTreeWalker();

			// walker.walk(new ShortToUnicodeString(), tree);
			// System.out.println();
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Error reading from file");
			System.exit(1);
		}
		
		System.exit(0);
	}
}
