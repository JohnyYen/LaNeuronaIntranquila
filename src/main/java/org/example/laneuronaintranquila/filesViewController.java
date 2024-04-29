package org.example.laneuronaintranquila;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.io.FileReader;
import java.io.IOException;

public class filesViewController {
    @FXML
    protected Button btnBack, btnRead;

    @FXML
    protected TableView<Object> table;

    private ObservableList<Object> list;
    @FXML
    protected void onClickBack(ActionEvent e) throws IOException {
        SceneController.sceneController.backMainScene(e);
    }

    @FXML
    protected void onClickRead(){
        table.setItems(list);
    }
}
