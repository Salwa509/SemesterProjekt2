import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EKGApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EKGGUI.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();

    }
}
