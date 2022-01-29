/**
 * Name: Ben Zhao 
 * Email: zhaob@carleton.edu
 * Partner's Name: Chris Elliott 
 * Partner's Email: elliottc2@carleton.edu
 * Description: This program implements PostScript which is stack based and has command with 
 * each command operation that is executed pops its operands from the stack and pushes on a result. 
 */

import java.util.ArrayDeque;


public class Interpreter {

    private SymbolTable table;        // PostScript symbol table
    private ArrayDeque<Token> stack;  // PostScript operand stack

    /**
     * Constructs a new PostScript Interpreter
     */
    public Interpreter() {
        table = new SymbolTable();
        stack = new ArrayDeque<Token>();
    }

    /**
     * Iterates over the Tokens in reader until they have all been processed
     * or a symbol token with the value "quit" is encountered
     */
    public void interpret(Reader reader) {
        // use reader as an iterator over tokens to process
        while (reader.hasNext()) {
            Token t = reader.next();
            // only check for quit if token is a symbol:
            if (t.isSymbol() && t.getSymbol().equals("quit")) {
                break;  // break statement exits the current loop
            }
            // process token
            processToken(t);
        }
        
    }

    /**
     * Process the token and perform the appropriate action
     */
    public void processToken(Token token) {
        switch(token.kind()) {
            case Token.BOOLEAN_KIND:
            case Token.NUMBER_KIND:
            case Token.PROCEDURE_KIND:
                stack.push(token);
                break;
            case Token.SYMBOL_KIND:
                switch(token.getSymbol()) {
                    case "pstack": 
                        pstack();
                        break;
                    case "pop": 
                        pop();
                        break;
                    case "add": 
                        add();
                        break;
                    case "sub": 
                        sub();
                        break;
                    case "mul": 
                        mul();
                        break;
                    case "div": 
                        div();
                        break;
                    case "lt": 
                        lt();
                        break;
                    case "gt": 
                        gt();
                        break;
                    case "eq":
                        eq();
                        break;
                    case "ne":
                        ne();
                        break;
                    case "dup":
                        dup();
                        break;
                    case "exch": 
                        exch();
                        break;
                    case "def": 
                        def(); 
                        break;
                    case "if": 
                        only();
                        break;
                    default:
                        defaultMethod(token);
                }
        }
    }
    
    // Method that takes the inputed token if the token is not recognized(a number, command
    // procedure, or boolean) and proccesses that token to perform the apprioprate action 
    private void defaultMethod(Token token) {
        String s = token.getSymbol();
        if (s.startsWith("/")) {
            stack.push(token);
        }
        else {
            Token i = table.get(s);
            if (i.isProcedure()) {
                for (Token j: i.getProcedure()) {
                    processToken(j);
                }
            }
            else {
                processToken(table.get(s));
            }
        }
    }


    // Methods for if command, pops a boolan and token and executes token if boolean is true
    private void only() {
        Token i = stack.pop();
        Token j = stack.pop();
        boolean k = j.getBoolean();
        if (k) {
            if (i.isProcedure()) {
                for (Token m: i.getProcedure()) {
                    processToken(m);
                }
            }
        }
    }

    // Pops two values and adds token value to symbol table representing the string of first token
    private void def() {
        Token i = stack.pop();
        Token j = stack.pop();
        String k = j.getSymbol();
        k = k.substring(1);
        table.add(k, i);
    }

    // Pops the top two values then switches their order, pushing the values back
    private void exch() {
        Token i = stack.pop();
        Token j = stack.pop();
        stack.push(i);
        stack.push(j);
    }

    // Peeks the top value and returns a duplicate of that value to the stack
    private void dup() {
        Token i = stack.peek();
        stack.push(i);

    }
    // Pops top two values off stack and returns T/F to stack based on whether values are not equal
    private void ne() {
        Token i = stack.pop();
        Token j = stack.pop();
        boolean k = i.getNumber() != j.getNumber();
        Token adder = new Token(k);
        stack.push(adder);
        
    }

    // Pops top two values and returns T/F to stack if the second value is less than the first value
    private void lt() {
        Token i = stack.pop();
        Token j = stack.pop();
        boolean k = j.getNumber() < i.getNumber();
        Token adder = new Token(k);
        stack.push(adder);

    }
    
    // Pops top two values and returns T/F to stack if the two values are equal
    private void eq() {
        Token i = stack.pop();
        Token j = stack.pop();
        boolean k = j.getNumber() == i.getNumber();
        Token adder = new Token(k);
        stack.push(adder);

    }
    // Pops top two values and returns T/F to stack if second value is greater than the first value
    private void gt() {
        Token i = stack.pop();
        Token j = stack.pop();
        boolean k = j.getNumber() > i.getNumber();
        Token adder = new Token(k);
        stack.push(adder);

    }

    // Divide top two values in stack(pop first) or two inputs and push new value to top of stack
    private void div() {
        Token i = stack.pop();
        Token j = stack.pop();
        double k = j.getNumber() / i.getNumber();
        Token adder = new Token(k);
        stack.push(adder);
    }

    // Multiply top two values in stack(pop first) or two inputs and push new value to top of stack
    private void mul() {
        Token i = stack.pop();
        Token j = stack.pop();
        double k = j.getNumber() * i.getNumber();
        Token adder = new Token(k);
        stack.push(adder);
        
    }
    // Subtract top two values in stack(pop first) or two inputs and push new value to top of stack
    private void sub() {
        Token i = stack.pop();
        Token j = stack.pop();
        double k = j.getNumber() - i.getNumber();
        Token adder = new Token(k);
        stack.push(adder);
    }

    // Add top two values in stack(pop first) or two inputs and push new value to top of stack
    private void add() {
        Token i = stack.pop();
        Token j = stack.pop();
        double k = j.getNumber() + i.getNumber();
        Token adder = new Token(k);
        stack.push(adder);
    }

    // Remove the last element from the stack 
    private void pop() {
        stack.removeFirst();
    }

    // Method that iterates over each item in stack and prints them out when "pstack" is entered
    private void pstack() {
       for (Token i: stack) {
           System.out.println(i);
       }

    }

    public static void main(String[] args) {
        Reader r = new Reader();
        Interpreter interpreter = new Interpreter();
        interpreter.interpret(r);
    }
}