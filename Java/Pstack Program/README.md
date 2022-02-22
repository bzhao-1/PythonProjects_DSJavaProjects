# Created by Ben Zhao PostScript Implementation
29 January 2022

The program implements a small portion of a stack-based language called PostScript. 
PostScript is a file format often used with printers. 
In fact, the file you send to your printer is a program that instructs your printer to draw the appropriate output. 
PostScript is stack-based: integral to the language is an operand stack. Each operation that is executed pops its operands from the stack and pushes on a result.

The code in interpreter.java implements public methods of token.java(to process each token), reader.java(to read the tokens), and SymbolTable.java to process escaped tokens
Examples of Implementation: (Sourced from Instructions by Professor Aaron Bauer Carleton College CS 201 Data Structures Winter 2022)
The following program computes 1 + 1:
1 1 add pstack
Every item you type in is a token. Tokens include numbers, booleans, or symbols. Here, we’ve typed in two numeric tokens, followed by two symbolic tokens. 
Each number is pushed on the internal stack of operands. When the add token is encountered, it causes PostScript to pop off two values and add them together. 
The result is pushed back on the stack. (Other mathematical operations include sub, mul, and div.) The pstack command causes the entire stack to be printed, one value per line, from top to bottom. 
In this case prints 2.0.
Provided the stack contains at least one value, the pop operator can be used to remove it. Thus, the following computes 2 and prints nothing:
1 1 add pop pstack
The following program computes 1 + 3 * 4:
1 3 4 mul add pstack
The result printed here, 13, is different than what is computed by the following program:
1 3 add 4 mul pstack
In this case the addition is performed first, printing 16.
Some operations simply move values about. You can duplicate values—the following squares the number 10.1 using dup to push a second 10.1 onto the stack:
10.1 dup mul pstack pop
The exch operator to exchange two values, computing 1 − 3 with this program:
3 1 exch sub pstack pop
Comparison operations compute logical values:
1 2 eq pstack pop
tests for equality of 1 and 2, and puts false on the stack to be printed. The program
1 1 eq pstack pop
yields a value of true.
Symbols are defined using the def operation. To define a symbolic value we specify a escaped symbol (preceded by a slash) and the value, all followed by the operator def:
/pi 3.141592653 def
Once we define a symbol, we can use it in computations:
/radius 1.6 def
pi radius dup mul mul pstack pop
computes and prints the area of a circle with radius 1.6. After the pop, the stack is empty.
