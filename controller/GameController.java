package nimgameui.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import nimgameui.model.NimGameUI;

import java.io.IOException;

public class GameController {
    @FXML private Label gameInfo;
    @FXML private FlowPane stoneImageSpace;
    @FXML private TextField removeText; // stone to be removed by each player
    @FXML private Pane root; // just to take previous root, to prevent grid pane stack on.
    private NimGameUI game; // game instance pass from first controller
    private NimPlayerRecord playerRecord;

    public void setGame(NimGameUI game) {
        this.game = game;
        gameInfo.setText(game.displayInfo());
        initializeStone();
    }


    public void setRoot(Pane root) {
        this.root = root;
    }

    public void initializeStone(){
        int stoneNum = game.getStoneNum();
        Image image = new Image("file:src/nimgameui/resources/stone.png"); // load the image of stone
        ImageView[] stoneArray = new ImageView[stoneNum];
        for (int i = 0; i < stoneNum; i++) {
            stoneArray[i] = new ImageView(image);
            stoneArray[i].setFitHeight(40);
            stoneArray[i].setFitWidth(40);
        }
        stoneImageSpace.getChildren().setAll(stoneArray);
    }

    // turn ends
    public void removeConfirm(MouseEvent mouseEvent) throws IOException {
        int stoneRemoved = Integer.parseInt(removeText.getText());
        game.removeStone(stoneRemoved);
        removeText.clear();
        gameInfo.setText(game.displayInfo());

        // stone disappear
        int stoneNum = game.getStoneNum();
        for (int j = stoneNum; j < stoneNum + stoneRemoved; j++) {
            // Fade object for stones
            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.millis(1000));
            fade.setFromValue(10);
            fade.setToValue(0.1);
            fade.setNode(stoneImageSpace.getChildren().get(j));
            fade.play();
        }

        if (game.isGameFinished()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/nimgameui/view/rank_scene.fxml"));
            GridPane newRoot = loader.load(); // The load must be called before getting constructor.
            RankController rankController = loader.<RankController>getController();
            rankController.setRecord(playerRecord);
            rankController.setGame(game);
            rankController.setRoot(root);
            // The root must be reset after game controller receive the game instance. Use children of the root everytime to
            // prevent gridPane stack on each other infinitely.
            root.getChildren().setAll(newRoot);
        }
    }

    public void setRecord(NimPlayerRecord playerRecord){
        this.playerRecord=playerRecord;
    }


}

