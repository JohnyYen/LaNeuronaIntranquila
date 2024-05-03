package org.example.laneuronaintranquila;

import Logica.NeuralNetwork.NNM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Optional;

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Estas segur@ que desea salir?");
        Optional<ButtonType> re = alert.showAndWait();
        if(re.isPresent() && re.get() == ButtonType.OK){
            NNM.instanciate.saveNetwork();
            SceneController.sceneController.closeAplication(e);
        }
    }

    @FXML
    protected void onClickFilesRead(ActionEvent e) throws IOException{
        SceneController.sceneController.changeFilesView(e);
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onClickInput(ActionEvent e) throws IOException{
        SceneController.sceneController.changeInputScene(e);
    }
}