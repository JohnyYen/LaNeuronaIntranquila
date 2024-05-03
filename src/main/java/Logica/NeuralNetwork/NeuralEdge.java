package Logica.NeuralNetwork;
import Logica.lib.cu.edu.cujae.ceis.graph.edge.*;
import Logica.lib.cu.edu.cujae.ceis.graph.vertex.Vertex;

import java.io.Serializable;

public class NeuralEdge extends Edge implements Serializable {
    float weight;
    private static final long serialVersionUID = 2L;
    public NeuralEdge(Vertex vertex) {
        super(vertex);
        weight = 0;
    }

    public NeuralEdge(Vertex vertex, float weight){
        super(vertex);
        this.weight = weight;
    }

    public float getWeight(){return this.weight;}
    public void setWeight(float weight){this.weight = weight;}
}
