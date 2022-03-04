// A simple symbol table for a postscript interpreter.
// (c) 2001,1996, 2001 duane a. bailey
// (c) 2021 aaron bauer
// (c) 2022 Ben Zhao

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Token> table; // the table is a map of strings to tokens.

    /**
     * Constructs empty symbol table
     */
    public SymbolTable() {
        table = new HashMap<String, Token>();
    }

    /**
     * Check if a string is present in the table
     * @param symbol String to check for in the table
     * @return true if symbol is in the table
     */
    public boolean contains(String symbol) {
        return table.containsKey(symbol);
    }

    /**
     * Add a string and associated token to the table
     * @param symbol String to add to the table
     * @param value  Token to associate with symbol Note: if symbol is already in
     *               the table, its associated Token will be replaced with value
     */
    public void add(String symbol, Token value) {
        table.put(symbol, value);
    }

    /**
     * Retrieve the token associated with a string from the table
     * @param symbol String to retrieve from the table
     * @return returns Token associated with symbol
     */
    public Token get(String symbol) {
        return table.get(symbol);
    }

    /**
     * Remove a string and the associated token from the table
     * @param symbol String to remove from the table
     * @return Token associated with symbol
     *        
     */
    public Token remove(String symbol) {
        return table.remove(symbol);
    }

    /**
     * @return returns printable version of symbol table.
     */
    public String toString() {
        return table.toString();
    }
}
