package org.example.laneuronaintranquila;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class TrainViewController {

    @FXML
    protected void onCLickBack(ActionEvent e) throws IOException{
        SceneController.sceneController.backMainScene(e);
    }
}
