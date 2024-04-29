package org.example.laneuronaintranquila;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button btnCreate, btnClose, btnTrain;

    @FXML
    protected void onClickCreateNetwork(ActionEvent e) throws IOException {
        SceneController.sceneController.changeCreateNetwork(e);
    }

    @FXML
    protected void onClickInputs(ActionEvent e) throws IOException{
        SceneController.sceneController.changeInputsScene(e);
    }
    @FXML
    protected void onClickClose(ActionEvent e) throws IOException{
        SceneController.sceneController.closeAplication(e);
    }

    @FXML
    protected void onClickFilesRead(ActionEvent e) throws IOException{
        SceneController.sceneController.changeFilesView(e);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}