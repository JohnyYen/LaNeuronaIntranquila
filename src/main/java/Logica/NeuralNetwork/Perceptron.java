package Logica.NeuralNetwork;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Clase Neurona del perceptron usada para generar resultados en la red
 */
public class Perceptron implements Serializable {

    private static final long serialVersionUID = 5L;
    private float bias, weightBias;
    private float umbral;
    private float lastOutput;
    private float error;
    private int layer;

    //CONSTRUCTORES

    Perceptron(int layer){
        this.umbral = 1;
        this.bias = 1;
        this.layer = layer;
        weightBias = 1;
    }
    Perceptron(float bias, float umbral){
        this.bias = bias;
        this.umbral = umbral;
    }


    public boolean activatePerceptron(ArrayList<Float> weights, ArrayList<Float> inputs){
        return synapsis(weights, inputs) > umbral;
    }

    /**
     * Metodo que realiza la activacion de la neurona mediante la suma ponderada y
     * la funcion de activacion sigmoidal
     * @param weights Son los pesos de las aristas que conectan a los perceptrones
     * @param inputs Son las entradas
     * @return Devuelve la salida de la neurona
     */
    public double synapsis(ArrayList<Float> weights, ArrayList<Float> inputs){
        //float sum = WeightedSum(weights, inputs);
       // this.lastOutput = Function.sigmoidalFunction(sum);
        return lastOutput;
    }

    /**
     * Metodo que se encarga de realizar la suma ponderada entre los pesos y
     * las entradas = Sum i=0->k(wi*xi)
     * @param weights Son los pesos de las aristas que conectan a los perceptrones
     * @param inputs Son las entradas
     * @return Devuelve la suma ponderada
     */
    private double WeightedSum(ArrayList<Double> weights, ArrayList<Double> inputs){
        double sum = 0;
        for (int i = 0; i < inputs.size(); i++) {
            sum+=(weights.get(i)*inputs.get(i));
        }
        return sum;
    }

    //METODOS SET Y GET DE LA CLASE

    public double getBias() {
        return bias;
    }

    public void setBias(float bias) {
        this.bias = bias;
    }

    public double getUmbral() {
        return umbral;
    }

    public void setUmbral(float umbral) {
        this.umbral = umbral;
    }

    public float getLastOutput() {
        return lastOutput;
    }

    public void setLastOutput(float lastOutput) {
        this.lastOutput = lastOutput;
    }

    public double getError() {
        return error;
    }

    public void setError(float error) {
        this.error = error;
    }

    public int getLayer() {
        return layer;
    }

    public double getWeightBias() {
        return weightBias;
    }

    public void setWeightBias(float weightBias){
        this.weightBias = weightBias;
    }
    public void setLayer(int layer) {
        this.layer = layer;
    }

}
