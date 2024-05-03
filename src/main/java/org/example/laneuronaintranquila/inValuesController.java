package org.example.laneuronaintranquila;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class inValuesController {
    @FXML
    public static int mode;
    @FXML
    private Button btnBack, btnTrain;
    @FXML
    private TextField epoch, error, alpha;

    @FXML
    protected void onClickBack(ActionEvent e) throws IOException {
        SceneController.sceneController.backMainScene(e);
    }

    @FXML
    protected void onClickTrain(ActionEvent e) throws IOException{
        if(epoch != null && error != null && alpha != null){
            if(!Errors.isEmpty(epoch) && Errors.isUniqueNumber(epoch)){
                System.out.println("Bien");
                if(!Errors.isEmpty(alpha) && Errors.isUniqueNumber(alpha)){
                    System.out.println("Bien");
                    if(!Errors.isEmpty(error) && Errors.isUniqueNumber(error)){
                        //Aqui va a la pantalla de entrenamiento de la red
                        if(mode == 0){

                        } else if (mode == 1) {

                        }

                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Informacion");
                        alert.setContentText("Ocurrio un problema con el campo del error\n" +
                                "revise correctamente que no este vacio y que solo contenga\n" +
                                "numeros");
                        alert.show();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setContentText("Ocurrio un problema con el campo del factor de entrenamiento\n" +
                            "revise correctamente que no este vacio y que solo contenga numeros");
                    alert.show();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setContentText("Ocurrio un problema con el campo de las epocas revise\n" +
                        "que el campo no este vacio o contenga solamente numeros");
                alert.show();
            }
        }
    }
}
