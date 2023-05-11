import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {

        launch();
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("DesignUI.fxml")));
        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
