package nimgameui.model;
import java.io.Serializable;
import java.util.Scanner;

public class NimHumanPlayer extends NimPlayer implements Serializable {
    private static final long serialVersionUID = 2; // remove after test

    public NimHumanPlayer(String username){
        super(username);
    }

    public NimHumanPlayer(){
    }

    @Override
    public int removeStone(int stoneMax,int stoneNum, Scanner sc) throws IllegalArgumentException{
        System.out.println(this.getUsername() + "'s turn - remove how many?");
        int remove = sc.nextInt();
        if ((remove > Math.min(stoneMax,stoneNum)) || (remove < 1)){
            throw new IllegalArgumentException();
        }
        return remove;
    }

    @Override
    public String removeStoneAdvance(boolean[] available, String lastMove, Scanner sc) {
        System.out.println(this.getUsername() + "'s turn - which to remove?");
        return sc.nextLine();
    }
}
