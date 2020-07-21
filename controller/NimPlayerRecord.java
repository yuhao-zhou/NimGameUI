package nimgameui.controller;

import nimgameui.model.NimHumanPlayer;
import nimgameui.model.NimPlayer;

import java.io.*;
import java.util.Scanner;

public class NimPlayerRecord {
    private NimPlayer[] playerList = new NimPlayer[100];
    private int numPlayer = 0; // number of player stored
    private File path = new File ("src/nimgameui/model", "players.dat");


    public NimPlayerRecord(){
        load(path);
    }
    public int checkName(String username){
        for (int i=0;i<numPlayer;i++){
            if (playerList[i].getUsername().equals(username)){
                return i;
            }
        }
        return -1;
    }

    public NimPlayer getPlayer(String username){
        return playerList[checkName(username)];
    }

    /**
     * Add a newly created NimPlayer to playerList if username does not previous exist,
     * otherwise return a message
     * @param username username input
     * @param playerType add either a human or AI player
     * */
    public NimPlayer addPlayer(String username, String playerType){
        // check return index from checkName()
        if (playerType.equals("human")){
            playerList[numPlayer] = new NimHumanPlayer(username);
        }
        numPlayer++;
        return playerList[numPlayer-1];
    }

    /**
     * Remove NimPlayer with given username from playerList if username exist in playerList,
     * otherwise return a message.
     * @param username username to be removed
     * @param sc scanner object passed from main
     */
    public void removePlayer(String username, Scanner sc){
        // check return index from checkName()
        if (username == null){
            System.out.println("Are you sure you want to remove all players? (y/n)");
            if (sc.next().equals("y")){
                playerList = new NimPlayer[100]; // reset to empty list
                numPlayer = 0;
            }
            sc.nextLine(); // throw garbage
        }
        else if (checkName(username) == -1){
            System.out.println("The player does not exist.");
        }
        else{
            for (int i=checkName(username);i<numPlayer-1;i++){  // left shift all player
                playerList[i] = playerList[i+1];
            }
            numPlayer --;
        }
    }

    public void load(File path){
        ObjectInputStream loadFile = null;
        try{
            try {
                loadFile = new ObjectInputStream(new FileInputStream(path));
                while (true) {
                    playerList[numPlayer] = (NimPlayer) loadFile.readObject();
                    numPlayer ++;
                }
            }
            catch (EOFException e){
                loadFile.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            // if no file exist for loading, then nothing need to be done.
        }
    }

    public void save(){
        ObjectOutputStream saveFile = null;
        try{
            saveFile = new ObjectOutputStream(new FileOutputStream(path));
            for (int i=0;i<numPlayer;i++){
                saveFile.writeObject(playerList[i]);
            }
            saveFile.close();
        }
        catch (IOException e){
            System.out.println(e); // This line should not be reached ideally
        }
        System.out.println();
    }

    public NimPlayer[] getPlayerList() {
        return playerList;
    }
}
