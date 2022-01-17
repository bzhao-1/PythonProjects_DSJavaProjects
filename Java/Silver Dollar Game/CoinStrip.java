/*
 * Name: Ben Zhao & Chris Elliott 
 * Email: zhaob@carleton.edu, elliottc2@carleton.edu
 * Description: This program runs the Silver Dollar Game! This game involves a strip being created, divided with individual spots.
 The silver dollar coins are randomly placed along with strip, and the goal is to move all n coins to the leftmost n spots of the strip. Coins can move only to the left, 
 no coin may pass another, and no spot may hold more than one coin. 
 The last person to move is the winner. 
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;



public class CoinStrip implements TwoPlayerGame {

    /*
     * A private instance variable for keeping track of coins here.
     * The lab writeup suggests using an array of integers, where each element
     * indicates the position of a coin:
     */
    private int[] coins;

    /**
     * Randomly choose a number of coins:
     * 50% chance of 3, 25% chance of 4, 12.5% chance of 5, etc.
     * Hint: StdRandom.bernoulli() returns true 50% of the time
     * 
     * Randomly position the coins.
     */
    public CoinStrip() {
        int n = 3;
        if (StdRandom.bernoulli(.5)) {
            n = 3;
        }
        else if (StdRandom.bernoulli(.5)) {
            if (StdRandom.bernoulli(.5)) { 
                n = 4;
            }
            else if (StdRandom.bernoulli(.5)){
                if (StdRandom.bernoulli(.5)) {
                    n = 5;
                }
                else if (StdRandom.bernoulli(.5)){
        
                    n = 6;
                }
                
                
            }
        }

         // use StdRandom to randomly determine the number of coins
    // then intialize coins to hold that many coin positions:

    // then randomly position the coins
    // (StdRandom.uniform(1, x) will give you a random integer between 1 and x)

        coins = new int[n];

        for(int i = 0; i < coins.length; i++){
            int a;
            a = StdRandom.uniform(1, coins.length * 2);
            if(i == 0){
                coins[i] = a;
              }
            else if(i >= 1){
                while(arrayContains(coins, a) == true){
                a = StdRandom.uniform(1, coins.length * 2);
                }
                coins[i] = a;
                
            }
        } 
        Arrays.sort(coins);
   

    }

    
    // Constructor
    public CoinStrip(int[] coins) {
        this.coins = coins;
    }

    /**
     * Returns the number of coins on the CoinStrip game board.
     * 
     * @return the number of coins on the CoinStrip game board.
     */
    public int getNumCoins() {
        return coins.length;
    }

    /**
     * Returns the 0-indexed location of a specific coin. Coins are
     * also zero-indexed. So, if the CoinStrip had 3 coins, the coins
     * would be indexed 0, 1, or 2.
     * 
     * @param coinNum the 0-indexed coin number
     * @return the 0-indexed location of the specified coin on the CoinStrip
     */
    public int getCoinPosition(int coinNum) { 
        return coins[coinNum];
         
    }
    
    // Helper Method for Ensuring Board has No repeats
    public static boolean arrayContains(int[] coins, int coin){
        for (int i = 0 ; i < coins.length; i++){
            if (coin == coins[i]){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the specified move follows the rules
     * 
     * @param resource which coin is moving (0-indexed)
     * @param units    the number of spaces to move the coin
     * @return true if the move follows the rules, false otherwise
     */
    @Override
    public boolean isValidMove(int resource, int units) {
        if(units <= 0){
            return false;
        }
        if(resource == 0){
            if((coins[resource]- units) >= 0){
                return true;
            }
            else if((coins[resource]- units) < 0){
                return false;
            }
        }
        if(resource >= 1){
            if ((coins[resource-1] + 1) < coins[resource] && coins[resource] - units > coins[resource-1]){
                return true;

            }
            else if ((coins[resource-1] + 1) == coins[resource] || coins[resource] - units <= coins[resource-1]){
                return false;
            }
        }
        return false;
    }
            
        

    /**
     * Makes the specified move
     * Assumes the move is valid
     * 
     * @param resource which coin is moving (0-indexed)
     * @param units    the number of spaces to move the coin
     */
    @Override
    public void makeMove(int resource, int units) {
        if (units >= 0){
            coins[resource] = coins[resource] - units;
            System.out.println(Arrays.toString(coins));
        }
        else {
            System.out.println("Invalid move");
        }
        

    }

    /**
     * @return true if the game is over (coins are all the way to the left)
     */
    @Override
    public boolean isGameOver() {
        if(coins[coins.length-1] == (coins.length - 1)){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return A String representation of the game board with the coins 0-indexed
     */
    @Override
    public String toString() {
        // Hint: build up the string version of the board using concatenation (+)
        /* your code here */
        System.out.println(Arrays.toString(coins));
        int curCoin = 0;
        for(int i =0; i <= coins[(coins.length -1)]; i++){
            if(coins[curCoin] == i){
                System.out.print("O");
                curCoin++;
            }
            else{
                System.out.print("-");
            }
            
        }
            

        return "";
    }

    public static void main(String[] args) {
        CoinStrip game = new CoinStrip();
        int turn = 0;

        // repeatedly take turns until the game is over
        while (!game.isGameOver()) {
            System.out.println(game);

            System.out.print("Enter player " + turn + "'s move: ");
            int coin = StdIn.readInt();
            int spaces = StdIn.readInt();

            // repeatedly ask for a move until a valid move is entered
            while (!game.isValidMove(coin, spaces)) {
                System.out.println("\nInvalid move! Try again");
                System.out.println(game);

                System.out.print("Enter player " + turn + "'s move: ");
                coin = StdIn.readInt();
                spaces = StdIn.readInt();
            }

            game.makeMove(coin, spaces);
            turn = (turn + 1) % 2; // will flip turn between 0 and 1
        }

        System.out.println(game);
        System.out.println("Player " + (turn + 1) % 2 + " wins!");
    }
}
