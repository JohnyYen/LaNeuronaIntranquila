package Logica.NeuralNetwork;

import Logica.lib.cu.edu.cujae.ceis.graph.LinkedGraph;
import Logica.lib.cu.edu.cujae.ceis.graph.edge.Edge;
import Logica.lib.cu.edu.cujae.ceis.graph.vertex.Vertex;

import java.io.Serializable;
import java.util.*;

/**
 * Red Neuronal Perceptron Multicapa
 */
public class NeuralNetwork extends LinkedGraph implements Serializable {
    private DataSheet dataSheet;
    private int layerSize;
    private float alpha;
    private float predictionError;
    private int[] topology;

    private static final long serialVersionUID = 1L;

    /**Constructor
     * Inicializa las 3 capas con la numLayeridad de neuronas correspondientes
     * Uso del metodo initEdgesWeight para inicializar los pesos de las aristas y concatenar neuronas
     * @param neuronsMidLayer numLayeridad de neuronas de la unica capa intermedia
    */
    public NeuralNetwork(int neuronsMidLayer) {
        dataSheet = new DataSheet();
        layerSize = 3;
        int[] neuronsLayer = {4, neuronsMidLayer, 3};
        for (int i = 0; i < 3; i++) {
            int numNeuronsLayer = neuronsLayer[i];
            for (int j = 0; j < numNeuronsLayer; j++) {
                insertVertex(new Perceptron(i));
            }
        }
        this.topology = neuronsLayer;
        initEdgesWeight();

    }

    /**
     * Constructor que recibe como parametro la cantidad de capas y por cada capa
     * la cantidad de neuronas a utilizar
     * @param midLayerSize
     * @param midLayerTopology
     */
    public NeuralNetwork(int midLayerSize, int[] midLayerTopology){
        dataSheet = new DataSheet();
        layerSize = midLayerSize+2;
        int[] neuronsLayer = new int[layerSize];
        neuronsLayer[0] = 4;
        for(int i = 0; i < midLayerTopology.length; i++)
            neuronsLayer[i+1] = midLayerTopology[i];

        neuronsLayer[layerSize-1] = 3;

        for (int i = 0; i < layerSize; i++) {
            int numNeuronsLayer = neuronsLayer[i];
            for (int j = 0; j < numNeuronsLayer; j++) {
                insertVertex(new Perceptron(i));
            }
        }
        this.topology = neuronsLayer;
        initEdgesWeight();

    }
    /**
     * Método que se encarga de inicializar las aristas entre los
     * perceptrones de cada capa, asignandole un valor random como peso
     */
    private void initEdgesWeight() {
        for(int i = 0; i < layerSize-1; i++){
            ArrayList<Vertex> vertex = getNodeInLayer(i);
            ArrayList<Vertex> nextVertex = getNodeInLayer(i+1);
            Random random = new Random();
            boolean isSave = false;
            Deque<Integer> cache = new ArrayDeque<Integer>();
            for(Vertex v : vertex){
                for(Vertex va : nextVertex){
                    if(!isSave){
                        insertWNeuralEdgeDG(v,va,Float.parseFloat(String.format("%.2f",random.nextFloat(0,4))));
                    }
                    else{
                        int indexVertex = cache.poll();
                        cache.offer(indexVertex);
                    }

                }
            }

        }
    }


    /**
     * Metodo que se encarga de buscar todos los nodos
     * en una capa determinada
     * @param layer Capa
     * @return Una lista de neuronas
     */
    public ArrayList<Vertex> getNodeInLayer(int layer){
        ArrayList<Vertex> list = new ArrayList<Vertex>();
        Iterator<Vertex> v = getVerticesList().listIterator();

        while(v.hasNext() && this.topology[layer] != list.size()){
            Vertex vi = v.next();
            if(((Perceptron)vi.getInfo()).getLayer() == layer)
                list.add(vi);
        }
        return list;
    }

    /**
     * Metodo que mediante la especie de la flor obtiene la salida esperada
     * @param flowerSpecies La flor de la salida esperada
     * @param oneHotVector El vector de la salida esperada
     */
    public void CategoricalVariable(String flowerSpecies, int[] oneHotVector){
        switch (flowerSpecies) {
            case "Iris-setosa":
                oneHotVector[0] = 1;
                break;
            case "Iris-virginica":
                oneHotVector[1] = 1;
                break;
            case "Iris-versicolor":
                oneHotVector[2] = 1;
                break;
            default:
                throw new IllegalArgumentException("Unexpected flower: " + flowerSpecies);
        }
    }

    /**
     * Método que se encarga de de clasificar la flor segun la salida
     * de las neuronas
     * @param outPuts Las salidas de las neuronas de salida
     * @return Devuelve la clasificación de la flor
     */
    public String clasificationFlower(ArrayList<Float> outPuts){
        String flower = null;
        float mayor = 0;
        int pos = 0;
        for (int i = 0; i < outPuts.size(); i++) {
            float actualValue = outPuts.get(i);
            if(actualValue>mayor) {
                mayor = actualValue;
                pos = i;
            }
        }
        flower = switch (pos) {
            case 0 -> "Iris Setosa";
            case 1 -> "Iris Virgínica";
            case 2 -> "Iris Versicolor";
            default -> throw new IllegalArgumentException("Unexpected value: " + pos);
        };
        return flower;
    }

    public int [] finalOutputs(ArrayList<Float> outPuts){
        int [] output = {0, 0, 0};
        double mayor = 0;
        int pos = 0;
        for (int i = 0; i < outPuts.size(); i++) {
            double actualValue = outPuts.get(i);
            if(actualValue>mayor) {
                mayor = actualValue;
                pos = i;
            }
        }
        switch (pos) {
            case 0 -> output[0] = 1;
            case 1 -> output[1] = 1;
            case 2 -> output[2] = 1;
            default -> throw new IllegalArgumentException("Unexpected value: " + pos);
        };
        return output;
    }

    /**
     * Metodo que obtiene la sumatoria del error cuadratico medio
     * @param outPuts Salidas obtenidas
     * @param oneHot Salidas esperadas
     */
    public void predictionError(ArrayList<Float> outPuts, int[] oneHot){
        float sum = 0;
        for (int i = 0; i < outPuts.size(); i++) {
            sum += Function.errorFunction(oneHot[i],outPuts.get(i));
        }
        this.predictionError = sum;
    }

    /**
     * Metodo que obtiene las salidas de la capa actual y actualiza
     * la salida de cada uno de los perceptrones de la capa actual.
     * @param currentVertex
     * @param inputs
     * @param thisLayer
     * @return Genera las salidas de la capa actual
     */
    public ArrayList<Float> getOutPutInLayer(ArrayList<Vertex> currentVertex, ArrayList<Float> inputs, int thisLayer){
        ArrayList<Float> outs = new ArrayList<Float>();
        ArrayList<Vertex> previousLayer = getNodeInLayer(thisLayer-1);
        for(Vertex v : currentVertex){
            float sumPond = 0;
            int i = 0;
            for(Vertex va : previousLayer){
                LinkedList<Edge> edglist = va.getEdgeList();
                Iterator<Edge> edg = edglist.iterator();
                boolean find = false;
                while(edg.hasNext() && !find){
                    NeuralEdge edge = (NeuralEdge) edg.next();
                    if(edge.getVertex().equals(v)){
                        sumPond += edge.getWeight() * inputs.get(i++);
                    }
                }
            }
            float activateValue = Function.sigmoidalFunction(sumPond);
            ((Perceptron)v.getInfo()).setLastOutput(activateValue);
            outs.add(activateValue);
        }

        return outs;
    }


    /**
     * Algoritmo de propagacion que dada unos valores de entrada
     * activa las neuronas consecuentes para predecir la flor
     * @return
     */
    public ArrayList<Float> propagation(ArrayList<Float> inFirst){
        ArrayList<Float> inputs = null;
        ArrayList<Vertex> vertexlist = new ArrayList<>();

        for (int i = 0; i < layerSize; i++) {
            vertexlist = getNodeInLayer(i);
            if(i == 0){
                //System.out.println(vertexlist);
                ((Perceptron)vertexlist.get(0).getInfo()).setLastOutput(inFirst.get(0));
                ((Perceptron)vertexlist.get(1).getInfo()).setLastOutput(inFirst.get(1));
                ((Perceptron)vertexlist.get(2).getInfo()).setLastOutput(inFirst.get(2));
                ((Perceptron)vertexlist.get(3).getInfo()).setLastOutput(inFirst.get(3));
                inputs = inFirst;
            }
            else{
                inputs = getOutPutInLayer(vertexlist, inputs, i);
            }
        }


        return inputs;
    }


    /**
     * Algoritmo de backpropagation que dada las salidas permite modificar
     * los pesos de las neuronas capa por capa para dar un menor porcentaje
     * de salida y predecir correctamente la flor
     * @param outs
     */
    public void backPropagation(int[] outs) {
        LinkedList<Float> deltas = new LinkedList<Float>();
        for(int i = layerSize-1; i != -1; i--){
            ArrayList<Vertex> currentNodeList = this.getNodeInLayer(i);
            if(i == layerSize-1){
                for(int j = 0; j < 3; j++){
                    Vertex vertex = currentNodeList.get(j);
                    deltas.add(Function.delta(((Perceptron)vertex.getInfo()).getLastOutput(), outs[j]));
                }
            }
            else {
                LinkedList<Float> newDeltas = new LinkedList<Float>();
                for (Vertex vertex : currentNodeList) {
                    float delta = Function.deltaHidden(((Perceptron) vertex.getInfo()).getLastOutput(), this.SumDeltas(deltas, vertex.getEdgeList(), i));
                    newDeltas.add(delta);
                }
                updateWeightInLayer(deltas, currentNodeList, getNodeInLayer(i+1));
                deltas = newDeltas;
            }
        }
    }


    /**
     * Metodo que dado una capa actualiza todos los perceptrones que formen parte de ella
     * @param deltas
     * @param vertexList
     * @param thisLayer
     */
    public void updateWeightInLayer(LinkedList<Float> deltas, ArrayList<Vertex> vertexList, int thisLayer){
        Iterator<Float> delt = deltas.iterator();
        for(int i = 0; i < vertexList.size(); i++){
            updateWeightInPerceptron(delt.next(), vertexList.get(i).getEdgeList(), thisLayer, vertexList.get(i));
        }
    }

    /**
     * Metodo que actualiza los pesos de la capa anterior mediante
     * la regla delta y los calculos consiguientes
     * @param deltas
     * @param vertexList
     * @param currentVertex
     */
    public void updateWeightInLayer(LinkedList<Float> deltas, ArrayList<Vertex> vertexList, ArrayList<Vertex> currentVertex){
        Iterator<Float> delt = deltas.iterator();
        for(Vertex vert : currentVertex){
            float delta = delt.next();
            for(Vertex v : vertexList){
                boolean find = false;
                for(int i = 0; i < v.getEdgeList().size() && !find; i++){
                    NeuralEdge edge = (NeuralEdge) v.getEdgeList().get(i);
                    if(edge.getVertex() == vert){
                        //find = true;
                        float newWeight = edge.getWeight() - alpha * delta * ((Perceptron)vert.getInfo()).getLastOutput();
                        edge.setWeight(newWeight);
                    }
                }
            }
        }
    }

    /**
     * Metodo que dado un vertice actualiza todos los pesos de las neuronas adyacentes a ella
     * mediante dos cola, una que guarda los nuevos pesos y otra que guarda los vertices.
     * Con la de los vertices se crean las nuevas aristas del vertice dado y los nuevos pesos
     * @param delta
     * @param edges
     * @param thisLayer
     * @param vertex
     */
    public void updateWeightInPerceptron(float delta, LinkedList<Edge> edges, int thisLayer, Vertex vertex){
        Queue<Vertex> vertexQueue = new ArrayDeque<>();
        Queue<Float> weightQueue = new ArrayDeque<>();
        Iterator<Edge> itEdges = edges.iterator();
        int posVertex = getVertexIndex(vertex);
        while(itEdges.hasNext()){
            NeuralEdge edge = (NeuralEdge) itEdges.next();
            if(((Perceptron)edge.getVertex().getInfo()).getLayer() == thisLayer){
                float newWeight = (float) (edge.getWeight() - alpha * delta * ((Perceptron)edge.getVertex().getInfo()).getLastOutput());
                edge.setWeight(newWeight);
            }
        }
    }


    /**
     * Se encarga de sumar los valores de los deltas
     *
     * @param deltas
     * @return
     */
    public float SumDeltas(LinkedList<Float> deltas, LinkedList<Edge> edges, int layer){
        float delta = 0;
        Iterator<Float> it = deltas.iterator();
        Iterator<Edge> edg = edges.iterator();
        while(it.hasNext()){
            NeuralEdge edge = (NeuralEdge) edg.next();
            if(((Perceptron)edge.getVertex().getInfo()).getLayer() == layer+1)
                delta += it.next()*(Float)edge.getWeight();
        }
        return delta;
    }

    /**
     * Metodo que se encarga de entrenar a la red
     */
    public void train(float alpha, float error, int epoch) {
        this.alpha = alpha;

//        for(Iris iris: dataSheet.getDataTrain()){
//            int[] test1 = new int[3];
//            ArrayList<Float> tes2 = null;
//
//            for(int i = 0; i < epoch; i++){
//                int[] outsObjetive = {0,0,0};
//                ArrayList<Float> finalOutput = this.propagation(iris.getArrayData());
//                this.CategoricalVariable(iris.getTypeFlower(), outsObjetive);
//                predictionError(finalOutput, outsObjetive);
//                if(predictionError*100 > error)
//                    this.backPropagation(outsObjetive);
//                test1 = outsObjetive;
//                tes2 = finalOutput;
//                //System.out.println("Error calculado: "+predictionError*100);
//            }
//            System.out.println("Error calculado: "+predictionError*100);
//            System.out.println("Salida objetiva: "+ test1[0] + " " + test1[1] + " " + test1[2] + "--- Salida obtenida: "+tes2);
//        }

        for(Iris iris: dataSheet.getDataTrain()){
            int[] test1 = new int[3];
            ArrayList<Float> tes2 = null;
            ArrayList<Float> finalOutput = null;
            int [] outsObjetive = {0,0,0};
            this.CategoricalVariable(iris.getTypeFlower(), outsObjetive);
            int [] outsFinal;
            for(int i = 0; i < epoch; i++){
                finalOutput = this.propagation(iris.getArrayData());
                predictionError(finalOutput, outsObjetive);
                if(predictionError*100 > error)
                    this.backPropagation(outsObjetive);
                test1 = outsObjetive;
                tes2 = finalOutput;
            }
            dataSheet.writeFlower(outsObjetive, finalOutput, clasificationFlower(finalOutput));
            System.out.println("Error calculado: "+predictionError*100);
            System.out.println("Salida objetiva: "+ test1[0] + " " + test1[1] + " " + test1[2] + "--- Salida obtenida:" +tes2);
        }
    }
    /**
     * Metodo que crea una arista ponderada no dirigida entre dos perceptrones
     * @param tail
     * @param head
     * @param weight
     */
    public void insertWNeuralEdgeNDG(Vertex tail, Vertex head, float weight){
        tail.getEdgeList().add(new NeuralEdge(head, weight));
        head.getEdgeList().add(new NeuralEdge(tail, weight));
    }

    /**
     * Metodo que crea una arista ponderada dirigida entre dos perceptrones
     * @param tail
     * @param head
     * @param weight
     */
    public void insertWNeuralEdgeDG(Vertex tail, Vertex head, float weight){
        tail.getEdgeList().add(new NeuralEdge(head, weight));
    }

    public void printfNetwork(){
        for(int i = 0; i < layerSize; i++){
            ArrayList<Vertex> vert = getNodeInLayer(i);
            System.out.println(vert.get(2).getEdgeList());
        }
    }

    public void addLayer(int countNeurons){
        if(countNeurons > 0){
            int[] newTopology = new int[topology.length+1];
            int save = 0;
            for(int i = 0; i < layerSize;i++){
                newTopology[i] = topology[i];
            }
            layerSize++;
            int aux = newTopology[layerSize-2];
            newTopology[layerSize-1] = aux;
            newTopology[layerSize-2] = countNeurons;
            topology = newTopology;
            for(int i = 0; i < topology[layerSize-2]; i++){
                getVerticesList().add(new Vertex(new Perceptron(layerSize)));
            }
            rebootWeight(layerSize-3);
        }
    }

    public void rebootWeight(int layer){
        for(int i = layer; i < layerSize-1; i++){
            ArrayList<Vertex> currentVertex = getNodeInLayer(i);
            ArrayList<Vertex> previousVertex = getNodeInLayer(i+1);
            Random random = new Random();
            for(Vertex v : currentVertex){
                v.getEdgeList().clear();
                for(Vertex a : previousVertex){
                    insertWNeuralEdgeDG(v,a,Float.parseFloat(String.format("%.2f",random.nextFloat(0,4))));
                }
            }
        }
    }

    public void eraseLayer(int layer){
        for(int i = layer; i < layerSize-1; i++){
            topology[i] = topology[i+1];
        }
        layerSize--;
        ArrayList<Vertex> currentVertex = getNodeInLayer(layer-1);
        for(Vertex v : currentVertex){
            getVerticesList().remove(v);
        }
        rebootWeight(layer-1);
    }

    public void insertLayer(int layer, int countNeurons){
        int[] newTopology = new int[layerSize+1];
        for(int i = layerSize; i > layer; i--){
            newTopology[i] = topology[i-1];
        }
    }

    public String getMatrix(){
        String output = "     ";
        for (int i = 0; i < getVerticesList().size(); i++){
            if(i < 4){
                output += "    i"+(i+1)+"    ";
            }
            else if(i > getVerticesList().size()-4){
                output += "    o"+(i+1)+ "    ";
            }
            else output += "    h"+(i+1)+"    ";
        }
        output += "\n";
        int i = 0;
        for(Vertex v : getVerticesList()){
            if(i < 4){
                output += "i"+(i+1)+"   ";
            }
            else if(i > getVerticesList().size()-4){
                output += "o"+(i+1)+"   ";
            }
            else  output += "h"+(i+1)+"   ";

           for(Vertex a : getVerticesList()){
               boolean found = false;
               NeuralEdge e = null;
               for(int j = 0; j < v.getEdgeList().size() && !found; j++)
                   if(v.getEdgeList().get(j).getVertex().equals(a)){
                       found = true;
                       e = (NeuralEdge) v.getEdgeList().get(j);
                   }


              if(i < 10){
                  if(found){
                      if(e.getWeight()<0){
                          output += "  " + String.format("%.2f", e.getWeight()) + "  ";
                      }
                      else{
                          output += "   " + String.format("%.2f", e.getWeight()) + "   ";
                      }
                  }
                  else output += "    0     ";
              }
              else {
                  if(found)
                      output += "  " + String.format("%.2f", e.getWeight()) + "  ";
                  else output += "     0    ";
              }
           }
            i++;
            output += "\n";
        }
        System.out.println(output);
        return output;
    }
    public int getLayer(){return this.layerSize;}

    public String predictionAnswer(ArrayList<Float> inputs, String flower){

        ArrayList<Float> finalOutput = this.propagation(inputs);
        return clasificationFlower(finalOutput);
    }
}
