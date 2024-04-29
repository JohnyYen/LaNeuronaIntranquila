package org.example.laneuronaintranquila;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.Objects;

public class Errors {

    public  static boolean isEmpty(TextField field){
        boolean empty = false;
        String text = field.getText();
        text = text.replaceAll(" ", "");
        if(text.equalsIgnoreCase(""))
            empty = true;
        return empty;
    }

    public static boolean isEqualsLenght(int lenght, String text){
        text = text.replaceAll(",","");
        int count = 0;
        for(int i = 0; i < text.length(); i++)
            if(Character.isDigit(text.charAt(i)))
                count++;
        return lenght == count;
    }

    public static boolean isAllNumber(TextField field){
        String text = field.getText().replaceAll(",", " ");
        boolean isCorrect = true;
        System.out.println(text);
        for(int i = 0; i < text.length() && isCorrect; i++)
            if(!Character.isDigit(text.charAt(i)) && !Character.isSpaceChar(text.charAt(i)))
                isCorrect = false;
        return isCorrect;
    }

    public static boolean isUniqueNumber(TextField field){
        String text = field.getText();
        boolean correct = true;
        try{
            int number = Integer.parseInt(text);
            System.out.println(number);
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("El valor introducido no era un numero");
            correct = false;
            alert.show();
        }
        return correct;
    }
    public static boolean isAllLetter(TextField field){
        String text = field.getText().replaceAll(" ", "");
        boolean isCorrect = true;
        for(int i = 0; i < text.length(); i++)
            if(!Character.isAlphabetic(text.charAt(i)))
                isCorrect = false;

        return isCorrect;
    }

    public static boolean isCorrectNumberNeurons(int layers, TextField field){
        String text = field.getText().replaceAll(",", " ");
        int count = 0;
        for(int i = 0; i < text.length(); i++)
            if(Character.isDigit(text.charAt(i)))
                count++;

        return count == layers;
    }

    public static boolean isNull(Object object){
        return object == null;
    }
}
