package nimgameui.view;


import com.sun.webkit.BackForwardList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nimgameui.controller.EntrySceneController;
import nimgameui.controller.GameController;

public class NimMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("NimGame");
        StackPane root = new StackPane(); // keep all later scene as top children of the invariant dummy root here.
        root.setAlignment(Pos.CENTER);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/nimgameui/view/entry_scene.fxml"));
        GridPane newRoot = loader.load(); // The load must be called before getting constructor.
        EntrySceneController entrySceneController = loader.<EntrySceneController>getController();
        entrySceneController.setRoot(root);
        root.getChildren().setAll(newRoot);

        Scene scene = new Scene(root,600,600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}