/*
 * Name: Ben Zhao 
 * Email: zhaob@carleton.edu
 * Description: This program analyzes the letter frequencies(of given k-length) in text documents
 * and then generates new text documents based on those frequencies. Our program implements the 
 * algorithm developed by Claude Shannon in 1948. 
 */

// Copyright (c) 2022 Ben Zhao

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.


import java.util.HashMap;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;


public class TextGenerator {
    // Frequency Table Hashmap field
    private HashMap<String, LetterInventory> frequencies = new HashMap<>();
    private int nums; // Integer that is the same as level k
    /**
     * Construct a new text generator by performing level k analysis on the text in filename
     * @param k The level of analysis
     * @param filename The name of the input file
     */
    public TextGenerator(int k, String filename) {
       
        // open the file
        In file = new In(filename);
        String current = "";
        String prevCurrent = current;
        nums = k;

        while (file.hasNextChar()) {
            // process the file character-by-character
            char nextChar = file.readChar();
            current += nextChar;
            if (current.length() == k) {
                if (!frequencies.containsKey(prevCurrent)) {
                    frequencies.put(prevCurrent, new LetterInventory());
                }
                frequencies.get(prevCurrent).addCount(nextChar);
                prevCurrent = current;
                current = current.substring(1);
            } 
        }
            
    }
    public class LetterInventory {
        // Letter Inventory HashMap
        private  HashMap<Character, Integer> occurences = new HashMap<>();
        // Construct a new Letter Inventory hashmap for each new string in frequency table hashmap
        public LetterInventory() {
            occurences = new HashMap<Character, Integer>();
        }
        // Returns the Letter Inventory Hashmap
        public HashMap<Character, Integer> getFrequency() {
            return occurences;
        }
        // Add Letter to Letter Inventory Hashmap as new key or update value if already in hashmap
        public void addCount(char c) {
            if (occurences.containsKey(c)) {
                occurences.put(c, occurences.get(c) + 1);
            }
            else {
                occurences.put(c, 1);
            }
        }
        // Randomly chooses nextletter when building new text document, weighted by value of letter
        public char getLetter() {
            int total = 0;
            for (int value: occurences.values()) {
                total += value;
            }
            double threshold = StdRandom.uniform();
            for (HashMap.Entry<Character, Integer> entry : occurences.entrySet()) {
                double count = entry.getValue();
                char letter = entry.getKey();
                threshold = threshold - count / total;
                if (threshold < 0) {
                    return letter;
                }
            }
            return 0;
        }

    }

    /**
     * Generate and return a random string based on the input text
     * @param length The number of characters worth of random text to generate
     * @return A string of length characters produced by level k frequency analysis
     */
    public String generate(int length) {
        Object[] keys = frequencies.keySet().toArray();
        String generate = (String) keys[StdRandom.uniform(keys.length)];
        int count = 0;
        while (generate.length() < length) {
            if (count == 0 || generate.length() == 1) {
                generate += frequencies.get(generate).getLetter();
            }
            else if (generate.length() > 1) {
                generate += frequencies.get(generate.substring(generate.length() - nums,
                 generate.length())).getLetter();
            }
            count++;
            // Make sure to only look at first k letters of generate
        }
     
        return generate;
    }

    public static void main(String[] args) {
        TextGenerator gen = new TextGenerator(2, "small.txt");
        System.out.println(gen.generate(10));
    }
}
