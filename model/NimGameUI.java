package nimgameui.model;

public class NimGameUI {
    private int stoneMax; // Maximum of stone to take each time
    private int stoneNum; // Stone left on table
    private NimPlayer player1;
    private NimPlayer player2;
    private boolean player1Turn;
    private boolean gameFinished;
    private int initialStone; // keep this for restart game

    public NimGameUI(int stoneNum, int stoneMax, NimPlayer player1, NimPlayer player2){
        this.stoneNum = stoneNum;
        this.stoneMax = stoneMax;
        this.player1 = player1;
        this.player2 = player2;
        this.initialStone = stoneNum;
        player1Turn = true;
        gameFinished = false;

        // update player numGamePlayed
        player1.play();
        player2.play();

        System.out.println("game created with " + player1.getUsername() + " and  " + player2.getUsername());
    }

    public String displayInfo(){
        return "There are " +  stoneNum + " stones on the table, " +
               ( player1Turn?player1.getUsername():player2.getUsername()) + ", remove how many? ";
    }

    public int getStoneNum() {
        return stoneNum;
    }

    // remove stone and determine if game finished, who win.
    public void removeStone(int stoneRemoved){
        stoneNum -= stoneRemoved;
        player1Turn = !player1Turn;
        if (stoneNum<=0){
            gameFinished = true;
            if (player1Turn){
                player1.win();
            }
            else{
                player2.win();
            }
        }
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public String displayWinner(){
        return  "Congrat! " +
                ( player1Turn?player1.getUsername():player2.getUsername()) + " win! ";
    }

    public void restartGame(){
        stoneNum = initialStone;
        gameFinished = false;
        player1Turn = true;
    }
}

