package nimgameui.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import nimgameui.model.NimGameUI;
import nimgameui.model.NimPlayer;

import java.io.IOException;

public class RankController {
    @FXML TableView rankTable;
    @FXML TableColumn username;
    @FXML TableColumn gamePlayed;
    @FXML TableColumn winRatio;
    @FXML private Label gameInfo;
    private Pane root; // just to take previous root, to prevent grid pane stack on.
    private NimGameUI game; // game instance pass from first controller
    private NimPlayerRecord playerRecord;

    public void setGame(NimGameUI game) {
        this.game = game;
        gameInfo.setText(game.displayWinner());

        //save previous game record
        playerRecord.save();

        //load data
        ObservableList<NimPlayer> data = rankTable.getItems();
        for (NimPlayer p:playerRecord.getPlayerList()){
            if (p != null){
                data.add(p);
            }
        }


        // Initialize the person table with the three columns.
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        gamePlayed.setCellValueFactory(new PropertyValueFactory<>("numGamePlayed"));
        winRatio.setCellValueFactory(new PropertyValueFactory<>("winRatio"));
    }


    public void setRoot(Pane root) {
        this.root = root;
    }

    public void newGame(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nimgameui/view/entry_scene.fxml"));
        GridPane newRoot = loader.load();
        EntrySceneController entrySceneController = loader.<EntrySceneController>getController();
        entrySceneController.setRoot(root);
        root.getChildren().setAll(newRoot);

    }

    public void again(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nimgameui/view/game_scene.fxml"));
        GridPane newRoot = loader.load(); // The load must be called before getting constructor.
        GameController gameController = loader.<GameController>getController();
        game.restartGame(); // take the stone back
        gameController.setGame(game);
        gameController.setRoot(root);
        gameController.setRecord(playerRecord);
        root.getChildren().setAll(newRoot);
    }

    public void setRecord(NimPlayerRecord playerRecord){
        this.playerRecord=playerRecord;
    }
}
