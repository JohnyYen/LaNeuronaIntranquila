package Logica.NeuralNetwork;

import java.text.DecimalFormat;

/**
 * Clase donde se almacenan todas las funciones usadas en
 * el perceptron multicapa
 */
public class Function {

    /**
     * Funcion Sigmoidal
     * @param x Valor de la suma ponderada
     * @return El valor de activacion
     */
    public static float sigmoidalFunction(float x){
        float a = (float) Math.pow(Math.E, -x);
        //System.out.println(a);
        return (1.0f/(1.0f+a));
    }

    public static double test(double x){
        return 1.0 / (1.0 + (Math.pow(Math.E, -x)));
    }

    /**
     * Derivada de la funcion Sigmoidad
     * @param y Valor de salida de la neurona
     * @return El valor de la derivada
     */
    public static float derivateSigmoidal(float y) {
        return y*(1.0f - y);
    }

    /**
     * Derivada de la funcion del error cuadratico medio
     * @param out Salida esperada
     * @param y Salida obtenida
     * @return El resultado de evaluar en la derivda
     */
    public static float derivateErrorFunction(float out, float y){
        return y - out;
    }

    /**
     * Error cuadratico medio = (1/2)*(Se-So)^2
     * @param out Salida esperada
     * @param y Salida obtenida
     * @return Devuelve el error cometido por el perceptron
     */
    public static float errorFunction(float y, float out){
        return (float) ((Math.pow(out-y,2))/2);
    }
    /**
     * Funcion delta = dy/dx(Sigmoidal)*dy/dx(ErrorFunction)
     * Para la capa de salida
     * @param y Salida obtenida
     * @param out Salida esperada
     * @return Devuelve el valor del delta para la actualizacion de los pesos
     */
    public static float delta(float y, float out) {
        float d = derivateSigmoidal(y);
        float ed = derivateErrorFunction(out, y);
        return d * ed;
    }

    /**
     * Funcion delta = dy/dx(Sigmoidal)*dy/dx(ErrorFunction)
     * Para las capas n-1
     * @param y Salida obtenida
     * @param sumDeltas Sumatoria de todas las deltas de la capa anterior
     * @return Devuelve el valor del delta para la actualizacion de los pesos
     */
    public static float deltaHidden(float y, float sumDeltas){
        return derivateSigmoidal(y)*sumDeltas;
    }
}
