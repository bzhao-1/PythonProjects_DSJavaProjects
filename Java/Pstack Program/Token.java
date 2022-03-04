
// A class for managing postscript tokens.
// (c) 2001,1996, 2001 duane a. bailey
// (c) 2021 aaron bauer
// (c) 2022 Ben Zhao 

import java.util.Iterator;
import java.util.List;

public class Token {
    // these constants define the type of token.
    public static final int NUMBER_KIND = 1;
    public static final int BOOLEAN_KIND = 2;
    public static final int SYMBOL_KIND = 3;
    public static final int PROCEDURE_KIND = 4;

    private int kind; // type of token
    private double number; // a double token value
    private boolean bool; // a boolean token value
    private String symbol; // name of a symbol
    private List<Token> procedure; // a list of tokens for procedures

    /**
     * Constructs a double token with value value
     * 
     * @param value The double value for the new Token
     */
    public Token(double value) {
        kind = NUMBER_KIND;
        this.number = value;
    }

    /**
     * Constructs   boolean token with value bool
     * 
     * @param bool The boolean value for the new Token
     */
    public Token(boolean bool) {
        kind = BOOLEAN_KIND;
        this.bool = bool;
    }

    /**
     * Constructs a symbol token with value symbol
     * 
     * @param symbol The String value for the new Token
     */
    public Token(String symbol) {
        kind = SYMBOL_KIND;
        this.symbol = symbol;
    }

    /**
     * Constructs a procedure token with values from the List proc
     * 
     * @param proc a list of Tokens in the procedure
     */
    public Token(List<Token> proc) {
        kind = PROCEDURE_KIND;
        this.procedure = proc;
    }

    /**
     * Get the kind of a token<br>
     * Great for use in switch statements
     * @return the kind of this token (one of BOOLEAN_KIND, NUMBER_KIND,
     *         SYMBOL_KIND, PROCEDURE_KIND)
     */
    public int kind() {
        return this.kind;
    }

    /**
     * @return true if this token is a number token
     */
    public boolean isNumber() {
        return kind == NUMBER_KIND;
    }

    /**
     * @return true if this token is a boolean token
     */
    public boolean isBoolean() {
        return kind == BOOLEAN_KIND;
    }

    /**
     * @return true if this token is a symbol token
     */
    public boolean isSymbol() {
        return kind == SYMBOL_KIND;
    }

    /**
     * @return true if this token is a procedure token
     */
    public boolean isProcedure() {
        return kind == PROCEDURE_KIND;
    }

    /**
     * Get the double value from a number token<br>
     * precondition: isNumber()
     * @return double value of this token
     */
    public double getNumber() {
        assert isNumber() : "cannot call getNumber on a Token that is not a number.";
        return number;
    }

    /**
     * Get the boolean value from a boolean token<br>
     * precondition: isBoolean()
     * @return boolean value of this token
     */
    public boolean getBoolean() {
        assert isBoolean() : "cannot call getBoolean on a Token that is not a boolean.";
        return bool;
    }

    /**
     * Get the string symbol from a symbol token<br>
     * precondition: isSymbol()
     * @return String value of this token
     */
    public String getSymbol() {
        assert isSymbol() : "cannot call getSymbol on a Token that is not a string.";
        return symbol;
    }

    /**
     * Get the list of tokens from a procedure token<br>
     * precondition: isProcedure()
     * @return List<Token> value of this token
     */
    public List<Token> getProcedure() {
        assert isProcedure() : "cannot call getProcedure on a Token that is not a procedure.";
        return procedure;
    }

    /**
     * Check if a token equals another object
     * @param other Object to compare with this for equality
     * @return true if this token has the same value as other
     */
    @Override
    public boolean equals(Object other) {
        if (other.getClass() != Token.class) {
            return false;
        }
        Token that = (Token) other;
        boolean result = false;
        // if types are different, the tokens are different
        if (this.kind != that.kind)
            return false;
        switch (this.kind) {
            case NUMBER_KIND:
                result = this.number == that.number;
                break;
            case BOOLEAN_KIND:
                result = this.bool == that.bool;
                break;
            case SYMBOL_KIND:
                result = this.symbol.equals(that.symbol);
                break;
            case PROCEDURE_KIND:
                result = this.procedure.equals(that.procedure);
                break;
        }
        return result;
    }

    /**
     * @return the hash code of this token
     */
    @Override
    public int hashCode() {
        switch (this.kind) {
            case NUMBER_KIND:
                return Double.hashCode(this.number);
            case BOOLEAN_KIND:
                return Boolean.hashCode(this.bool);
            case SYMBOL_KIND:
                return this.symbol.hashCode();
            case PROCEDURE_KIND:
                return this.procedure.hashCode();
            default:
                return 0;
        }
    }

    /**
     * @return String representation of token.
     */
    public String toString() {
        String result = "<unknown>";
        switch (kind) {
            case NUMBER_KIND:
                result = "" + number;
                break;
            case BOOLEAN_KIND:
                result = "" + bool;
                break;
            case SYMBOL_KIND:
                result = symbol;
                break;
            case PROCEDURE_KIND:
                result = "{ ";
                // the iterator allows us to visit elements nondestructively
                Iterator<Token> i = procedure.iterator();
                while (i.hasNext()) {
                    Token t = i.next();
                    result = result + " " + t;
                }
                result = result + " }";
                break;
        }
        return result;
    }

}
