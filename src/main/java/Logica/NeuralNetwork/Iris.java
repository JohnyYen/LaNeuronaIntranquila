package Logica.NeuralNetwork;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase usada para el entrenamiento de la red neuronal
 */
public class Iris implements Serializable {
    private float widthLabel, heightLabel;
    private float widthSepal, heightSepal;
    private static final long serialVersionUID = 4L;
    private String typeFlower;

    public Iris(float anchoPetalo, float largoPetalo, float ancho, float largo, String tipo){
        this.widthSepal = ancho;
        this.heightSepal = largo;
        this.heightLabel = largoPetalo;
        this.widthLabel = anchoPetalo;
        this.typeFlower = tipo;
    }
    public String getTypeFlower(){return this.typeFlower;}
    public double getHeightlabel(){return this.heightLabel;}
    public double getWidthLabel(){return this.widthLabel;}
    public double getHeight(){return this.heightSepal;}
    public double getWidth(){return this.widthSepal;}
    public ArrayList<Float> getArrayData(){
        ArrayList<Float> list = new ArrayList<Float>();
        list.add(heightLabel);
        list.add(widthLabel);
        list.add(heightSepal);
        list.add(widthSepal);
        return list;
    }

}
