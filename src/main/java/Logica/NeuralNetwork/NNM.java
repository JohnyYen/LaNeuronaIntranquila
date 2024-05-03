package Logica.NeuralNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;

/**
 * Clase controladora principal donde se crea la informacion
 * de la red neuronal y se carga y guarda en ficheros
 */
public class NNM {
    private NeuralNetwork network;
    private File configuration;
    public static NNM instanciate;
    private NNM(){
        configuration = new File("src/main/java/Logica/NeuralNetwork/IRIS/configuration.DAT");
    }

    public void saveNetwork() throws IOException {
        RandomAccessFile file = new RandomAccessFile(this.configuration, "rw");
        byte[] networkBytes = Convert.toBytes(network);
        file.writeLong(networkBytes.length);
        file.write(networkBytes);
        file.close();
    }
    public static void createInstanciate(){
        if(instanciate == null)
            instanciate = new NNM();
    }

    public void train(float alpha, float error, int epoch){
        if(network != null)
            network.train(alpha, error, epoch);
    }

    public void predecir(ArrayList<Float> inputs, String flower){
        if(network != null)
            network.predictionAnswer(inputs, flower);

    }
    public void createNetwork(){
        try {
            RandomAccessFile file = new RandomAccessFile(configuration, "rw");
            int networkLenght = file.readInt();
            byte[] networkBytes = new byte[networkLenght];
            file.read(networkBytes);
            network = (NeuralNetwork) Convert.toObject(networkBytes);
            file.close();
        }
        catch (IOException | ClassNotFoundException e){
            network = new NeuralNetwork(4);
        }
    }

    public void createNetwork(int[] topology){
        try {
            RandomAccessFile file = new RandomAccessFile(configuration, "rw");
            int networkLenght = file.readInt();
            byte[] networkBytes = new byte[networkLenght];
            file.read(networkBytes);
            network = (NeuralNetwork) Convert.toObject(networkBytes);
            file.close();
        }
        catch (IOException | ClassNotFoundException e){
            network = new NeuralNetwork(topology.length, topology);
        }
    }
    public void createLayer(int countNeurons){
        if(network != null && countNeurons > 0)
            network.addLayer(countNeurons);
    }

    public void eraseLayer(int layer){
        if(network != null && layer > 0)
            network.eraseLayer(layer);
    }

    public void changeLayer(int layer, int countNeurons){

    }

    public NeuralNetwork getNetwork(){return this.network;}
}
