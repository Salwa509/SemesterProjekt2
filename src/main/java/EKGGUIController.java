import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;

public class EKGGUIController implements EKGListener {

    public EKGDAO EKGDAO = new EKGDAOImpl();
    public Button run;
    public Button loader;
    public TextField PatientID;
    public Polyline ekgLinje;
    public TextField patientID;
    double x = 0;

    public void EKGMeasurements(ActionEvent actionEvent) {
        ProducerConsumerThread producerConsumerThread = new ProducerConsumerThread(this);
        Thread thread = new Thread(producerConsumerThread);
        thread.start();
    }


    public void next(ActionEvent actionEvent) throws IOException {
        Parent secondPaneLoader = FXMLLoader.load(getClass().getResource("/loader.fxml"));
        Scene secondScene = new Scene(secondPaneLoader);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
        primaryStage.show();
    }

    @Override
    public void notify(LinkedList<EKGDTO> ekgdtos) {
        Platform.runLater(() -> {
            LinkedList<Double> ekg = new LinkedList<>();
            for (int i = 0; i < ekgdtos.size(); i++) {
                EKGDTO ekgDTO = ekgdtos.get(i);
                ekg.add(x);
                ekg.add((1500 - ekgDTO.getEKGMeasurements()) / 10);
                ekgDTO.setEKGPatientID(Integer.parseInt(PatientID.getText()));
                x++;
            }
            if (x > 600) {
                x = 0;
                ekgLinje.getPoints().clear();
            }
            ekgLinje.getPoints().addAll(ekg);
        });
        new Thread(() -> {
            EKGDAO.saveBatch(ekgdtos);
        }).start();
    }
}