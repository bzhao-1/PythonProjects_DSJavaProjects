import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

// An Iterator-like class for consuming streams of postscript tokens.
// (c) 2001,1996, 2001 duane a. bailey
// See Token.java for more help.

public class Reader implements Iterator<Token> {

    private Scanner scanner; // Scanner associated with input

    /**
     * Constructs a reader of postscript tokens from System.in (the terminal)
     */
    public Reader() {
        scanner = new Scanner(System.in);
    }

    /**
     * Constructs a reader of postscript tokens from the file filename.
     * @param filename the file to read postscript tokens from
     */
    public Reader(String filename) {
        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: " + filename + " file not found");
            System.exit(1);
        }
    }

    /**
     * Check if there is a next input token
     * @return returns true if there are more tokens on input stream
     */
    public boolean hasNext() {
        return scanner.hasNext();
    }

    /**
     * Get the next input token
     * @return consumes and returns next Token from input stream.
     */
    public Token next() {
        return readToken();
    }

    // post: returns next token from input stream, or null if error
    private Token readToken() {
        Token result = null;
        String s;
        do {
            if (!scanner.hasNext()) {
                // exit if we have no more input
                return null;
            }
            s = scanner.next();
            if (s.equals("%")) {
                scanner.nextLine();
                s = null;
            }
        } while (s == null);
        // exit if we're at end of procedure
        if (s.equals("}"))
            return null;
        else if (s.equals("{")) { // procedure start
            List<Token> list = new LinkedList<Token>();
            Token t;
            // consume tokens until end of procedure
            for (t = readToken(); t != null; t = readToken()) {
                list.add(t);
            }
            result = new Token(list);
        } else if (s.equals("true") || s.equals("false")) { // booleans
            result = new Token(s.equals("true"));
        } else
            try {
                result = new Token(Double.parseDouble(s));
            } catch (NumberFormatException e) {
                result = new Token(s); // all others are symbols
            }
        return result;
    }
}
