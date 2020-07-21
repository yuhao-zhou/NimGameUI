package nimgameui.controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import nimgameui.model.NimGameUI;
import nimgameui.model.NimPlayer;
import java.io.IOException;

public class EntrySceneController {
    @FXML private Pane root;
    @FXML private Label rules;
    @FXML private Label welcome;
    @FXML private Label numberOfStoneLabel;
    @FXML private TextField numberOfStoneText;
    @FXML private Label maxOfStoneLabel;
    @FXML private TextField maxOfStoneText;
    @FXML private Label player1NameLabel;
    @FXML private TextField player1NameText;
    @FXML private Label player2NameLabel;
    @FXML private TextField player2NameText;
    private NimGameUI game;
    private NimPlayerRecord playerRecord;

    // set root from NimMain, keep top root as dummy.
    public void setRoot(Pane root) {
        this.root = root;
    }

    public void initialize(){
        // populate all label
        welcome.setText("Welcome to the game of Nim!");
        rules.setText("Players remove appropriate number of stone from the table. The person remove the last stone lose! ");
        numberOfStoneLabel.setText("Initial number of stone");
        numberOfStoneText.setPromptText("100 maximum");
        maxOfStoneLabel.setText("Max number of stone taken each turn");
        maxOfStoneText.setPromptText("10 maximum");
        player1NameLabel.setText("Name of player 1");
        player2NameLabel.setText("Name of player 2");


        // To move the mouse out of any textField. It is required to do this after scene setup, as below.
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                root.requestFocus();
            }
        });
    }

    public void startGame(MouseEvent mouseEvent) throws IOException {
        String player1Name = player1NameText.getText();
        String player2Name = player2NameText.getText();
        int stoneNum = Integer.parseInt(numberOfStoneText.getText());
        int stoneMax = Integer.parseInt(maxOfStoneText.getText());

        // load the player record
        playerRecord = new NimPlayerRecord();

        // If player dont exist, create them. Otherwise, refer to them
        NimPlayer player1, player2;
        if(playerRecord.checkName(player1Name) == -1){
            player1 = playerRecord.addPlayer(player1Name, "human");
        }
        else{
            player1 = playerRecord.getPlayer(player1Name);
        }

        if(playerRecord.checkName(player2Name) == -1){
            player2 = playerRecord.addPlayer(player2Name, "human");
        }
        else{
            player2 = playerRecord.getPlayer(player2Name);
        }

        // now set the game
        game = new NimGameUI(stoneNum,stoneMax,player1,player2);
        runGame();
    }

    public void runGame() throws IOException {
        // The loader created allows the extraction of second controller created by loading FXML.
        // this allows the second controller to obtain the game instance.

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nimgameui/view/game_scene.fxml"));
        GridPane newRoot = loader.load(); // The load must be called before getting constructor.
        GameController gameController = loader.<GameController>getController();
        gameController.setRecord(playerRecord);  // The player record is also passed around to allow stats showing in Rank
        gameController.setGame(game);
        gameController.setRoot(root);
        // Dummy top root passed to children, and appended with new top child.
        root.getChildren().setAll(newRoot);
    }

}
