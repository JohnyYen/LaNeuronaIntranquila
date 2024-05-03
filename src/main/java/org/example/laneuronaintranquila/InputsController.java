package org.example.laneuronaintranquila;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;

public class InputsController {
    @FXML
    protected TextField n, ls, as, ap, lp;
    @FXML
    protected void onClickBack(ActionEvent e) throws IOException {
        SceneController.sceneController.backMainScene(e);
    }

    @FXML
    protected void onClickTrain(ActionEvent e) throws IOException {
        boolean isCorrect = false;
        ArrayList<Float> inputs = new ArrayList<Float>();
        if(n != null && ls != null && as != null && ap != null && lp != null){
            if(Errors.isFloatNumber(ls.getText()) && !Errors.isEmpty(ls)){
                System.out.println("Hello");
                if(Errors.isFloatNumber(as.getText()) && !Errors.isEmpty(as)){
                    if(Errors.isFloatNumber(lp.getText()) && !Errors.isEmpty(lp)){
                        if(Errors.isFloatNumber(ap.getText()) && !Errors.isEmpty(ap)){
                            if(Errors.isAllLetter(n)&&!Errors.isEmpty(n) && Errors.isFlower(n.getText())){
                                float f = Float.parseFloat(as.getText());
                                float a = Float.parseFloat(ap.getText());
                                float x = Float.parseFloat(ls.getText());
                                float z = Float.parseFloat(lp.getText());
                                inputs.add(f);
                                inputs.add(a);
                                inputs.add(x);
                                inputs.add(z);
                                isCorrect = true;
                            }
                            else{
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Asegure que el campo de las flores no este vacio\n" +
                                        "o que haya escrito Iris-setosa o Iris-virginica o Iris-versicolor\n");
                            }
                        }
                    }
                }
            }
        }

        if(isCorrect){
            SceneController.sceneController.changeInputsScene(e);
        }
    }
}
