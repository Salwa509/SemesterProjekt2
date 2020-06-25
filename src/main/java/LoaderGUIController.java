import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LoaderGUIController {


    public TextField Felt;
    public Button Søg;
    public TextArea målinger;
    public Button tilbage;
    public Polyline ekgLinje;

    public void loadData(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            EKGDAO EKGDAO = new EKGDAOImpl();
            List<EKGDTO> ekgDTO = EKGDAO.load(Felt.getText());
            LinkedList<Double> EKG = new LinkedList<>();
            ekgLinje.getPoints().clear();
            for (int i = 0; i < ekgDTO.size(); i++) {
                EKG.add(Double.valueOf(i));
                EKG.add((1500 - ekgDTO.get(i).getEKGMeasurements()) / 10);
            }
            ekgLinje.getPoints().addAll(EKG);
        });
    }

    public void Back(ActionEvent actionEvent) throws IOException {
        Parent secondPaneLoader = FXMLLoader.load(getClass().getResource("/EKGGUI.fxml"));
        Scene secondScene = new Scene(secondPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
        primaryStage.show();

    }
}

