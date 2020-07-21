package nimgameui.model;

import java.io.Serializable;
import java.util.Scanner;

public abstract class NimPlayer implements Serializable {
    private String username;
    private int numGamePlayed;
    private int numGameWon;
    private static final long serialVersionUID = 1; // remove after test

    public NimPlayer(String username){
        this.username = username;
    }

    public NimPlayer(){
    }

    public String getUsername() {
        return username;
    }


    /**
     * reset numGamePlayed and numGameWon for NimPlayer instance
     */
    public void reset() {
        numGamePlayed = 0;
        numGameWon = 0;
    }

    public void display() {
        System.out.println(username + "," + ","
                + numGamePlayed + " games," + numGameWon + " wins");
    }

    public String getWinRatio() {
        if (numGamePlayed == 0){
            return "0";
        }
        return String.format("%.2f", (((double) numGameWon)/ numGamePlayed * 100));
    }

    public int getNumGamePlayed() {
        return numGamePlayed;
    }

    abstract int removeStone(int stoneMax,int stoneNum, Scanner sc);

    abstract String removeStoneAdvance(boolean[] available, String lastMove, Scanner sc);

    /**
     * This player wins. Update numGameWon
     */
    public void win(){
        numGameWon++;
        System.out.println(this.username+ " wins!");
    }

    /**
     * This player joins the game. Update numGamePlayed
     */
    public void play() {
        numGamePlayed++;
    }
}
