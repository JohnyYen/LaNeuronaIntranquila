package org.example.laneuronaintranquila;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class createNetworkController {
    @FXML
    protected Button btnBack, btnAccepted;
    @FXML
    protected TextField name, layer, neurons;

    @FXML
    protected void onClickBack(ActionEvent e) throws IOException {
        SceneController.sceneController.backMainScene(e);
    }

    @FXML
    protected void onClickAccepted(ActionEvent e) throws IOException {
        boolean isAccepted = false;
        if(name != null && layer != null && neurons != null){
            if(!Errors.isEmpty(name) && Errors.isAllLetter(name)){
                System.out.println("Bien");

                if(!Errors.isEmpty(layer) && Errors.isAllNumber(layer) && Errors.isUniqueNumber(layer)){
                    System.out.println("Bien2");
                    if(!Errors.isEmpty(neurons) && Errors.isAllNumber(neurons)){
                        System.out.println("Listo");
                        String numbers = neurons.getText().replaceAll(",", " ");
                        if(Errors.isEqualsLenght(Integer.parseInt(layer.getText()), numbers)){
                            int i = 0, j = 0;
                            int[] neur = new int[Integer.parseInt(layer.getText())];
                            while(neur.length != j){
                                if(Character.isDigit(numbers.charAt(j)))
                                    neur[i++] = Integer.parseInt(""+numbers.charAt(j));
                                j++;
                            }

                            isAccepted = true;
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Informacion");
                            alert.setContentText("La cantidad de capas introducidas es diferente a la\n" +
                                    "cantidad de neuronas introducidas");
                            alert.show();
                        }
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Informacion");
                        alert.setContentText("Ocurrio un error con el campo de la cantidad\n" +
                                "de neuronas por capa, revise que no este vacio o que tenga\n" +
                                "unicamente valores numericos");
                        alert.show();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacion");
                    alert.setContentText("Ocurrio un error con el campo de la cantidad de capas\n" +
                            "revise que este introduciendo numeros y que el campo no es vacio");
                    alert.show();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacion");
                alert.setContentText("Ocurrio un error con el nombre de la red, revise\n" +
                        "que el campo no este vacio o que tenga valores correctos");
                alert.show();
            }
        }
//
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Informacion");
//        alert.setContentText("Ocurrio un error con el nombre de la red, revise" +
//                "que el campo no este vacio o que tenga valores correctos");
        if(isAccepted)
            SceneController.sceneController.backMainScene(e);
    }
}
